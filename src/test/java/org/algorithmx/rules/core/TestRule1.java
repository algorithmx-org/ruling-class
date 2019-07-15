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
package org.algorithmx.rules.core;

import org.algorithmx.rules.annotation.Action;
import org.algorithmx.rules.annotation.Nullable;
import org.algorithmx.rules.annotation.Rule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Rule(name = "Test Rule", description = "Test Description 1")
public class TestRule1 {

    public TestRule1() {
        super();
    }

    public boolean when(int id, @Nullable Date closingDate, List<String> values) {
        return true;
    }

    @Action
    public void calculateId(int id) {
        // do something
    }

    @Action
    public void makePayment(Date closingDate) {
        // do something
    }
}