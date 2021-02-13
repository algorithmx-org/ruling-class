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
package org.algorithmx.rulii.test.validation;

import org.algorithmx.rulii.annotation.Given;
import org.algorithmx.rulii.annotation.Otherwise;
import org.algorithmx.rulii.annotation.Rule;
import org.algorithmx.rulii.annotation.Then;
import org.algorithmx.rulii.validation.RuleViolations;

@Rule(name = "TestRule5")
public class TestRule5 {

    public TestRule5() {
        super();
    }

    @Given
    public boolean when(Integer value) {
        return value != null && value < 50;
    }

    @Then
    public void then(Integer value, RuleViolations errors) {
        errors.add("TestRule5", "Test.Error.500").param("value", value);
    }

    @Otherwise
    public void otherwise(Integer value, RuleViolations errors) {
        errors.add("TestRule5", "Test.Error.6000").param("value", value);
    }
}
