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
package org.algorithmx.rules.bind;

import org.algorithmx.rules.core.Identifiable;

import java.lang.reflect.Type;

/**
 * A binding between a name and a value of a type.
 * A Binding has a name, type and a value. The name and type cannot be changed once the Binding is created.
 *
 * @param <T> generic type of the Binding.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public interface Binding<T> extends Identifiable {

	String NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9]*?$";

	/**
	 * Name of the Binding.
     *
	 * @return name.
	 */
	String getName();

	/**
	 * Type of the Binding.
     *
	 * @return type.
	 */
	Type getType();

    /**
     * Determines whether this Binding is modifiable.
     *
     * @return true if modifiable; false otherwise.
     */
    default boolean isMutable() {
    	return true;
	}

	default T get() {
		return getValue();
	}

	/**
	 * Value of the Binding.
     *
	 * @return value.
	 */
	T getValue();

	/**
	 * Textual Value of the Binding.
	 *
	 * @return string value of the Binding. The default implementation will call toString() on the value.
	 */
	default String getTextValue() {
		Object result = getValue();
		return result != null ? result.toString() : null;
	}

	/**
	 * Sets the value of the Binding.
	 *
	 * @param value new value.
	 *
	 * @throws InvalidBindingException thrown if the value doesn't pass the validation check
	 * or if there is type mismatch. The type checking simply makes sure that the value passed matches the declared type
	 * of the Binding. It will not check any generics similar to Java. If the Binding is declared as List of Integers
	 * and the value that is passed is a List of Strings. InvalidBindingException will NOT be thrown in this case.
	 */
	void setValue(T value) throws InvalidBindingException;

	/**
	 * Determines whether the given type is acceptable.
	 * 
	 * @param type input type.
	 * @return true if the given type matches the SimpleBinding type.
	 */
	boolean isTypeAcceptable(Type type);

	/**
	 * Determines whether the binding can be assigned to the given Type.
	 *
	 * @param type desired type.
	 * @return true if this Binding can be assigned to the desired type.
	 */
	boolean isAssignable(Type type);
}
