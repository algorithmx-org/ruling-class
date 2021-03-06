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

package org.algorithmx.rulii.core;

import org.algorithmx.rulii.bind.BindingDeclaration;
import org.algorithmx.rulii.bind.Bindings;
import org.algorithmx.rulii.core.context.RuleContext;

public interface Runnable<T> {

    T run(RuleContext context) throws UnrulyException;

    /**
     * Derives all the arguments and executes this Rule.
     *
     * @param bindings Rule Bindings.
     * @return execution status of the rule.
     * @throws UnrulyException thrown if there are any runtime errors during the execution.
     */
    T run(Bindings bindings) throws UnrulyException;

    /**
     * Derives all the arguments and executes this Rule.
     *
     * @param params Rule Parameters.
     * @return execution status of the rule.
     * @throws UnrulyException thrown if there are any runtime errors during the execution.
     */
    T run(BindingDeclaration...params) throws UnrulyException;
}
