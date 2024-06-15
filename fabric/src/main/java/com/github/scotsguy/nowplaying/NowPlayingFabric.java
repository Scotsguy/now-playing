package com.github.scotsguy.nowplaying;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class NowPlayingFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(NowPlaying.DISPLAY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(NowPlaying::onEndTick);

        NowPlaying.init();
    }
}
