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
package org.algorithmx.rules.bind;

import org.algorithmx.rules.core.condition.Condition;
import org.algorithmx.rules.core.condition.ConditionBuilder;
import org.algorithmx.rules.core.condition.TriCondition;
import org.algorithmx.rules.core.model.ParameterDefinition;
import org.algorithmx.rules.util.LambdaUtils;
import org.algorithmx.rules.util.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Binding Parameter tests.
 *
 * @author Max Arulananthan
 */
public class ParameterResolverTest {

    public ParameterResolverTest() {
        super();
    }

    @Test
    public void testParameterTypes() throws NoSuchMethodException {
        Method m = TestClass.class.getDeclaredMethod("testMethod0", String.class, List.class, Binding.class, Map.class);
        ParameterDefinition[] parameters = ParameterDefinition.load(m);

        Assert.assertTrue(parameters[0].getType().equals(String.class));
        Assert.assertTrue(parameters[1].getType().equals(new TypeReference<List<Integer>>(){}.getType()));
        Assert.assertTrue(parameters[2].getType().equals(new TypeReference<Binding<List<Integer>>>(){}.getType()));
        Assert.assertTrue(parameters[2].isBinding());
        Assert.assertTrue(parameters[3].getType().equals(new TypeReference<Map<?, Long>>(){}.getType()));
    }

    // TODO : Fix me
    @Test
    public void testBindableParameter1() throws NoSuchMethodException {
        Method m = TestClass.class.getDeclaredMethod("testMethod1", int.class, Map.class);
        ParameterDefinition[] parameters = ParameterDefinition.load(m);
        Assert.assertTrue(!parameters[0].isBinding() && parameters[0].getType().equals(int.class));
        Assert.assertTrue(!parameters[1].isBinding());
    }

    @Test
    public void testBindableParameter2() throws NoSuchMethodException {
        Method m = TestClass.class.getDeclaredMethod("testMethod2", Binding.class);
        ParameterDefinition[] parameters = ParameterDefinition.load(m);
        Assert.assertTrue(parameters[0].isBinding() && parameters[0].getBindingType().equals(Integer.class));
    }

    @Test
    public void testBindableParameter3() throws NoSuchMethodException {
        Method m = TestClass.class.getDeclaredMethod("testMethod3", String.class, Integer.class, Binding.class);
        ParameterDefinition[] parameters = ParameterDefinition.load(m);
        Assert.assertTrue(!parameters[0].isBinding() && parameters[0].getType().equals(String.class));
        Assert.assertTrue(!parameters[1].isBinding() && parameters[1].getType().equals(Integer.class));
        Assert.assertTrue(parameters[2].isBinding() && parameters[2].getBindingType().equals(
                new TypeReference<List<Integer>>() {}.getType()));
    }

    @Test
    public void testBindableParameter4() {
        TriCondition<Integer, Binding<List<String>>, Optional<Integer>> condition = ((a, b, x) -> a > 10);
        SerializedLambda lambda = LambdaUtils.getSerializedLambda(condition);
        Class c = LambdaUtils.getImplementationClass(lambda);
        Method m = LambdaUtils.getImplementationMethod(lambda, c);
        ParameterDefinition[] parameters = ParameterDefinition.load(m);
        Assert.assertTrue(!parameters[0].isBinding() && parameters[0].getType().equals(Integer.class));
        Assert.assertTrue(parameters[1].isBinding() && parameters[1].getBindingType().equals(Object.class));
    }

    @Test
    public void testBindableParameter5() {
        Condition condition = ConditionBuilder.with3Args((Integer a, Binding<List<String>> b, Integer x) -> a > 10)
                .parameterType(1, new TypeReference<Binding<List<Integer>>>() {}.getType())
                .build();
        ParameterDefinition[] parameters = condition.getConditionDefinition().getMethodDefinition().getParameterDefinitions();
        Assert.assertTrue(!parameters[0].isBinding() && parameters[0].getType().equals(Integer.class));
        Assert.assertTrue(parameters[1].isBinding() && parameters[1].getBindingType().equals(new TypeReference<List<Integer>>() {}.getType()));
    }

    private static class TestClass {

        public boolean testMethod0(String a, List<Integer> b, Binding<List<Integer>> c, Map<?, Long> d) {
            return true;
        }

        public void testMethod1(int a, Map<String, Integer> values) {}

        public boolean testMethod2(Binding<Integer> x) {
            return true;
        }

        public boolean testMethod3(String a, Integer b, Binding<List<Integer>> x) {
            return true;
        }

    }
}
