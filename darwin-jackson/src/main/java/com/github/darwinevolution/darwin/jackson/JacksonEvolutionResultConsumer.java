/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016, Darwin Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.darwinevolution.darwin.jackson;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.darwinevolution.darwin.api.EvolutionResultConsumer;
import com.github.darwinevolution.darwin.api.ResultConsumerConfiguration;
import com.github.darwinevolution.darwin.execution.result.ValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.comparison.ComparisonResult;
import com.github.darwinevolution.darwin.execution.result.ExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JacksonEvolutionResultConsumer<T> extends EvolutionResultConsumer<T> {

    public static final String FILTER_NAME = "darwin.comparison.result";

    private static final Logger log = LoggerFactory.getLogger(JacksonEvolutionResultConsumer.class);
    private static final Logger evolutionLog = LoggerFactory.getLogger("darwin.evolution");

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void consumeResults(ComparisonResult<T> comparisonResult, ResultConsumerConfiguration resultConsumerConfiguration) {
        try {
            SimpleFilterProvider fp = new SimpleFilterProvider();
            Set<String> includedFields = new HashSet<String>();
            includedFields.add("name");
            includedFields.add("implementationPreference");
            includedFields.add("timestamp");
            includedFields.add("resultType");
            includedFields.add("protoplastDurationNs");
            includedFields.add("evolvedDurationNs");


            if (comparisonResult.getResultType().isError() && resultConsumerConfiguration.shouldPrintDetailsOnError()) {
                includedFields.add("protoplastArguments");
                includedFields.add("protoplastValue");
                includedFields.add("protplastException");

                includedFields.add("evolvedArguments");
                includedFields.add("evolvedValue");
                includedFields.add("evolvedException");
            }

            if (resultConsumerConfiguration.shouldPrintBothArguments()) {
                includedFields.add("protoplastArguments");
                includedFields.add("evolvedArguments");
            }

            if (resultConsumerConfiguration.shouldPrintResults()) {
                includedFields.add("protoplastValue");
                includedFields.add("protplastException");
                includedFields.add("evolvedValue");
                includedFields.add("evolvedException");
            }

            fp.addFilter(FILTER_NAME, SimpleBeanPropertyFilter.filterOutAllExcept(includedFields));
            String json = objectMapper.writer(fp).writeValueAsString(JsonComparisonResult.from(comparisonResult, resultConsumerConfiguration));
            evolutionLog.debug(json);
        } catch (JsonProcessingException e) {
            log.error("Error creating json comparison result", e);
        }
    }


    @XmlRootElement
    @JsonFilter(FILTER_NAME)
    public static class JsonComparisonResult implements Serializable {

        private String name;
        private String implementationPreference;
        private Long timestamp;
        private String resultType;

        private Long protoplastDurationNs;
        private Object[] protoplastArguments;
        private String protoplastValue;
        private String protplastException;

        private Long evolvedDurationNs;
        private Object[] evolvedArguments;
        private String evolvedValue;
        private String evolvedException;

        private JsonComparisonResult(String name,
                                     String implementationPreference,
                                     Long timestamp, String resultType,
                                     Long protoplastDurationNs,
                                     Object[] protoplastArguments,
                                     String protoplastValue,
                                     String protplastException,
                                     Long evolvedDurationNs,
                                     Object[] evolvedArguments,
                                     String evolvedValue,
                                     String evolvedException) {
            this.name = name;
            this.implementationPreference = implementationPreference;
            this.timestamp = timestamp;
            this.resultType = resultType;
            this.protoplastDurationNs = protoplastDurationNs;
            this.protoplastArguments = protoplastArguments;
            this.protoplastValue = protoplastValue;
            this.protplastException = protplastException;
            this.evolvedDurationNs = evolvedDurationNs;
            this.evolvedArguments = evolvedArguments;
            this.evolvedValue = evolvedValue;
            this.evolvedException = evolvedException;
        }

        public String getName() {
            return name;
        }

        public String getImplementationPreference() {
            return implementationPreference;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public String getResultType() {
            return resultType;
        }

        public Long getProtoplastDurationNs() {
            return protoplastDurationNs;
        }

        public Object[] getProtoplastArguments() {
            return protoplastArguments;
        }

        public String getProtoplastValue() {
            return protoplastValue;
        }

        public String getProtplastException() {
            return protplastException;
        }

        public Long getEvolvedDurationNs() {
            return evolvedDurationNs;
        }

        public Object[] getEvolvedArguments() {
            return evolvedArguments;
        }

        public String getEvolvedValue() {
            return evolvedValue;
        }

        public String getEvolvedException() {
            return evolvedException;
        }

        public static JsonComparisonResult from(ComparisonResult comparisonResult, ResultConsumerConfiguration resultConsumerConfiguration) {
            return new JsonComparisonResult(comparisonResult.getName(),
                    comparisonResult.getImplementationPreference().name(),
                    comparisonResult.getTimestamp(),
                    comparisonResult.getResultType().name(),
                    comparisonResult.getProtoplastExecutionResult().getDurationNs(),
                    comparisonResult.getProtoplastExecutionResult().getArguments(),
                    extractValue(comparisonResult.getProtoplastExecutionResult(), resultConsumerConfiguration),
                    extractException(comparisonResult.getProtoplastExecutionResult(), resultConsumerConfiguration),
                    comparisonResult.getEvolvedExecutionResult().getDurationNs(),
                    comparisonResult.getEvolvedExecutionResult().getArguments(),
                    extractValue(comparisonResult.getEvolvedExecutionResult(), resultConsumerConfiguration),
                    extractException(comparisonResult.getEvolvedExecutionResult(), resultConsumerConfiguration));
        }

        private static String extractValue(ExecutionResult executionResult, ResultConsumerConfiguration resultConsumerConfiguration) {
            if (executionResult instanceof ValueExecutionResult) {
                return String.valueOf(((ValueExecutionResult) executionResult).getValue());
            } else {
                return null;
            }
        }

        private static String extractException(ExecutionResult executionResult, ResultConsumerConfiguration resultConsumerConfiguration) {
            if (executionResult instanceof ExceptionExecutionResult) {
                return String.valueOf(((ExceptionExecutionResult) executionResult).getException());
            } else {
                return null;
            }
        }

    }
}
