/*
 * Copyright (c) 2022-2025 AppleTheGolden
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

package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.command.Commands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.concurrent.CompletableFuture;

public class NowPlayingFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Keybindings
        KeyBindingHelper.registerKeyBinding(NowPlaying.DISPLAY_KEY);
        KeyBindingHelper.registerKeyBinding(NowPlaying.NEXT_KEY);

        // Commands
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, buildContext) ->
                new Commands<FabricClientCommandSource>().register(Minecraft.getInstance(), dispatcher, buildContext)));

        // Tick events
        ClientTickEvents.END_CLIENT_TICK.register(NowPlaying::onEndTick);

        // Resource reload event
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(
                ResourceLocation.fromNamespaceAndPath(
                        NowPlaying.MOD_ID,
                        "custom_resource_reloader"
                ),
                (sharedState, backgroundExecutor, preparationBarrier, gameExecutor) -> {
                    NowPlaying.onResourceReload();
                    return CompletableFuture.allOf(CompletableFuture.runAsync(() -> {}))
                            .thenCompose(preparationBarrier::wait)
                            .thenAcceptAsync((val) -> {}, gameExecutor);
                }
        );

        // Main initialization
        NowPlaying.init();
    }
}
