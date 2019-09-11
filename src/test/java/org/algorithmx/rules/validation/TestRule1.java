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
package org.algorithmx.rules.validation;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;
import org.algorithmx.rules.model.ValidationErrorContainer;

@Rule(name = "TestRule1")
public class TestRule1 {

    public TestRule1() {
        super();
    }

    @Given
    public boolean when(Integer value) {
        return value != null && value < 10;
    }

    @Then
    public void then(Integer value, ValidationErrorContainer errors) {
        errors.add("TestRule1", "Test.Error.100").param("value", value);
    }
}
