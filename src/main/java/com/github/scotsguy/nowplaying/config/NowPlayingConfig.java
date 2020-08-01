package com.github.scotsguy.nowplaying.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class NowPlayingConfig{
    public static ForgeConfigSpec.EnumValue<Config.Style> musicStyle;
    public static ForgeConfigSpec.EnumValue<Config.Style> jukeboxStyle;

    public static void init(Builder builder) {
        builder.comment("Now playing config");
        builder.push("Notification Style");
        musicStyle = builder
                .comment("How the 'Now Playing' notification should be displayed when music plays normally.")
                .defineEnum("Music", Config.Style.Toast);

        jukeboxStyle = builder
                .comment("How the 'Now Playing' notification should be displayed when music plays from a jukebox.")
                .defineEnum("Jukebox", Config.Style.Hotbar);
    }
}
