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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.StackLocatorUtil;

public class ModLogger {
    private final Logger logger;

    public ModLogger(Logger logger) {
        this.logger = logger;
    }

    public ModLogger(String name) {
        this(LogManager.getLogger(name));
    }

    private String edit(Level level, String message) {
        if (level == Level.DEBUG) return String.format("[%s/%s]: %s", logger.getName(),
                StackLocatorUtil.getCallerClass(4).getSimpleName(), message);
        return String.format("[%s]: %s", logger.getName(), message);
    }

    private void log(Level level, String message, Object... args) {
        if (!logger.isEnabled(level)) return;
        logger.log(level, edit(level, message), args);
    }

    public void trace(String message, Object... args){
        log(Level.TRACE, message, args);
    }

    public void debug(String message, Object... args){
        log(Level.DEBUG, message, args);
    }

    public void info(String message, Object... args){
        log(Level.INFO, message, args);
    }

    public void warn(String message, Object... args){
        log(Level.WARN, message, args);
    }

    public void error(String message, Object... args){
        log(Level.ERROR, message, args);
    }

    public void fatal(String message, Object... args){
        log(Level.FATAL, message, args);
    }
}
