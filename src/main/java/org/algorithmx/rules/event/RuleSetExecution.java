package org.algorithmx.rules.event;

import org.algorithmx.rules.core.ruleset.RuleSet;
import org.algorithmx.rules.lib.spring.util.Assert;

public class RuleSetExecution<T> {

    private final RuleSet rules;
    private final T executingElement;

    public RuleSetExecution(RuleSet rules, T executingElement) {
        super();
        Assert.notNull(rules, "rules cannot be null.");
        this.rules = rules;
        this.executingElement = executingElement;
    }

    public RuleSet getRules() {
        return rules;
    }

    public T getExecutingElement() {
        return executingElement;
    }

    @Override
    public String toString() {
        return "RuleSetExecution{" +
                "rules=" + rules +
                ", executingElement=" + executingElement +
                '}';
    }
}
