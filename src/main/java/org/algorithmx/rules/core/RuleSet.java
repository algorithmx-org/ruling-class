/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 2019, algorithmx.org (dev@algorithmx.org)
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
package org.algorithmx.rules.core;

/**
 * RuleSet is a logical grouping of Rules.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public interface RuleSet extends Identifiable, Iterable<Rule> {

    /**
     * Ruleset name.
     *
     * @return name.
     */
    String getName();

    /**
     * RuleSet description.
     *
     * @return description.
     */
    String getDescription();

    /**
     * PreCondition(Optional) Condition to be met before the execution of the Rules.
     *
     * @param condition pre check before execution of the Rules.
     */
    void preCondition(Condition condition);

    /**
     * Returns the Condition (if one exists) to be met before the execution of the Rules.
     *
     * @return pre check before execution of the Rules.
     */
    Condition getPreCondition();

    /**
     * PreAction(Optional) to be performed after the execution of the Rules.
     *
     * @param action pre action before the execution of the Rules.
     */
    void preAction(Action action);

    /**
     * Returns the action performed after the execution of the Rules.
     *
     * @return pre action before the execution of the Rules.
     */
    Action getPreAction();

    /**
     * PostAction(Optional) to be performed after the execution of the Rules.
     *
     * @param action post action after the execution of the Rules.
     */
    void postAction(Action action);

    /**
     * Returns the action to be performed after the execution of the Rules.
     *
     * @return post action after the execution of the Rules.
     */
    Action getPostAction();

    /**
     * Condition that determines when execution should stop.
     *
     * @param condition stopping condition.
     */
    void stopWhen(Condition condition);

    /**
     * Returns the Condition that determines when execution should stop.
     *
     * @return stopping condition.
     */
    Condition getStopCondition();

    /**
     * Retrieves a Rule with the given name in this RuleSet.
     *
     * @param ruleName desired rule name.
     * @return Rule if found; null otherwise.
     */
    Rule getRule(String ruleName);

    /**
     * Retrieves a Rule with the given implementation class in this RuleSet.
     *
     * @param ruleClass implementation class.
     * @return Rule if found; null otherwise.
     */
    Rule getRule(Class<?> ruleClass);

    /**
     * Size of this RuleSet (ie. number of Rules in this RuleSet)
     *
     * @return number of Rules in this RuleSet.
     */
    int size();

    /**
     * Returns all the Rules in this RuleSet.
     *
     * @return rules.
     */
    Rule[] getRules();

    /**
     * Adds the given Rule to this RuleSet.
     *
     * @param rule Rule to be added.
     * @return this RuleSet (for fluency).
     */
    RuleSet add(Rule rule);

    /**
     * Adds a new Rule to this RuleSet.
     *
     * @param ruleClass Rule class to be added.
     * @return this RuleSet (for fluency).
     */
    RuleSet add(Class<?> ruleClass);

    /**
     * Adds the given Rule to this RuleSet with the desired name.
     *
     * @param name desired name.
     * @param rule Rule to be added.
     * @return this RuleSet (for fluency).
     */
    RuleSet add(String name, Rule rule);

    /**
     * Adds the given Rule to this RuleSet with the desired name.
     *
     * @param name name of the Rule.
     * @param given when condition.
     * @param actions then actions.
     * @return this RuleSet (for fluency).
     */
    RuleSet add(String name, ConditionConsumer given, ActionConsumer...actions);

    /**
     * Adds the given Rule to this RuleSet with the desired name.
     *
     * @param name name of the Rule.
     * @param given when condition.
     * @param then then action.
     * @param otherwise otherwise action.
     * @return this RuleSet (for fluency).
     */
    RuleSet add(String name, ConditionConsumer given, ActionConsumer then, ActionConsumer otherwise);

    /**
     * Removes the listed Rules from this RuleSet.
     *
     * @param ruleNames Rules to exclude.
     * @return this RuleSet (minus the excluded Rules) for fluency.
     * @throws org.algorithmx.rules.error.UnrulyException if any of the excluded Rules do not exist in this RuleSet.
     */
    RuleSet remove(String...ruleNames);

    /**
     * Removes the listed Rules from this RuleSet.
     *
     * @param ruleClasses Rules to exclude.
     * @return this RuleSet (minus the excluded Rules) for fluency.
     * @throws org.algorithmx.rules.error.UnrulyException if any of the excluded Rules do not exist in this RuleSet.
     */
    RuleSet remove(Class<?>...ruleClasses);
}

