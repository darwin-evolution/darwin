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
package com.github.darwinevolution.darwin.execution.result;

/**
 * Enum that contains values representing outcome of comparing results of executing protoplast and evolved implementations.
 */
public enum ResultType {

    /**
     * When {@link com.github.darwinevolution.darwin.evolution.evolved.Evolved}
     * and {@link com.github.darwinevolution.darwin.evolution.protoplast.Protoplast}
     * implementations both returned values,
     * and those values match according to {@link com.github.darwinevolution.darwin.api.ValueResultComparator#areValuesEqual(Object, Object)}
     */
    OK,

    /**
     * When {@link com.github.darwinevolution.darwin.evolution.evolved.Evolved}
     * and {@link com.github.darwinevolution.darwin.evolution.protoplast.Protoplast}
     * implementations both thrown exceptions,
     * and those exceptions match according to {@link com.github.darwinevolution.darwin.api.ExceptionResultComparator#areExceptionsEqual(Exception, Exception)}
     */
    OK_EXCEPTIONS,

    /**
     * When {@link com.github.darwinevolution.darwin.evolution.evolved.Evolved}
     * and {@link com.github.darwinevolution.darwin.evolution.protoplast.Protoplast}
     * implementations both thrown exceptions,
     * but those exceptions do not match according to {@link com.github.darwinevolution.darwin.api.ExceptionResultComparator#areExceptionsEqual(Exception, Exception)}
     */
    ERROR_DIFFERENT_EXCEPTIONS,

    /**
     * When {@link com.github.darwinevolution.darwin.evolution.evolved.Evolved}
     * and {@link com.github.darwinevolution.darwin.evolution.protoplast.Protoplast}
     * implementations both returned values,
     * but those values do not match according to {@link com.github.darwinevolution.darwin.api.ValueResultComparator#areValuesEqual(Object, Object)}
     */
    ERROR_DIFFERENT_RESULTS,

    /**
     * When either {@link com.github.darwinevolution.darwin.evolution.evolved.Evolved}
     * or {@link com.github.darwinevolution.darwin.evolution.protoplast.Protoplast}
     * implementation returned value and other thrown exception.
     */
    ERROR_EXCEPTION_VS_RESULT,

    /**
     * Initial undefined state.
     */
    INVALID_STATE;

    /**
     * @return true if result is some form of error
     */
    public boolean isError() {
        return this.equals(ERROR_DIFFERENT_EXCEPTIONS) || this.equals(ERROR_DIFFERENT_RESULTS) || this.equals(ERROR_EXCEPTION_VS_RESULT) || this.equals(INVALID_STATE);
    }

    /**
     * @return true if result is some form of success
     */
    public boolean isSuccess() {
        return this.equals(OK) || this.equals(OK_EXCEPTIONS);
    }
}
