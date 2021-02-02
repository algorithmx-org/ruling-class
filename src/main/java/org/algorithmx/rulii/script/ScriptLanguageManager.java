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

package org.algorithmx.rulii.script;

import org.algorithmx.rulii.core.UnrulyException;
import org.algorithmx.rulii.lib.spring.util.Assert;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

public final class ScriptLanguageManager {

    private static final Map<String, ScriptProcessor> registeredScriptProcessors = new HashMap<>();

    static {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

        if (scriptEngineManager.getEngineFactories() != null) {
            for (ScriptEngineFactory factory : scriptEngineManager.getEngineFactories()) {
                register(factory.getScriptEngine());
            }
        }
    }

    private ScriptLanguageManager() {
        super();
    }

    public static void register(ScriptEngine engine) {
        register(new DefaultScriptProcessor(engine));
    }

    public static void register(ScriptProcessor processor) {
        Assert.notNull(processor, "processor cannot be null.");
        registeredScriptProcessors.put(processor.getEngine().getFactory().getLanguageName(), processor);
    }

    public static ScriptProcessor getScriptProcessor(String language) throws UnrulyException {
        return registeredScriptProcessors.get(language);
    }
}
