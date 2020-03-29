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
package org.algorithmx.rules.core.rule;

import org.algorithmx.rules.model.RuleDefinition;

/**
 * Interface to be implemented by any Rule that wishes to be aware of it's RuleDefinition.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public interface RuleDefinitionAware {

    /**
     * Set the RuleDefinition on the applicable Rule.
     *
     * @param ruleDefinition meta information about the Rule.
     */
    void setRuleDefinition(RuleDefinition ruleDefinition);
}
