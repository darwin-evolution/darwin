package io.darwin.slf4j;

import io.darwin.api.EvolutionResultConsumer;
import io.darwin.execution.ImplementationPreferenceType;
import io.darwin.execution.result.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jEvolutionResultConsumer<T> extends EvolutionResultConsumer<T> {

    private static final Logger log = LoggerFactory.getLogger("darwin.evolution");

    @Override
    public void consumeResults(String evolutionName, ImplementationPreferenceType implementationPreferenceType, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {
        StringBuffer sb = new StringBuffer();

        sb.append(evolutionName).append("|");
        sb.append(implementationPreferenceType.name()).append("|");
        sb.append(currentExecutionResult.getResult()).append("|");
        sb.append(currentExecutionResult.getExecutionTime()).append("|");
        sb.append(currentExecutionResult.getException()).append("|");
        sb.append(evolvedExecutionResult.getResult()).append("|");
        sb.append(evolvedExecutionResult.getExecutionTime()).append("|");
        sb.append(evolvedExecutionResult.getException()).append("|");
        sb.append(compareResults(implementationPreferenceType, currentExecutionResult, evolvedExecutionResult).getResultType().name());

        log.info(sb.toString());
    }
}
