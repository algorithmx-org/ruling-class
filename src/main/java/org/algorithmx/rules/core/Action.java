package org.algorithmx.rules.core;

import org.algorithmx.rules.bind.BindingDeclaration;
import org.algorithmx.rules.bind.Bindings;

@FunctionalInterface
public interface Action {

    void run(RuleExecutionContext ctx) throws UnrulyException;

    default void run(BindingDeclaration... bindings) {
        run(Bindings.simpleBindings().bind(bindings));
    }

    default void run(Bindings bindings) throws UnrulyException {
        run(RuleExecutionContext.create(bindings));
    }
}
