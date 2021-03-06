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

package org.algorithmx.rulii.validation.beans;

import org.algorithmx.rulii.bind.Bindings;
import org.algorithmx.rulii.core.ruleset.RuleSet;
import org.algorithmx.rulii.validation.RuleViolations;

public interface BeanValidator {

    static BeanValidator create() {
        return new DefaultBeanValidator();
    }

    default RuleViolations validate(Object bean) {
        return validate(bean, null, true, null);
    }

    RuleViolations validate(Object bean, Bindings bindings, boolean includeAnnotatedRules, RuleSet ruleSet);
}
