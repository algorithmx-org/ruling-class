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

package org.algorithmx.rulii.core.test.function;

import org.algorithmx.rulii.core.function.Function;
import org.algorithmx.rulii.core.function.FunctionBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class FunctionBuilderTest {

    public FunctionBuilderTest() {
        super();
    }

    @Test
    public void testNoArg() {
        Function<Boolean> function = FunctionBuilder
                .with(() -> true)
                .name("function0")
                .build();

        Assert.assertTrue(function.getMethodDefinition().getName().equals("function0"));
        Assert.assertTrue(function.getMethodDefinition().getParameterDefinitions().length == 0);
    }

    @Test
    public void test2Args() {
        Function<BigDecimal> function = FunctionBuilder
                .with((String x, BigDecimal value) -> value)
                .build();

        Assert.assertTrue(function.getMethodDefinition().getParameterDefinitions().length == 2);
        Assert.assertTrue(function.getMethodDefinition().getParameterDefinitions()[1].getName().equals("value"));
        Assert.assertTrue(function.getMethodDefinition().getParameterDefinitions()[1].getType().equals(BigDecimal.class));

        function.apply("123", new BigDecimal("10.00"));
    }
}
