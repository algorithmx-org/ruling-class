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
package org.algorithmx.rules.bind.convert.string;

import org.algorithmx.rules.bind.convert.ConversionException;
import org.algorithmx.rules.bind.convert.ConverterTemplate;

import java.lang.reflect.Type;

/**
 * Converts a String value to a Enum.
 *
 * @author Max Arulananthan.
 * @since 1.0
 */
public class StringToEnumConverter extends ConverterTemplate<String, Enum> {

    public StringToEnumConverter() {
        super();
    }

    @Override
    public Enum convert(String value, Type toType) throws ConversionException {
        if (value == null) return null;

        try {
            return Enum.valueOf((Class) toType, value);
        } catch (IllegalArgumentException e) {
            throw new ConversionException(e, value, getSourceType(), getTargetType());
        }
    }
}