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
package org.algorithmx.rules.bind.impl;

import org.algorithmx.rules.bind.Binding;
import org.algorithmx.rules.bind.BindingMatchingStrategy;
import org.algorithmx.rules.bind.Bindings;
import org.algorithmx.rules.bind.TypeReference;
import org.algorithmx.rules.spring.util.Assert;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Strategy class that matches Bindings in a given Rule Context by the given Name & Type.
 *
 * @author Max Arulananthan
 * @Since 1.0
 */
public class MatchByNameAndTypeBindingMatchingStrategy implements BindingMatchingStrategy {

    public MatchByNameAndTypeBindingMatchingStrategy() {
        super();
    }

    /**
     * Returns a set of Bindings that match the given name.
     *
     * @param bindings bindings.
     * @param name desired name.
     * @param type desired type.
     * @return all the matches; Will be an empty Set if no matches are found.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Binding<T>[] match(Bindings bindings, String name, TypeReference<T> type) {
        Assert.notNull(bindings, "bindings cannot be bull");
        Assert.notNull(name, "name cannot be bull");
        Assert.notNull(type, "type cannot be bull");

        Set<Binding<T>> result = new HashSet<>();
        // Look for the Binding by name & type
        Binding<T> binding = bindings.getBinding(name, type);
        // Add the Binding (if we found one)
        if (binding != null) result.add(binding);

        return result.toArray(new Binding[result.size()]);
    }
}