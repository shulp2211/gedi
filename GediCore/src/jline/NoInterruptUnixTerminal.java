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
package jline;

// Based on Apache Karaf impl

/**
 * Non-interruptible (via CTRL-C) {@link UnixTerminal}.
 *
 * @since 2.0
 */
public class NoInterruptUnixTerminal
    extends UnixTerminal
{
    private String intr;

    public NoInterruptUnixTerminal() throws Exception {
        super();
    }

    @Override
    public void init() throws Exception {
        super.init();
        intr = getSettings().getPropertyAsString("intr");
        if ("<undef>".equals(intr)) {
            intr = null;
        }
        if (intr != null) {
            getSettings().undef("intr");
        }
    }

    @Override
    public void restore() throws Exception {
        if (intr != null) {
            getSettings().set("intr", intr);
        }
        super.restore();
    }
}
