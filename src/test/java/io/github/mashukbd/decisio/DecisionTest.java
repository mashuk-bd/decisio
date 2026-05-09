package io.github.mashukbd.decisio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class DecisionTest {

    @Test
    public void testSingleRuleMatches() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
            .when(s -> s.length() > 3)
            .then(s -> result.append("long"))
            .otherwise(s -> result.append("short"));

        assertEquals("long", result.toString());
    }

    @Test
    public void testSingleRuleDoesNotMatch() {
        StringBuilder result = new StringBuilder();

        Decision.of("hi")
            .when(s -> s.length() > 3)
            .then(s -> result.append("long"))
            .otherwise(s -> result.append("short"));

        assertEquals("short", result.toString());
    }

    @Test
    public void testMultipleRulesFirstMatches() {
        StringBuilder result = new StringBuilder();

        Decision.of(5)
            .when(n -> n > 3)
            .then(n -> result.append("greater"))
            .when(n -> n < 10)
            .then(n -> result.append("less"))
            .otherwise(n -> result.append("other"));

        assertEquals("greater", result.toString());
    }

    @Test
    public void testMultipleRulesSecondMatches() {
        StringBuilder result = new StringBuilder();

        Decision.of(2)
            .when(n -> n > 3)
            .then(n -> result.append("greater"))
            .when(n -> n < 10)
            .then(n -> result.append("less"))
            .otherwise(n -> result.append("other"));

        assertEquals("less", result.toString());
    }

    @Test
    public void testNoRulesMatchOtherwise() {
        StringBuilder result = new StringBuilder();

        Decision.of(15)
            .when(n -> n > 20)
            .then(n -> result.append("big"))
            .when(n -> n < 5)
            .then(n -> result.append("small"))
            .otherwise(n -> result.append("medium"));

        assertEquals("medium", result.toString());
    }

    @Test
    public void testOnlyOtherwise() {
        StringBuilder result = new StringBuilder();

        Decision.of("test")
            .otherwise(s -> result.append("default"));

        assertEquals("default", result.toString());
    }

    @Test
    public void testAndCondition() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
            .when(s -> s.length() > 3)
            .and(s -> s.startsWith("h"))
            .then(s -> result.append("match"))
            .otherwise(s -> result.append("no match"));

        assertEquals("match", result.toString());
    }

    @Test
    public void testAndConditionFails() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
            .when(s -> s.length() > 3)
            .and(s -> s.startsWith("w"))
            .then(s -> result.append("match"))
            .otherwise(s -> result.append("no match"));

        assertEquals("no match", result.toString());
    }

    @Test
    public void testOrCondition() {
        StringBuilder result = new StringBuilder();

        Decision.of("world")
            .when(s -> s.length() > 10)
            .or(s -> s.startsWith("w"))
            .then(s -> result.append("match"))
            .otherwise(s -> result.append("no match"));

        assertEquals("match", result.toString());
    }

    @Test
    public void testOrConditionFails() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
            .when(s -> s.length() > 10)
            .or(s -> s.startsWith("w"))
            .then(s -> result.append("match"))
            .otherwise(s -> result.append("no match"));

        assertEquals("no match", result.toString());
    }

    @Test
    public void testComplexChaining() {
        StringBuilder result = new StringBuilder();

        Decision.of(7)
            .when(n -> n > 5)
            .and(n -> n < 10)
            .then(n -> result.append("range"))
            .when(n -> n % 2 == 0)
            .then(n -> result.append("even"))
            .otherwise(n -> result.append("other"));

        assertEquals("range", result.toString());
    }

    @Test
    public void testIntegerDecision() {
        StringBuilder result = new StringBuilder();

        Decision.of(42)
            .when(n -> n == 42)
            .then(n -> result.append("answer"))
            .otherwise(n -> result.append("not answer"));

        assertEquals("answer", result.toString());
    }

    @Test
    public void testNullValue() {
        StringBuilder result = new StringBuilder();

        Decision.of((String) null)
            .when(s -> s != null)
            .then(s -> result.append("not null"))
            .otherwise(s -> result.append("null"));

        assertEquals("null", result.toString());
    }

    @Test
    public void testActionReceivesCorrectValue() {
        final String[] captured = new String[1];

        Decision.of("test value")
            .when(s -> true)
            .then(s -> captured[0] = s)
            .otherwise(s -> captured[0] = "default");

        assertEquals("test value", captured[0]);
    }

    @Test
    public void testAndNotCondition() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
                .when(s -> s.length() > 3)
                .andNot(s -> s.startsWith("w"))
                .then(s -> result.append("match"))
                .otherwise(s -> result.append("no match"));

        assertEquals("match", result.toString());
    }

    @Test
    public void testAndNotConditionFails() {
        StringBuilder result = new StringBuilder();

        Decision.of("world")
                .when(s -> s.length() > 3)
                .andNot(s -> s.startsWith("w"))
                .then(s -> result.append("match"))
                .otherwise(s -> result.append("no match"));

        assertEquals("no match", result.toString());
    }

    @Test
    public void testOrNotCondition() {
        StringBuilder result = new StringBuilder();

        Decision.of("hello")
                .when(s -> s.length() > 10)
                .orNot(s -> s.startsWith("w"))
                .then(s -> result.append("match"))
                .otherwise(s -> result.append("no match"));

        assertEquals("match", result.toString());
    }

    @Test
    public void testOrNotConditionFails() {
        StringBuilder result = new StringBuilder();

        Decision.of("world")
                .when(s -> s.length() > 10)
                .orNot(s -> s.startsWith("w"))
                .then(s -> result.append("match"))
                .otherwise(s -> result.append("no match"));

        assertEquals("no match", result.toString());
    }
}