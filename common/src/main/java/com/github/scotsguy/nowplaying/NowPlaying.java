package com.github.scotsguy.nowplaying;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class NowPlaying {
    public static void init() {
        AutoConfig.register(NowPlayingConfig.class, JanksonConfigSerializer::new);
    }
}
