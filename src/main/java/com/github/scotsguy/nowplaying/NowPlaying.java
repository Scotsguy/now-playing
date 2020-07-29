package com.github.scotsguy.nowplaying;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
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
