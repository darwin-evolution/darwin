package io.darwin.slf4j;

import io.darwin.execution.ImplementationPreference;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.Test;

public class Slf4jEvolutionResultConsumerTest {

    @Test
    public void shouldProduceLogMessageForGivenSpec(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = new ComparisonResult<Integer>("testEvolution",
                ImplementationPreference.EVOLVED, Arrays.array(1, 2),
                new ProtoplastValueExecutionResult<Integer>(Arrays.array(1,2), 100, 6),
                new EvolvedValueExecutionResult<Integer>(Arrays.array(1,2), 130, 6),
                ResultType.OK);

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage);

        Assertions.assertThat(loggedMessage).contains("testEvolution");
        Assertions.assertThat(loggedMessage).contains(String.valueOf(6));
    }

    @Test
    public void shouldProduceExceptionLogMessageForGivenSpec(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = new ComparisonResult<Integer>("testEvolution",
                ImplementationPreference.EVOLVED, Arrays.array(1, 2),
                new ProtoplastExceptionExecutionResult<Integer>(Arrays.array(1,2), 100, new Exception("fasd")),
                new EvolvedExceptionExecutionResult<Integer>(Arrays.array(1,2), 130, new Exception("fdsa")),
                ResultType.OK);

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage);

        Assertions.assertThat(loggedMessage).contains("testEvolution");
        Assertions.assertThat(loggedMessage).contains("fdsa");
    }

}
