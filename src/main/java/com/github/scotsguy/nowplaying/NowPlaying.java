package com.github.scotsguy.nowplaying;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NowPlaying implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /*
        MinecraftClient client = MinecraftClient.getInstance();
        SoundManager manager = client.getSoundManager();
        SoundSystem soundSystem = ((SoundManagerAccessor) manager).getSoundSystem();
        SoundInstanceListener listener = new NowPlayingListener();
        soundSystem.registerListener(new NowPlayingListener());

         */
    }
}
