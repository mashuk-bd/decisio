package io.github.mashukbd.decisio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class RuleTest {

    @Test
    public void testRuleMatches() {
        Predicate<String> predicate = s -> s.length() > 3;
        Consumer<String> action = s -> {};

        Rule<String> rule = new Rule<>(predicate, action);

        assertTrue(rule.matches("hello"));
        assertFalse(rule.matches("hi"));
    }

    @Test
    public void testRuleExecute() {
        final String[] result = new String[1];

        Predicate<String> predicate = s -> true;
        Consumer<String> action = s -> result[0] = s.toUpperCase();

        Rule<String> rule = new Rule<>(predicate, action);

        rule.execute("test");

        assertEquals("TEST", result[0]);
    }

    @Test
    public void testRuleWithInteger() {
        Predicate<Integer> predicate = n -> n % 2 == 0;
        Consumer<Integer> action = n -> {};

        Rule<Integer> rule = new Rule<>(predicate, action);

        assertTrue(rule.matches(4));
        assertFalse(rule.matches(5));
    }
}