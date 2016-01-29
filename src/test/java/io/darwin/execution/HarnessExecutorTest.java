package io.darwin.execution;

import io.darwin.execution.harness.CurrentExecutionHarness;
import io.darwin.execution.result.ExecutionResult;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class HarnessExecutorTest {

    @Test
    public void shouldCatchAnyExceptionThrownFromHarness() {
        //given
        final IllegalStateException illegalStateException = new IllegalStateException();

        //when
        ExecutionResult<Integer> integerExecutionResult = new HarnessExecutor<Integer>()
                .executeHarness(new CurrentExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        throw illegalStateException;
                    }
                });

        //then
        assertThat(integerExecutionResult.getException()).isEqualTo(illegalStateException);
        assertThat(integerExecutionResult.getArguments()).isEmpty();
        assertThat(integerExecutionResult.getResult()).isNull();
    }

    @Test
    public void shouldReturnIntegerResultFromHarness() {
        //given
        final Integer result = 100;

        //when
        ExecutionResult<Integer> integerExecutionResult = new HarnessExecutor<Integer>()
                .executeHarness(new CurrentExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        return result;
                    }
                });

        //then
        assertThat(integerExecutionResult.getException()).isNull();
        assertThat(integerExecutionResult.getArguments()).isEmpty();
        assertThat(integerExecutionResult.getResult()).isEqualTo(result);
    }

    @Test
    public void shouldReturnBooleanResultFromHarness() {
        //given

        //when
        ExecutionResult<Boolean> integerExecutionResult = new HarnessExecutor<Boolean>()
                .executeHarness(new CurrentExecutionHarness<Boolean>() {
                    @Override
                    public Boolean execute() throws Exception {
                        return false;
                    }
                });

        //then
        assertThat(integerExecutionResult.getException()).isNull();
        assertThat(integerExecutionResult.getArguments()).isEmpty();
        assertThat(integerExecutionResult.getResult()).isFalse();
    }

    @Test
    public void shouldReturnIntegerArrayResultFromHarness() {
        //given
        final Integer[] integers = {1,2};

        //when
        ExecutionResult<Integer[]> integerExecutionResult = new HarnessExecutor<Integer[]>()
                .executeHarness(new CurrentExecutionHarness<Integer[]>() {
                    @Override
                    public Integer[] execute() throws Exception {
                        return integers;
                    }
                });

        //then
        assertThat(integerExecutionResult.getException()).isNull();
        assertThat(integerExecutionResult.getArguments()).isEmpty();
        assertThat(integerExecutionResult.getResult()).isNotEmpty();
        assertThat(integerExecutionResult.getResult()).isEqualTo(integers);
    }


    @Test
    public void shouldReturnIntegerListResultFromHarness() {
        //given
        final List<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        integers.add(2);

        //when
        ExecutionResult<List<Integer>> integerExecutionResult = new HarnessExecutor<List<Integer>>()
                .executeHarness(new CurrentExecutionHarness<List<Integer>>() {
                    @Override
                    public List<Integer> execute() throws Exception {
                        return integers;
                    }
                });

        //then
        assertThat(integerExecutionResult.getException()).isNull();
        assertThat(integerExecutionResult.getArguments()).isEmpty();
        assertThat(integerExecutionResult.getResult()).isNotEmpty();
        assertThat(integerExecutionResult.getResult()).isEqualTo(integers);
    }


}
