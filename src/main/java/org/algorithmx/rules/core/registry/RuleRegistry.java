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

import org.algorithmx.rules.core.rule.Rule;
import org.algorithmx.rules.core.ruleset.RuleSet;

public interface RuleRegistry {

    static RuleRegistry create() {
        return new DefaultRuleRegistry();
    }

    boolean isNameInUse(String name);

    int getRuleCount();

    int getRuleSetCount();

    Object get(String name);

    Rule[] getRules();

    RuleSet[] getRuleSets();

    <T> Rule<T> getRule(String name);

    RuleSet getRuleSet(String name);

    void register(Rule rule);

    void register(RuleSet rules);
}
