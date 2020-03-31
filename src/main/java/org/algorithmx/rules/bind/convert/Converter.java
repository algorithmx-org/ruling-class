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
package org.algorithmx.rules.bind.convert;

import org.algorithmx.rules.bind.TypeReference;

import java.lang.reflect.Type;

/**
 * A converter converts a source object of type T to a target of type S.
 *
 * @param <T> the source type
 * @param <R> the target type
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public interface Converter<T, R> {

    /**
     * Returns the source type.
     *
     * @return source type.
     */
    Type getSourceType();

    /**
     * Returns the target type.
     *
     * @return target type.
     */
    Type getTargetType();

    /**
     * Determines whether this converter can be used to convert the desire source and target types.
     *
     * @param fromType source type.
     * @param toType target type.
     * @return true if this converter can convert the desired types; false otherwise.
     */
    boolean canConvert(Type fromType, Type toType);

    /**
     * Determines whether this converter can be used to convert the desire source and target types.
     *
     * @param fromType source type.
     * @param toType target type.
     * @return true if this converter can convert the desired types; false otherwise.
     */
    default boolean canConvert(Class<?> fromType, Class<?> toType) {
        return canConvert(TypeReference.with(fromType).getType(), TypeReference.with(fromType).getType());
    }

    /**
     * Convert the source object of type T to target type R.
     *
     * @param value source value.
     * @return converted value.
     * @throws ConversionException thrown in case of an error.
     */
    R convert(T value) throws ConversionException;
}
