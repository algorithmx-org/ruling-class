/**
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
package org.algorithmx.rules.core.registry;

import org.algorithmx.rules.core.Runnable;
import org.algorithmx.rules.core.rule.Rule;
import org.algorithmx.rules.core.ruleset.RuleSet;
import org.algorithmx.rules.lib.spring.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultRuleRegistry implements RuleRegistry {

    private final Map<String, Runnable> registry = new HashMap<>();

    public DefaultRuleRegistry() {
        super();
    }

    @Override
    public boolean isNameInUse(String name) {
        Assert.notNull(name, "name cannot be null.");
        return registry.containsKey(name);
    }

    @Override
    public int getRuleCount() {
        return getRules().length;
    }

    @Override
    public int getRuleSetCount() {
        return getRuleSets().length;
    }

    @Override
    public Rule[] getRules() {
        List<Rule> result = new ArrayList<>();

        for (Runnable runnable : registry.values()) {
            if (runnable instanceof Rule) result.add((Rule) runnable);
        }
        return result.toArray(new Rule[result.size()]);
    }

    @Override
    public RuleSet[] getRuleSets() {
        List<RuleSet> result = new ArrayList<>();

        for (Runnable runnable : registry.values()) {
            if (runnable instanceof RuleSet) result.add((RuleSet) runnable);
        }
        return result.toArray(new RuleSet[result.size()]);
    }


    @Override
    public Object get(String name) {
        Assert.notNull(name, "name cannot be null.");
        return registry.get(name);
    }

    @Override
    public <T> Rule<T> getRule(String name) {
        Assert.notNull(name, "name cannot be null.");
        return (Rule<T>) registry.get(name);
    }

    @Override
    public RuleSet getRuleSet(String name) {
        Assert.notNull(name, "name cannot be null.");
        return (RuleSet) registry.get(name);
    }

    @Override
    public void register(Rule rule) {
        Assert.notNull(rule, "rule cannot be null.");

        if (isNameInUse(rule.getName())) {
            throw new AlreadyRegisteredException(rule.getName(), rule);
        }

        registry.put(rule.getName(), rule);
    }

    @Override
    public void register(RuleSet rules) {
        Assert.notNull(rules, "rules cannot be null.");

        if (isNameInUse(rules.getName())) {
            throw new AlreadyRegisteredException(rules.getName(), rules);
        }

    }
}
