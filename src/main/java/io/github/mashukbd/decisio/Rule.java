package io.github.mashukbd.decisio;

import java.util.function.Predicate;
import java.util.function.Consumer;

public class Rule<T> {

    private Predicate<T> condition;

    private Consumer<T> action;

    public Rule(Predicate<T> condition, Consumer<T> action) {
        this.condition = condition;
        this.action = action;
    }

    public boolean matches(T value) {
        return condition.test(value);
    }

    public void execute(T value) {
        action.accept(value);
    }
}
