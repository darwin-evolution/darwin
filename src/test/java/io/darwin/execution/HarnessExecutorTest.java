/**
 * The MIT License
 *
 * Copyright (c) 2016 Darwin contributors
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
package io.darwin.execution;

import io.darwin.execution.harness.ProtoplastExecutionHarness;
import io.darwin.execution.result.ExecutionResult;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class HarnessExecutorTest {

    @Test
    public void shouldCatchAnyExceptionThrownFromHarness() {
        //given
//        final IllegalStateException illegalStateException = new IllegalStateException();
//
//        //when
//        ExecutionResult<Integer> integerExecutionResult = new HarnessExecutor<Integer>()
//                .executeHarness(new ProtoplastExecutionHarness<Integer>() {
//                    @Override
//                    public Integer execute() throws Exception {
//                        throw illegalStateException;
//                    }
//                });
//
//        //then
//        assertThat(integerExecutionResult.getException()).isEqualTo(illegalStateException);
//        assertThat(integerExecutionResult.getArguments()).isEmpty();
//        assertThat(integerExecutionResult.getResult()).isNull();
//    }
//
//    @Test
//    public void shouldReturnIntegerResultFromHarness() {
//        //given
//        final Integer result = 100;
//
//        //when
//        ExecutionResult<Integer> integerExecutionResult = new HarnessExecutor<Integer>()
//                .executeHarness(new ProtoplastExecutionHarness<Integer>() {
//                    @Override
//                    public Integer execute() throws Exception {
//                        return result;
//                    }
//                });
//
//        //then
//        assertThat(integerExecutionResult.getException()).isNull();
//        assertThat(integerExecutionResult.getArguments()).isEmpty();
//        assertThat(integerExecutionResult.getResult()).isEqualTo(result);
//    }
//
//    @Test
//    public void shouldReturnBooleanResultFromHarness() {
//        //given
//
//        //when
//        ExecutionResult<Boolean> integerExecutionResult = new HarnessExecutor<Boolean>()
//                .executeHarness(new ProtoplastExecutionHarness<Boolean>() {
//                    @Override
//                    public Boolean execute() throws Exception {
//                        return false;
//                    }
//                });
//
//        //then
//        assertThat(integerExecutionResult.getException()).isNull();
//        assertThat(integerExecutionResult.getArguments()).isEmpty();
//        assertThat(integerExecutionResult.getResult()).isFalse();
//    }
//
//    @Test
//    public void shouldReturnIntegerArrayResultFromHarness() {
//        //given
//        final Integer[] integers = {1,2};
//
//        //when
//        ExecutionResult<Integer[]> integerExecutionResult = new HarnessExecutor<Integer[]>()
//                .executeHarness(new ProtoplastExecutionHarness<Integer[]>() {
//                    @Override
//                    public Integer[] execute() throws Exception {
//                        return integers;
//                    }
//                });
//
//        //then
//        assertThat(integerExecutionResult.getException()).isNull();
//        assertThat(integerExecutionResult.getArguments()).isEmpty();
//        assertThat(integerExecutionResult.getResult()).isNotEmpty();
//        assertThat(integerExecutionResult.getResult()).isEqualTo(integers);
//    }
//
//
//    @Test
//    public void shouldReturnIntegerListResultFromHarness() {
//        //given
//        final List<Integer> integers = new ArrayList<Integer>();
//        integers.add(1);
//        integers.add(2);
//
//        //when
//        ExecutionResult<List<Integer>> integerExecutionResult = new HarnessExecutor<List<Integer>>()
//                .executeHarness(new ProtoplastExecutionHarness<List<Integer>>() {
//                    @Override
//                    public List<Integer> execute() throws Exception {
//                        return integers;
//                    }
//                });
//
//        //then
//        assertThat(integerExecutionResult.getException()).isNull();
//        assertThat(integerExecutionResult.getArguments()).isEmpty();
//        assertThat(integerExecutionResult.getResult()).isNotEmpty();
//        assertThat(integerExecutionResult.getResult()).isEqualTo(integers);
    }


}
