package com.github.scotsguy.nowplaying.quilt;

import com.github.scotsguy.nowplaying.NowPlaying;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class NowPlayingQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        NowPlaying.init();
    }
}
