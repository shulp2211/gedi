/**
 * 
 *    Copyright 2017 Florian Erhard
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */
/*
 * Copyright (c) 2002-2015, the original author or authors.
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package jline.console.completer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import jline.internal.Ansi;

import static jline.internal.Preconditions.checkNotNull;

/**
 * Completer for a set of strings.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 2.3
 */
public class AnsiStringsCompleter
    implements Completer
{
    private final SortedMap<String, String> strings = new TreeMap<String, String>();

    public AnsiStringsCompleter() {
        // empty
    }

    public AnsiStringsCompleter(final Collection<String> strings) {
        checkNotNull(strings);
        for (String str : strings) {
            this.strings.put(Ansi.stripAnsi(str), str);
        }
    }

    public AnsiStringsCompleter(final String... strings) {
        this(Arrays.asList(strings));
    }

    public Collection<String> getStrings() {
        return strings.values();
    }

    public int complete(String buffer, final int cursor, final List<CharSequence> candidates) {
        // buffer could be null
        checkNotNull(candidates);

        if (buffer == null) {
            candidates.addAll(strings.values());
        }
        else {
            buffer = Ansi.stripAnsi(buffer);
            for (Map.Entry<String, String> match : strings.tailMap(buffer).entrySet()) {
                if (!match.getKey().startsWith(buffer)) {
                    break;
                }

                candidates.add(match.getValue());
            }
        }

        return candidates.isEmpty() ? -1 : 0;
    }
}