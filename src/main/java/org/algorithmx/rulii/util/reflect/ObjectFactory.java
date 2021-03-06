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

package org.algorithmx.rulii.util.reflect;

import org.algorithmx.rulii.bind.match.BindingMatchingStrategy;
import org.algorithmx.rulii.core.UnrulyException;

/**
 * Factory use to defaultObjectFactory Objects. Framework requires Object instances to be created (such as Rules), the ObjectFactory is
 * used to defaultObjectFactory those Objects.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public interface ObjectFactory {

    /**
     * Returns the default implementation of the ObjectFactory.
     *
     * @return instance of the ObjectFactory.
     */
    static ObjectFactory create() {
        return new DefaultObjectFactory();
    }

    <T extends BindingMatchingStrategy> T createBindingMatchingStrategy(Class<T> type) throws UnrulyException;

    <T> T createAction(Class<T> type) throws UnrulyException;

    <T> T createCondition(Class<T> type) throws UnrulyException;

    <T> T createFunction(Class<T> type) throws UnrulyException;

    <T> T createRule(Class<T> type) throws UnrulyException;

    <T> T createRuleSet(Class<T> type) throws UnrulyException;

    /**
     * Creates a new instance of the desired Type.
     *
     * @param type desired type.
     * @param <T> generic Type.
     * @param isUseCache use object cache.
     * @return new instance of Type.
     * @throws UnrulyException thrown in case we are unable to defaultObjectFactory the type at runtime.
     */
    <T> T create(Class<T> type, boolean isUseCache) throws UnrulyException;
}
