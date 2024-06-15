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
    public void register(Minecraft mc, CommandDispatcher<S> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register((LiteralArgumentBuilder<S>)literal("nowplaying")
                        .executes(ctx -> {
                            NowPlaying.display();
                            return Command.SINGLE_SUCCESS;
                        })
        );
    }
}
