package io.github.mashukbd.decisio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Decision<T> {

    private final T value;

    private final List<Rule<T>> rules = new ArrayList<>();

    private Consumer<T> otherwiseAction;

    private Decision(T value) {
        this.value = value;
    }

    public static <T> Decision<T> of(T value) {
        return new Decision<>(value);
    }

    public ConditionBuilder when(Predicate<T> predicate) {
        return new ConditionBuilder(predicate);
    }

    public void otherwise(Consumer<T> action) {

        this.otherwiseAction = action;

        evaluate();
    }

    private void evaluate() {

        for (Rule<T> rule : rules) {

            if (rule.matches(value)) {
                rule.execute(value);
                return;
            }
        }

        if (otherwiseAction != null) {
            otherwiseAction.accept(value);
        }
    }

    public class ConditionBuilder {

        private Predicate<T> predicate;

        public ConditionBuilder(Predicate<T> predicate) {
            this.predicate = predicate;
        }

        public ConditionBuilder and(Predicate<T> other) {
            predicate = predicate.and(other);
            return this;
        }

        public ConditionBuilder or(Predicate<T> other) {
            predicate = predicate.or(other);
            return this;
        }

        public ConditionBuilder andNot(Predicate<T> other) {
            predicate = predicate.and(other.negate());
            return this;
        }

        public ConditionBuilder orNot(Predicate<T> other) {
            predicate = predicate.or(other.negate());
            return this;
        }

        public Decision<T> then(Consumer<T> action) {
            rules.add(new Rule<>(predicate, action));
            return Decision.this;
        }
    }
}
