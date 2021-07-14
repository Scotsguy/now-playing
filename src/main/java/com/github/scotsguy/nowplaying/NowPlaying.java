package com.github.scotsguy.nowplaying;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NowPlaying implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(NowPlayingConfig.class, JanksonConfigSerializer::new);
    }
}
