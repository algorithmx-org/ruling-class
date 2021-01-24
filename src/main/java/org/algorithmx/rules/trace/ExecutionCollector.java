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
package org.algorithmx.rules.trace;

import org.algorithmx.rules.bind.match.ParameterMatch;
import org.algorithmx.rules.core.action.Action;
import org.algorithmx.rules.core.condition.Condition;
import org.algorithmx.rules.core.model.MethodDefinition;
import org.algorithmx.rules.event.ActionExecution;
import org.algorithmx.rules.event.ConditionExecution;
import org.algorithmx.rules.event.ExecutionEvent;
import org.algorithmx.rules.event.FunctionExecution;
import org.algorithmx.rules.event.RuleExecution;
import org.algorithmx.rules.event.RuleSetExecution;
import org.algorithmx.rules.util.RuleUtils;

public abstract class ExecutionCollector extends ExecutionTracer {

    private final boolean detailed;
    private int tabCount = 0;

    public ExecutionCollector() {
        this(false);
    }

    public ExecutionCollector(boolean detailed) {
        super();
        this.detailed = detailed;
    }

    public void collect(ExecutionEvent event, String executionText) {}

    @Override
    public void onFunction(ExecutionEvent<FunctionExecution> event) {
        collect(event, createLog("Function Result : " + event.getData().getResult(),
                event.getData().getMethodDefinition(), event.getData().getParameterMatches(),
                event.getData().getValues(), tabCount + 1));
    }

    @Override
    public void onCondition(ExecutionEvent<ConditionExecution> event) {
        collect(event, createLog("Condition Result : " + event.getData().getResult(),
                event.getData().getMethodDefinition(), event.getData().getParameterMatches(),
                event.getData().getValues(), tabCount + 1));
    }

    @Override
    public void onAction(ExecutionEvent<ActionExecution> event) {
        collect(event, createLog("Action Result : Executed",
                event.getData().getMethodDefinition(), event.getData().getParameterMatches(),
                event.getData().getValues(), tabCount + 1));
    }

    protected String createLog(String description, MethodDefinition methodDefinition,
                               ParameterMatch[] matches, Object[] values, int tabCount) {
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append(description);

        if (isDetailed()) {
            result.append(System.lineSeparator());
            result.append(RuleUtils.getMethodDescription(methodDefinition, matches, values, RuleUtils.getTabs(tabCount)));
        }

        return result.toString();
    }

    @Override
    public void onRuleStart(ExecutionEvent<RuleExecution> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("Rule : " + event.getData().getRule().getTarget());
        collect(event, result.toString());
    }

    @Override
    public void onRuleEnd(ExecutionEvent<RuleExecution> event) {
        tabCount--;
    }

    @Override
    public void onRulePreConditionStart(ExecutionEvent<RuleExecution<Condition>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("Rule Pre Condition : ");
        collect(event, result.toString());
    }

    @Override
    public void onRulePreConditionEnd(ExecutionEvent<RuleExecution<Condition>> event) {
        tabCount--;
    }

    @Override
    public void onRuleConditionStart(ExecutionEvent<RuleExecution<Condition>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("Given : ");
        collect(event, result.toString());
    }

    @Override
    public void onRuleConditionEnd(ExecutionEvent<RuleExecution<Condition>> event) {
        tabCount--;
    }

    @Override
    public void onRuleActionStart(ExecutionEvent<RuleExecution<Action>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("Action : ");
        collect(event, result.toString());
    }

    @Override
    public void onRuleActionEnd(ExecutionEvent<RuleExecution<Action>> event) {
        tabCount--;
    }

    @Override
    public void onRuleOtherwiseActionStart(ExecutionEvent<RuleExecution<Action>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("Otherwise : ");
        collect(event, result.toString());
    }

    @Override
    public void onRuleOtherwiseActionEnd(ExecutionEvent<RuleExecution<Action>> event) {
        tabCount--;
    }

    @Override
    public void onRuleSetStart(ExecutionEvent<RuleSetExecution> event) {
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("RuleSet : " + event.getData().getRules().getName());

        if (event.getData().getRules().getDescription() != null) {
            result.append(System.lineSeparator());
            result.append("Description : " + event.getData().getRules().getDescription());
        }

        result.append(System.lineSeparator());
        collect(event, result.toString());
    }

    @Override
    public void onRuleSetEnd(ExecutionEvent<RuleSetExecution> event) {}

    @Override
    public void onRuleSetPreConditionStart(ExecutionEvent<RuleSetExecution<Condition>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("RuleSet Pre Condition : ");
        collect(event, result.toString());
    }

    @Override
    public void onRuleSetPreConditionEnd(ExecutionEvent<RuleSetExecution<Condition>> event) {
        tabCount--;
    }

    @Override
    public void onRuleSetStopConditionStart(ExecutionEvent<RuleSetExecution<Condition>> event) {
        tabCount++;
        StringBuilder result = new StringBuilder();
        result.append(RuleUtils.getTabs(tabCount));
        result.append("RuleSet Stop Condition : ");
        collect(event, result.toString());
    }

    @Override
    public void onRuleSetStopConditionEnd(ExecutionEvent<RuleSetExecution<Condition>> event) {
        tabCount--;
    }

    public boolean isDetailed() {
        return detailed;
    }
}
