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

import org.algorithmx.rules.bind.Bindings;
import org.algorithmx.rules.core.condition.ConditionBuilder;
import org.algorithmx.rules.core.context.RuleContext;
import org.algorithmx.rules.core.context.RuleContextBuilder;
import org.algorithmx.rules.core.rule.RuleBuilder;
import org.algorithmx.rules.core.ruleset.RuleSet;
import org.algorithmx.rules.core.ruleset.RuleSetBuilder;
import org.algorithmx.rules.validation.rules.binding.MustBeDefinedRule;
import org.algorithmx.rules.validation.rules.binding.MustNotBeDefinedRule;
import org.algorithmx.rules.validation.rules.future.FutureValidationRule;
import org.algorithmx.rules.validation.rules.max.MaxValidationRule;
import org.algorithmx.rules.validation.rules.min.MinValidationRule;
import org.algorithmx.rules.validation.rules.notblank.NotBlankValidationRule;
import org.algorithmx.rules.validation.rules.notnull.NotNullValidationRule;
import org.algorithmx.rules.validation.rules.nulll.NullValidationRule;
import org.algorithmx.rules.validation.rules.past.PastValidationRule;
import org.algorithmx.rules.validation.rules.pattern.PatternValidationRule;
import org.algorithmx.rules.validation.rules.size.SizeValidationRule;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test cases for the various Validation Rules.
 *
 * @author Max Arulananthan.
 */
public class ValidationTest {

    public ValidationTest() {
        super();
    }

    @Test
    public void test1() {
        RuleSet rules = RuleSetBuilder
                .with("RuleSet1", "Test Rule Set")
                    .rule(RuleBuilder.build(TestRule1.class))
                    .rule(RuleBuilder.build(TestRule2.class))
                    .rule(RuleBuilder.build(TestRule3.class))
                    .rule(RuleBuilder.build(TestRule4.class))
                    .rule(RuleBuilder.build(TestRule5.class))
                .build();

        Bindings bindings = Bindings.create()
                .bind("value", Integer.class, 1)
                .bind("errors", RuleViolations.class, new RuleViolations());

        rules.run(RuleContextBuilder.with(bindings).build());
    }

    @Test
    public void test2() {
        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(TestRule1.class))
                    .rule(RuleBuilder.build(TestRule2.class))
                    .rule(RuleBuilder.build(TestRule3.class))
                    .rule(RuleBuilder.build(TestRule4.class))
                    .rule(RuleBuilder.build(TestRule5.class))
                .build();

        rules.run(RuleContextBuilder.
                with(Bindings.create()
                        .bind(value -> 75)
                        .bind(errors -> new RuleViolations())).build());
    }

    @Test
    public void testNotNullRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value",1)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(new NotNullValidationRule()))
                .build();

        RuleContext context = RuleContextBuilder.with(bindings).build();
        rules.run(context);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void testNullRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("b", String.class, null)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(new NullValidationRule()))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void testStringLengthRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", String.class, "  ")
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(new SizeValidationRule(1, Integer.MAX_VALUE)))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void testStringTextRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", String.class, "  a")
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(new NotBlankValidationRule()))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void testStringPatternRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", String.class, "ababab")
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet2", "Test Rule Set")
                    .rule(RuleBuilder.build(new PatternValidationRule("[z]*")))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 1);
    }


    @Test
    public void testFutureDateRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", new Date())
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .rule(RuleBuilder.build(new FutureValidationRule()))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
    }

    @Test
    public void tesPastDateRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", new Date())
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .rule(RuleBuilder.build(new PastValidationRule()))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 0);
    }


    @Test
    public void testMaxRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", 25)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .rule(RuleBuilder.build(new MaxValidationRule(50)))
                    .rule(RuleBuilder.build(new MaxValidationRule(20)))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 1);
    }

    @Test
    public void testMinRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("value", 10)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .rule(RuleBuilder.build(new MinValidationRule(11)))
                    .rule(RuleBuilder.build(new MinValidationRule(5)))
                    .rule(RuleBuilder.build(new MinValidationRule(25)))
                    .rule(RuleBuilder.build(new MinValidationRule(25)))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 3);
    }

    @Test
    public void testMustBeDefinedRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("a", 22)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .preCondition(ConditionBuilder.build(() -> true))
                    .rule(RuleBuilder.build(new MustBeDefinedRule("c")))
                    .rule(RuleBuilder.build(new MustBeDefinedRule("a")))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 1);
    }

    @Test
    public void testMustNotBeDefinedRule() {
        RuleViolations errors = new RuleViolations();
        Bindings bindings = Bindings.create()
                .bind("a", 22)
                .bind("e", errors);

        RuleSet rules = RuleSetBuilder
                .with("RuleSet", "Test Rule Set")
                    .rule(RuleBuilder.build(new MustNotBeDefinedRule("a")))
                    .rule(RuleBuilder.build(new MustNotBeDefinedRule("c")))
                .build();

        rules.run(RuleContextBuilder.with(bindings).build());
        Assert.assertTrue(errors.size() == 1);
    }
}
