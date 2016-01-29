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
package io.darwin.async;

import io.darwin.Evolution;

import java.util.concurrent.TimeUnit;

public class AsyncEvolutionBuilder<T> {

    private Evolution.EvolutionBuilder<T> evolutionBuilder;

    public AsyncEvolutionBuilder(Evolution.EvolutionBuilder<T> evolutionBuilder) {
        this.evolutionBuilder = evolutionBuilder;
    }

    public AsyncEvolutionBuilder in(EvolutionTimeout evolutionTimeout){

        return this;
    }

    public static class EvolutionTimeout {
        private Long time;
        private TimeUnit timeUnit;

        public EvolutionTimeout(Long time, TimeUnit timeUnit) {
            this.time = time;
            this.timeUnit = timeUnit;
        }

        public Long getTime() {
            return time;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EvolutionTimeout)) return false;

            EvolutionTimeout that = (EvolutionTimeout) o;

            if (time != null ? !time.equals(that.time) : that.time != null) return false;
            return timeUnit == that.timeUnit;

        }

        @Override
        public int hashCode() {
            int result = time != null ? time.hashCode() : 0;
            result = 31 * result + (timeUnit != null ? timeUnit.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "EvolutionTimeout{" +
                    "time=" + time +
                    ", timeUnit=" + timeUnit +
                    '}';
        }
    }
}
