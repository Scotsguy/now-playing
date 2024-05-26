package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.gui.screen.ConfigScreenProvider;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(NowPlaying.MOD_ID_NEOFORGE)
public class NowPlayingNeoForge {
    public NowPlayingNeoForge() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (mc, parent) -> ConfigScreenProvider.getConfigScreen(parent));

        NowPlaying.init();
    }
}
