/*
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 1999-2021, Algorithmx Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.algorithmx.rulii.core.condition;

import org.algorithmx.rulii.bind.match.ParameterMatch;
import org.algorithmx.rulii.core.UnrulyException;
import org.algorithmx.rulii.core.model.MethodDefinition;
import org.algorithmx.rulii.core.context.RuleContext;
import org.algorithmx.rulii.event.ConditionExecution;
import org.algorithmx.rulii.event.EventType;
import org.algorithmx.rulii.event.ExecutionEvent;
import org.algorithmx.rulii.lib.spring.util.Assert;
import org.algorithmx.rulii.util.RuleUtils;
import org.algorithmx.rulii.util.reflect.MethodExecutor;

/**
 * Default Condition implementation.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class DefaultCondition implements Condition {

    private final MethodDefinition methodDefinition;
    private final MethodExecutor methodExecutor;
    private final Object target;

    public DefaultCondition(Object target, MethodDefinition methodDefinition) {
        super();
        Assert.notNull(methodDefinition, "methodDefinition cannot be null.");
        this.methodDefinition = methodDefinition;
        this.target = target;
        this.methodExecutor = MethodExecutor.create(methodDefinition.getMethod());
    }

    @Override
    public boolean isTrue(RuleContext context) throws ConditionExecutionException {
        Assert.notNull(context, "context cannot be null.");

        if (!context.isActive()) throw new UnrulyException("RuleContext is not Active. Perhaps it was stopped earlier ? "
                + "Create a new RuleContext and try again.");

        ParameterMatch[] matches = null;
        Object[] values = null;
        ExecutionEvent<ConditionExecution> event = null;

        try {
            matches = context.match(getMethodDefinition());
            values = context.resolve(matches, getMethodDefinition());
            boolean result = isTrue(values);
            event = new ExecutionEvent(EventType.ON_CONDITION,
                    new ConditionExecution(this, result, getMethodDefinition(), RuleUtils.immutable(matches), values));
            return result;
        } catch (Exception e) {
            event = new ExecutionEvent(EventType.ON_CONDITION,
                    new ConditionExecution(this, e, getMethodDefinition(), RuleUtils.immutable(matches), values));
            throw new ConditionExecutionException("Unexpected error occurred trying to execute Condition.",
                    e, this, matches, values);
        } finally {
            if (event != null) context.getEventProcessor().fireListeners(event);
        }
    }

    @Override
    public boolean isTrue(Object... args) throws UnrulyException {
        // Execute the Action Method
        Object result = methodExecutor.execute(target, args);

        if (result == null) throw new UnrulyException("Condition excepts a boolean return type. Actual [null]");
        if (!(result instanceof Boolean)) throw new UnrulyException("Condition excepts a boolean return type. " +
                "Actual [" + result.getClass().getSimpleName() + "]");

        return (Boolean) result;
    }

    @Override
    public final MethodDefinition getMethodDefinition() {
        return methodDefinition;
    }

    @Override
    public final Object getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "DefaultCondition{" +
                "methodDefinition=" + methodDefinition +
                ", methodExecutor=" + methodExecutor +
                ", target=" + target +
                '}';
    }
}
