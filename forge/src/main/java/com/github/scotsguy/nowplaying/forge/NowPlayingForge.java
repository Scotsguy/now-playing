package com.github.scotsguy.nowplaying.forge;

import com.github.scotsguy.nowplaying.NowPlaying;
import net.minecraftforge.fml.common.Mod;

@Mod("nowplaying")
public class NowPlayingForge {
    public NowPlayingForge() {
        NowPlaying.init();
    }
}
