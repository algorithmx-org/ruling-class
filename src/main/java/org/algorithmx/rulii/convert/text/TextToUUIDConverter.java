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

package org.algorithmx.rulii.convert.text;

import org.algorithmx.rulii.convert.ConversionException;
import org.algorithmx.rulii.convert.ConverterTemplate;
import org.algorithmx.rulii.lib.apache.StringUtils;

import java.lang.reflect.Type;
import java.util.UUID;

public class TextToUUIDConverter extends ConverterTemplate<CharSequence, UUID> {

    public TextToUUIDConverter() {
        super();
    }

    @Override
    public UUID convert(CharSequence value, Type toType) throws ConversionException {
        if (value == null) return null;
        return StringUtils.isEmpty(value) ? null : UUID.fromString(value.toString().trim());
    }
}
