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

package org.algorithmx.rulii.extract.standard;

import org.algorithmx.rulii.extract.TypedValueExtractorTemplate;
import org.algorithmx.rulii.extract.TypedValueProcessor;
import org.algorithmx.rulii.lib.spring.util.Assert;

public class ThreadLocalValueExtractor extends TypedValueExtractorTemplate<ThreadLocal<?>> {

    public ThreadLocalValueExtractor() {
        super(ThreadLocal.class, 0);
    }

    @Override
    public void extract(ThreadLocal<?> container, TypedValueProcessor processor) {
        Assert.notNull(processor, "processor cannot be null.");
        processor.value("", container.get());
    }
}
