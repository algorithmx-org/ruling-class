/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 1999-2019, Live Software & Consultants Inc (rules@algorithmx.org)
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
package org.algorithmx.rules.core.rule;

import org.algorithmx.rules.core.condition.Condition;
import org.algorithmx.rules.lib.spring.util.Assert;
import org.algorithmx.rules.util.reflect.ObjectFactory;

/**
 * Builder class for creating Rule instances.
 *
 * @author Max Arulananthan.
 * @since 1.0
 */
public abstract class RuleBuilder {

    public static <T> ClassBasedRuleBuilder<T> with(Class<T> ruleClass) {
        Assert.notNull(ruleClass, "ruleClass cannot be null.");
        return with(ruleClass, ObjectFactory.create());
    }

    public static <T> Rule<T> build(Class<T> ruleClass) {
        return with(ruleClass).build();
    }

    public static <T> ClassBasedRuleBuilder<T> withTarget(T ruleTarget) {
        Assert.notNull(ruleTarget, "ruleTargetCannot be null");
        return ClassBasedRuleBuilder.with((Class<T>) ruleTarget.getClass(), ruleTarget);
    }

    public static <T> Rule<T> build(T ruleTarget) {
        return withTarget(ruleTarget).build();
    }

    public static <T> ClassBasedRuleBuilder<T> with(Class<T> ruleClass, ObjectFactory objectFactory) {
        Assert.notNull(ruleClass, "ruleClass cannot be null.");
        Assert.notNull(objectFactory, "objectFactory cannot be null.");
        return ClassBasedRuleBuilder.with(ruleClass, objectFactory.createRule(ruleClass));
    }

    public static <T> Rule<T> build(Class<T> ruleClass, ObjectFactory objectFactory) {
        return with(ruleClass, objectFactory).build();
    }

    public static LambdaBasedRuleBuilder<?> given(Condition condition) {
        Assert.notNull(condition, "condition cannot be null.");
        return new LambdaBasedRuleBuilder(condition);
    }
}
