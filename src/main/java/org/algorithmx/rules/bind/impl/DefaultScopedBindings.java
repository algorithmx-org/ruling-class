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
package org.algorithmx.rules.bind.impl;

import org.algorithmx.rules.bind.Bindings;
import org.algorithmx.rules.bind.ScopedBindings;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Default implementation of the ScopedBindings.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class DefaultScopedBindings implements ScopedBindings {

    private final Stack<Bindings> scopes = new Stack<>();

    public DefaultScopedBindings() {
        super();
        init();
    }

    @Override
    public Bindings getCurrentScope() {
        return scopes.peek();
    }

    @Override
    public Iterable<Bindings> getScopes() {
        return scopes;
    }

    @Override
    public Iterable<Bindings> getScopesInReverseOrder() {
        List<Bindings> result = scopes.subList(0, scopes.size());
        Collections.reverse(result);
        return result;
    }

    @Override
    public Bindings newScope() {
        Bindings result = createScope();
        scopes.push(result);
        return result;
    }

    @Override
    public Bindings endScope() {
        return scopes.pop();
    }

    @Override
    public void clear() {
        scopes.clear();
        init();
    }

    /**
     * Creates a new scope and pushes it into the Stack.
     */
    protected void init() {
        scopes.push(createScope());
    }

    /**
     * Creates a new scope.
     *
     * @return newly created Bindings.
     */
    protected Bindings createScope() {
        return new SimpleBindings();
    }
}