package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlayingConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

public class LevelRendererMixinImpl {
    public static void modifyRecordPlayingOverlay(Gui gui, Component text) {
        NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
        if (config.jukeboxStyle == NowPlayingConfig.Style.Hotbar) {
            gui.setNowPlaying(text);
        }
    }
}
