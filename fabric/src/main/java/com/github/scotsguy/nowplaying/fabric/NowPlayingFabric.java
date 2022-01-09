package com.github.scotsguy.nowplaying.fabric;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.github.scotsguy.nowplaying.NowPlayingConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NowPlayingFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NowPlaying.init();
    }
}
