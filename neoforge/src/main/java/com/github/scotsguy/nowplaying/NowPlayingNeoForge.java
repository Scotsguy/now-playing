package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.gui.screen.ConfigScreenProvider;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@Mod(NowPlaying.MOD_ID)
public class NowPlayingNeoForge {
    public NowPlayingNeoForge() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, parent) -> ConfigScreenProvider.getConfigScreen(parent))
                );

        NowPlaying.init();
    }
}
