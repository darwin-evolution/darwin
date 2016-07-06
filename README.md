[![Build Status](https://travis-ci.org/darwin-evolution/darwin.svg?branch=master)](https://travis-ci.org/darwin-evolution/darwin)
[![MIT License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)
[![Javadocs](http://javadoc.io/badge/com.github.darwin-evolution/darwin.svg)](http://javadoc.io/doc/com.github.darwin-evolution/darwin)

## The Problem
How to test refactored code that originally had no tests, unclear specification, no owner, and just begged to end it's misery ?

## Solution
Let the the users do it for you! All you need are few steps.

#### Identify your enemy

```java

public class RentalDiscountService {

    // help needed!! but to be honest, it could've been worse...
    public Double calculateDiscountPercent(Integer movieType, Integer rentalPeriod) {
        if (movieType == 1 || (movieType != 3 &&  rentalPeriod == -1)) {
            return 10.0;
        } else if (movieType == 2 && rentalPeriod == 1) {
            return 40.0;
        } else if (movieType == 4 && rentalPeriod > 10) {
            return 5.0;
        } else {
            return 0.0;
        }
    }
}
         
```


#### Add maven dependency

```xml
<dependency>
    <groupId>com.github.darwin-evolution</groupId>
    <artifactId>darwin</artifactId>
    <version>0.9.1</version>
</dependency>
```

#### Create art not code


```java
public class RentalDiscountCalculator {

    private static final BigDecimal NO_DISCOUNT = BigDecimal.ZERO;
    private static final BigDecimal WEEKEND_DISCOUNT = BigDecimal.TEN;
    private static final BigDecimal ROMANTIC_EVENING_DISCOUNT = new BigDecimal(40.0);
    private static final BigDecimal FAMILY_WEEK = new BigDecimal(5.0);

    //piece of art, not code... (please don't judge)
    public BigDecimal calculateDiscount(MovieType movieType, RentalPeriod rentalPeriod) {
        if (movieType.equals(MovieType.COMEDY)
                || (!movieType.equals(MovieType.HORROR) && rentalPeriod.isWeekend())) {
            return WEEKEND_DISCOUNT;
        }

        if (movieType.equals(MovieType.ROMANCE) && rentalPeriod.isOverNight()) {
            return ROMANTIC_EVENING_DISCOUNT;
        }

        if (movieType.equals(MovieType.FAMILY) && rentalPeriod.isLongRental())  {
            return FAMILY_WEEK;
        }

        return NO_DISCOUNT;
    }
}
              
```

#### Write evolution

We encapsulate both implementations in single evolution, in order to test and measure them. 
Both implementations are executed in sequence, starting with legacy one.

```java

import com.github.darwinevolution.darwin.Evolution;
import com.github.darwinevolution.darwin.execution.harness.EvolvedExecutionHarness;
import com.github.darwinevolution.darwin.execution.harness.ProtoplastExecutionHarness;

public class RentalDiscountService {

    private RentalDiscountCalculator rentalDiscountCalculator;

    // we rule don't we?
    public Double calculateDiscountPercent(final Integer movieType, final Integer rentalPeriod) throws Exception {
        return Evolution.<Double>of("calculateDiscountPercent")
                .from(new ProtoplastExecutionHarness<Double>() {
                    @Override
                    public Double execute() throws Exception {
                        arguments(movieType, rentalPeriod); // arguments for logging purposes
                        
                        // run our legacy implementation
                        return legacyCalculateDiscountPercent(movieType, rentalPeriod); 
                    }
                }).to(new EvolvedExecutionHarness<Double>() {
                    @Override
                    public Double execute() throws Exception {
                        arguments(movieType, rentalPeriod); // arguments for logging purposes
                        
                        // unleash the beauty!
                        return rentalDiscountCalculator.calculateDiscount(MovieTypeFactory.from(movieType), 
                            RentalPeriod.from(rentalPeriod)).doubleValue();
                    }
                }).evolve();
    }

    // we need to extract it and contain it, before it spreads around all our system...
    private Double legacyCalculateDiscountPercent(Integer movieType, Integer rentalPeriod) {
        if (movieType == 1 || (movieType != 3 && rentalPeriod == -1)) {
            return 10.0;
        } else if (movieType == 2 && rentalPeriod == 1) {
            return 40.0;
        } else if (movieType == 4 && rentalPeriod > 10) {
            return 5.0;
        } else {
            return 0.0;
        }
    }
}
```

#### Configure logger

Results of our evolution executions will be logged here...

```xml    
<Loggers>
    <Logger name="darwin.evolution" level="trace" additivity="false">
        ...
    </Logger>
</Loggers>
```

#### Build it, ship it

Build your app and install in any environment (dev, qa, prod).
 
#### Analyze produced logs

When someone executes our **RentalDiscountService.calculateDiscountPercent** method, 
our logs will contain entries like this ([more info](https://github.com/darwin-evolution/darwin#anchor-1))

> "calculateDiscountPercent"|"PROTOPLAST"|"1463682597919"|"OK"||"249896"||||"171597"|||

You noticed big juicy **OK**? It means values returned by both implementations were equal, so we've just tested our new code
without moving a finger. It's nice to have users...

#### Remove old implementation

After incubation period, may it be a week or a month, when you see in logs that every call
behaved exactly the same as the old one (or even better) you can 
safely delete all traces of old implementation alongside evolution associated with it.
And now you are one step closer to perfection!

## Documentation

Visit our project [page](https://github.com/darwin-evolution/darwin).


## Authors and Contributors
Damian Kolasa (@fatfredyy)

### Support or Contact
Having trouble with Darwin? Check out our [groups](https://groups.google.com/d/forum/darwin-evolution).
