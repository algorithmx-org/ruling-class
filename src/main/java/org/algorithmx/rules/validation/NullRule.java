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
package org.algorithmx.rules.validation;

import org.algorithmx.rules.annotation.Description;
import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Match;
import org.algorithmx.rules.annotation.Otherwise;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.bind.match.MatchByTypeMatchingStrategy;
import org.algorithmx.rules.core.context.RuleContext;
import org.algorithmx.rules.lib.spring.util.Assert;

import java.util.function.Supplier;

/**
 * Must be Null validation Rule. Check to make sure the value is null.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
@Rule
@Description("Given value must be null.")
public class NullRule extends ValidationRule {

    private static final String ERROR_CODE      = "validators.nullRule";
    private static final String DEFAULT_MESSAGE = "Value must be null.";

    private final Supplier<Object> supplier;

    public NullRule(Object value) {
        this(() -> value);
    }

    public NullRule(Supplier<Object> supplier) {
        super(ERROR_CODE, Severity.ERROR, DEFAULT_MESSAGE);
        Assert.notNull(supplier, "supplier cannot be null.");
        this.supplier = supplier;
    }

    @Given
    public boolean isValid() {
        return supplier.get() == null;
    }

    @Otherwise
    public void otherwise(@Match(using = MatchByTypeMatchingStrategy.class) RuleContext context,
                          @Match(using = MatchByTypeMatchingStrategy.class) RuleViolations errors) {
        errors.add(createRuleViolationBuilder().build(context));
    }

    @Override
    public String toString() {
        return "NullRule{" +
                "value=" + supplier.get() +
                '}';
    }
}
