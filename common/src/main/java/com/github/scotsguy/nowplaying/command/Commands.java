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

package com.github.scotsguy.nowplaying.command;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;

import static net.minecraft.commands.Commands.literal;

@SuppressWarnings("unchecked")
public class Commands<S> extends CommandDispatcher<S> {
    public void register(Minecraft mc, CommandDispatcher<S> dispatcher, CommandBuildContext buildCtx) {
        dispatcher.register((LiteralArgumentBuilder<S>)literal("nowplaying")
                .executes(ctx -> {
                    NowPlaying.displayLastMusic();
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}
