![Travis](https://travis-ci.org/darwin-evolution/darwin.svg)
![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)

### Why ?
Darwin was created to ease refactoring. He allows You to test Your new implementation, in any environment, even in production (that of course needs some care and caution). Darwin basically executes existing and new implementations (in sequence) comparing results, logging the outcome and returning value from chosen implementation to caller.

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

        final Integer a = 1, b = 3, c = -1;

        Integer max = Evolution.<Integer>of("calculateMax")
                .from(new ProtoplastExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateMax(a, b, c);
                    }
                })
                .to(new EvolvedExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateMax2(a, b, c);
                    }
                })
                .evolve();



### Authors and Contributors
Damian Kolasa (@fatfredyy)

### Support or Contact
Having trouble with Pages? Check out our [groups](https://groups.google.com/d/forum/darwin-evolution).
