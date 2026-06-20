/*
 * Copyright (c) 2022-2026 AppleTheGolden
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.scotsguy.nowplaying.util;

import com.github.scotsguy.nowplaying.platform.services.PlatformServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;

@SuppressWarnings("unused")
public class Logging {

    private Logging() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    public static Logger getLogger(String name) {
        if (PlatformServices.getInstance().isDevEnv()
                || PlatformServices.getInstance().hasNamedLogger()) {
            return LogManager.getLogger(name);
        } else {
            return LogManager.getLogger(name, new PrefixingMessageFactory("[" + name + "/]: "));
        }
    }

    private static final class PrefixingMessageFactory extends AbstractMessageFactory {

        private final String prefix;

        public PrefixingMessageFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Message newMessage(String message) {
            return new SimpleMessage(prefix + message);
        }

        @Override
        public Message newMessage(String message, Object... params) {
            return new FormattedMessage(prefix + message, params);
        }
    }
}
