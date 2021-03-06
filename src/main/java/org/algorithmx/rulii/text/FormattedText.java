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

package org.algorithmx.rulii.text;

import org.algorithmx.rulii.lib.spring.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class FormattedText {

    private final String template;
    private final List<Placeholder> placeholders;

    public FormattedText(String template, List<Placeholder> placeholders) {
        super();
        Assert.notNull(template, "template cannot be null.");
        this.template = template;
        this.placeholders = Collections.unmodifiableList(placeholders);
        Collections.sort(placeholders);
    }

    public String getTemplate() {
        return template;
    }

    public Placeholder getFirstPlaceholder(String name) {
        Placeholder result = null;

       for (Placeholder placeholder : placeholders) {
           if (placeholder.getName().equals(name)) {
               result = placeholder;
               break;
           }
       }

        return result;
    }

    public List<Placeholder> getPlaceholder(String name) {
        List<Placeholder> result = new ArrayList<>();

        placeholders.stream().forEach(p -> {
            if (p.getName().equals(name)) result.add(p);
        });

        return result;
    }

    public int getPlaceholderSize() {
        return placeholders.size();
    }

    public boolean hasPlaceholders() {
        return placeholders.size() > 0;
    }

    public String replaceWithIndex(ParameterInfo...parameters) {

        if (placeholders == null || placeholders.size() == 0) return template;
        if (parameters == null || parameters.length == 0) return template;

        Map<String, ParameterInfo> matchMap = new HashMap<>();

        for (ParameterInfo parameter : parameters) {
            matchMap.put(parameter.getName(), parameter);
        }

        StringBuilder result = new StringBuilder();
        Queue<Placeholder> queue = new ArrayBlockingQueue<>(placeholders.size(), false, placeholders);

        for (int i = 0; i < template.length(); i++) {

            if (!queue.isEmpty() && queue.peek().getStartPosition() == i) {
                Placeholder match = queue.poll();
                ParameterInfo parameter = matchMap.get(match.getName());

                if (parameter != null) {
                    result.append(match.getMessageFormatText(parameter.getIndex()));
                } else {
                    result.append("[" + match.getName() + " not found]");
                }

                i = match.getEndPosition() - 1;
            } else {
                result.append(template.charAt(i));
            }
        }

        return result.toString();
    }
}
