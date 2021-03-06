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

package org.algorithmx.rulii.config;

import org.algorithmx.rulii.convert.ConverterRegistry;
import org.algorithmx.rulii.bind.match.BindingMatchingStrategy;
import org.algorithmx.rulii.bind.match.ParameterResolver;
import org.algorithmx.rulii.lib.spring.util.Assert;
import org.algorithmx.rulii.script.NoOpScriptProcessor;
import org.algorithmx.rulii.script.ScriptLanguageManager;
import org.algorithmx.rulii.script.ScriptProcessor;
import org.algorithmx.rulii.text.MessageFormatter;
import org.algorithmx.rulii.text.MessageResolver;
import org.algorithmx.rulii.util.reflect.MethodResolver;
import org.algorithmx.rulii.util.reflect.ObjectFactory;

import java.time.Clock;
import java.util.Locale;

public class RuliiConfigurationBuilder {

    private BindingMatchingStrategy matchingStrategy = BindingMatchingStrategy.create();
    private ParameterResolver parameterResolver = ParameterResolver.create();
    private MethodResolver methodResolver = MethodResolver.create();
    private MessageFormatter messageFormatter = MessageFormatter.create();
    private ConverterRegistry converterRegistry = ConverterRegistry.create();
    private ObjectFactory objectFactory = ObjectFactory.create();
    private Clock clock = Clock.systemDefaultZone();
    private Locale locale = Locale.getDefault();
    private MessageResolver messageResolver = MessageResolver.create();
    private ScriptProcessor scriptProcessor = new NoOpScriptProcessor();

    private RuliiConfigurationBuilder() {
        super();
    }

    public static RuliiConfigurationBuilder create() {
        RuliiConfigurationBuilder result = new RuliiConfigurationBuilder();
        ScriptProcessor jsScriptProcessor = ScriptLanguageManager.getScriptProcessor(ScriptLanguageManager.JAVASCRIPT);

        // Found js language processor
        if (jsScriptProcessor != null) {
            result.scriptProcessor(jsScriptProcessor);
        }

        return result;
    }

    public RuliiConfigurationBuilder matchingStrategy(BindingMatchingStrategy matchingStrategy) {
        Assert.notNull(matchingStrategy, "matchingStrategy cannot be null.");
        this.matchingStrategy = matchingStrategy;
        return this;
    }

    public RuliiConfigurationBuilder parameterResolver(ParameterResolver parameterResolver) {
        Assert.notNull(parameterResolver, "parameterResolver cannot be null.");
        this.parameterResolver = parameterResolver;
        return this;
    }

    public RuliiConfigurationBuilder methodResolver(MethodResolver methodResolver) {
        Assert.notNull(methodResolver, "methodResolver cannot be null.");
        this.methodResolver = methodResolver;
        return this;
    }

    public RuliiConfigurationBuilder messageFormatter(MessageFormatter messageFormatter) {
        Assert.notNull(messageFormatter, "messageFormatter cannot be null.");
        this.messageFormatter = messageFormatter;
        return this;
    }

    public RuliiConfigurationBuilder converterRegistry(ConverterRegistry converterRegistry) {
        Assert.notNull(converterRegistry, "converterRegistry cannot be null.");
        this.converterRegistry = converterRegistry;
        return this;
    }

    public RuliiConfigurationBuilder objectFactory(ObjectFactory objectFactory) {
        Assert.notNull(objectFactory, "objectFactory cannot be null.");
        this.objectFactory = objectFactory;
        return this;
    }

    public RuliiConfigurationBuilder clock(Clock clock) {
        Assert.notNull(clock, "clock cannot be null.");
        this.clock = clock;
        return this;
    }

    public RuliiConfigurationBuilder locale(Locale locale) {
        Assert.notNull(locale, "locale cannot be null.");
        this.locale = locale;
        return this;
    }

    public RuliiConfigurationBuilder messageResolver(MessageResolver messageResolver) {
        Assert.notNull(messageResolver, "messageResolver cannot be null.");
        this.messageResolver = messageResolver;
        return this;
    }

    public RuliiConfigurationBuilder messageResolver(String...baseNames) {
        this.messageResolver = MessageResolver.create(baseNames);
        return this;
    }

    public RuliiConfigurationBuilder scriptProcessor(ScriptProcessor scriptProcessor) {
        Assert.notNull(scriptProcessor, "scriptProcessor cannot be null.");
        this.scriptProcessor = scriptProcessor;
        return this;
    }

    public RuliiConfiguration build() {
        return new RuliiConfiguration(matchingStrategy, parameterResolver, methodResolver, messageResolver,
                messageFormatter, converterRegistry, objectFactory, scriptProcessor, clock, locale);
    }
}
