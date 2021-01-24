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
package org.algorithmx.rules.bind.match;

import org.algorithmx.rules.bind.Binding;
import org.algorithmx.rules.bind.Bindings;
import org.algorithmx.rules.util.TypeReference;
import org.algorithmx.rules.lib.spring.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * BindingMatchingStrategy that matches Bindings by the given Name.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class MatchByNameMatchingStrategy implements BindingMatchingStrategy {

    public MatchByNameMatchingStrategy() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, Binding<T>> match(Bindings bindings, String name, TypeReference<T> type) {
        Assert.notNull(bindings, "bindings cannot be bull");
        Assert.notNull(name, "name cannot be bull");

        Map<String, Binding<T>> result = new HashMap<>();
        // Look for the Binding by name
        Binding<T> binding = bindings.getBinding(name);
        // Add the Binding (if we found one)
        if (binding != null) result.put(binding.getName(), binding);

        return Collections.unmodifiableMap(result);
    }
}
