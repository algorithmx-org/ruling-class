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
import java.util.Map;
import java.util.TreeMap;

public final class ScriptLanguageManager {

    public static final String JAVASCRIPT = "ECMAScript";

    private static final Map<String, ScriptProcessor> registeredScriptProcessors = new TreeMap<>();

    static {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

        if (scriptEngineManager.getEngineFactories() != null) {
            for (ScriptEngineFactory factory : scriptEngineManager.getEngineFactories()) {
                register(factory.getScriptEngine(), factory.getLanguageName());
            }
        }
    }

    private ScriptLanguageManager() {
        super();
    }

    public static void register(ScriptEngine engine, String languageName) {
        register(new DefaultScriptProcessor(engine), languageName);
    }

    public static void register(ScriptProcessor processor, String languageName) {
        Assert.notNull(languageName, "languageName cannot be null.");
        Assert.notNull(processor, "processor cannot be null.");
        registeredScriptProcessors.put(languageName, processor);
    }

    public static ScriptProcessor getScriptProcessor(String language) throws UnrulyException {
        return registeredScriptProcessors.get(language);
    }

    public static ScriptProcessor[] getAvailableScriptProcessors() {
        return registeredScriptProcessors.values().stream().toArray(size -> new ScriptProcessor[size]);
    }

    public static String[] getAvailableScriptingLanguages() {
        return registeredScriptProcessors.keySet().stream().toArray(size -> new String[size]);
    }
}

