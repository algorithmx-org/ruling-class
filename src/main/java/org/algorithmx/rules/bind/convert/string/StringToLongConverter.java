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

/**
 * Converts a String value to a Long.
 *
 * @author Max Arulananthan.
 * @since 1.0
 */
public class StringToLongConverter extends ConverterTemplate<String, Long> {

    public StringToLongConverter() {
        super();
    }

    @Override
    public Long convert(String value) {
        if (value == null) return null;

        try {
            return new Long(value);
        } catch (NumberFormatException e) {
            throw new ConversionException(e, value, getSourceType(), getTargetType());
        }
    }
}
