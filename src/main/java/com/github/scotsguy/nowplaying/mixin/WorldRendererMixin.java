package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlayingConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Redirect(method = "playSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;setRecordPlayingOverlay(Lnet/minecraft/text/Text;)V"))
    private void modifyRecordPlayingOverlay(InGameHud inGameHud, Text text) {
        NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
        if (config.jukeboxStyle == NowPlayingConfig.Style.Hotbar) {
            inGameHud.setRecordPlayingOverlay(text);
        }

    }
}
