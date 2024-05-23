package com.github.scotsguy.nowplaying;

import net.fabricmc.api.ClientModInitializer;

public class NowPlayingFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NowPlaying.init();
    }
}
