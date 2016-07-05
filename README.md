![Travis](https://travis-ci.org/darwin-evolution/darwin.svg)
![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)

### Why ?
Have you ever faced a situation when you wanted to refactor some code, but there were no testes, nobody
actually knows what this code should do exactly, everybody wants to refactor it but they are all afraid 
and don't want to risk ? Then this library is exactly for you! It allows you to write your new implementation
and execute it in test harness alongside existing implementation (even in production environment), 
returning values computed by original implementation, so nothing changes for end user. 
Darwin will monitor and log all executions comparing results returned by both implementations.
Logs will contain every information you need to decide if your implementation is mature enough to be used instead 
of old one.

### How ?
Add maven dependency:

```xml
<dependency>
    <groupId>com.github.darwin-evolution</groupId>
    <artifactId>darwin</artifactId>
    <version>0.9.1</version>
</dependency>
```

Sample evolution:

        import com.github.darwinevolution.darwin.Evolution;
        
        final Integer a = 1, b = 3, c = -1; //our execution arguments

        Integer max = Evolution.<Integer>of("calculateMax") // let's create evolution called "calculateMax"
                .from(new ProtoplastExecutionHarness<Integer>() { // harnessing...
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c); // providing arguments for logging and comparison purposes...
                        return calculateMax(a, b, c); // current implementation
                    }
                })
                .to(new EvolvedExecutionHarness<Integer>() { // harnessing evolved piece of art...
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c); // again logging etc.
                        return calculateMax2(a, b, c); // evolved implementation...
                    }
                })
                .evolve(); // execute both implementations, log execution details (compared results, time etc...) and return execution result 
                            // by default we return old implementation's result


### Authors and Contributors
Damian Kolasa (@fatfredyy)

### Support or Contact
Having trouble with Darwin? Check out our [groups](https://groups.google.com/d/forum/darwin-evolution).
