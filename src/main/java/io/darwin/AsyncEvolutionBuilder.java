package io.darwin;

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
