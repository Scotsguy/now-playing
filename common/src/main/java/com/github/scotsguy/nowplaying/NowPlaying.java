package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.util.ModLogger;

public class NowPlaying {
    public static final String MOD_ID = "nowplaying";
    public static final String MOD_NAME = "NowPlaying";
    public static final ModLogger LOG = new ModLogger(MOD_NAME);

    public static void init() {
        Config.getAndSave();
    }
}
