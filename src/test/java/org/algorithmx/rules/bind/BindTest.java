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
package org.algorithmx.rules.bind;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Basic Binding tests.
 *
 * @author Max Arulananthan
 */
public class BindTest {

    public BindTest() {
        super();
    }

    @Test
    public void testBind1() {
        Bindings bindings = Bindings.create();
        Binding<String> var = bindings.bind("key", String.class, "value");
        Assert.assertEquals("key", var.getName());
        Assert.assertEquals(String.class, var.getType());
        Assert.assertEquals("value", var.getValue());
    }

    @Test
    public void testBind2() {
        Bindings bindings = Bindings.create();
        Binding<Double> var = bindings.bind("key", Double.class);
        var.setValue(33.33);
        double result = var.getValue();
        Assert.assertEquals(33.33, result, 0.00);
    }

    @Test
    public void testBind3() {
        Bindings bindings = Bindings.create();
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        Binding<List<Integer>> var = bindings.bind("key", new TypeReference<List<Integer>>(){});
        var.setValue(values);
        Assert.assertEquals(values, var.getValue());
    }

    @Test(expected = BindingAlreadyExistsException.class)
    public void testBind4() {
        Bindings bindings = Bindings.create();
        bindings.bind("key", String.class, "value");
        bindings.bind("key", new TypeReference<List<Integer>>(){});
    }

    @Test
    public void testBind5() {
        Bindings bindings = Bindings.create();
        bindings.bind("key1", String.class, "value");
        bindings.bind("key2", new TypeReference<List<Integer>>(){});
        bindings.bind("key3", TypeReference.with(BigDecimal.class));
        Assert.assertEquals(3, bindings.size());
        bindings.clear();
        Assert.assertEquals(0, bindings.size());
    }

    @Test
    public void testBind6() {
        Bindings bindings = Bindings.create();
        bindings.bind("key1", String.class, "value");
        bindings.bind("key2", new TypeReference<List<Integer>>(){});
        Assert.assertTrue(bindings.contains("key1"));
        Assert.assertTrue(bindings.contains("key2"));
    }

    @Test
    public void testBind7() {
        Bindings bindings = Bindings.create();
        bindings.bind("key1", String.class, "value");
        bindings.bind("key2", new TypeReference<List<?>>(){});
        bindings.bind("key3", BigDecimal.class);
        bindings.bind("key4", new TypeReference<Map<? extends List<?>, List<Integer>>>(){});

        Assert.assertTrue(bindings.contains("key1", String.class));
        Assert.assertTrue(bindings.contains("key2", new TypeReference<List<Integer>>(){}));
        Assert.assertTrue(bindings.contains("key3", BigDecimal.class));
        Assert.assertTrue(bindings.contains("key4", new TypeReference<Map<ArrayList<?>, List<Integer>>>(){}));
    }

    @Test
    public void testBind8() {
        Bindings bindings = Bindings.create();
        bindings.bind("key1", String.class, "value");
        bindings.bind("key2", new TypeReference<List<?>>(){});
        bindings.bind("key3", BigDecimal.class, new BigDecimal("10.00"));
        bindings.bind("key4", new TypeReference<Map<? extends List<?>, List<Integer>>>(){});

        Binding<BigDecimal> binding = bindings.getBinding("key3");

        Assert.assertNotNull(binding);
        Assert.assertEquals(binding.getValue(), new BigDecimal("10.00"));
    }

    @Test
    public void testBind9() {
        Bindings bindings = Bindings.create();
        bindings.bind("key1", String.class, "value");
        bindings.bind("key2", new TypeReference<List<?>>() {});
        bindings.bind("key3", TypeReference.with(BigDecimal.class), new BigDecimal("10.00"));
        bindings.bind("key4", new TypeReference<Map<? extends List<?>, List<Integer>>>() {});
        bindings.bind("key5", TypeReference.with(BigDecimal.class), new BigDecimal("20.00"));

        Set<Binding<BigDecimal>> bindings1 = bindings.getBindings(BigDecimal.class);
        Assert.assertTrue(bindings1.size() == 2);
        Set<Binding<Map<? extends List<?>, List<Integer>>>> bindings2 = bindings.getBindings(new TypeReference<Map<? extends List<?>, List<Integer>>>() {});
        Assert.assertTrue(bindings2.size() == 1);
    }

    @Test
    public void testBind10() {
        Bindings bindings1 = Bindings.create();
        Binding<String> var1 = bindings1.bind("key1", String.class, "value");
        Binding<String> var2 = bindings1.bind("key2", String.class, "value");
        Bindings bindings2 = Bindings.create();
        bindings2.bind(var1, var2);
        Assert.assertTrue(bindings2.contains("key1", String.class));
        Assert.assertTrue(bindings2.contains("key2", String.class));
    }

    @Test(expected = InvalidBindingException.class)
    public void testValidation() {
        Bindings bindings = Bindings.create();
        Binding<String> binding = bindings.bind("key",  String.class, "hello world!", (String s) -> !s.contains("hello"), true);
    }

    @Test
    public void testSupplier1() {
        Bindings bindings = Bindings.create();
        bindings.bind("key", () -> "Hello World!", TypeReference.with(String.class));
        Assert.assertTrue(bindings.get("key").equals("Hello World!"));
    }

    @Test(expected = InvalidBindingException.class)
    public void testSupplier2() {
        Bindings bindings = Bindings.create();
        bindings.bind("key", () -> "Hello World!", TypeReference.with(String.class));
        bindings.set("key", "new value");
    }
}

