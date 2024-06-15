package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Nullable
    private ClientLevel level;

    @WrapOperation(
            method = "playJukeboxSong",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"
            )
    )
    private void setTimeOrShowToast(Gui instance, Component text, Operation<Void> original,
                                    @Local JukeboxSong song, @Local SoundEvent sound) {
        Config config = Config.get();
        switch(config.options.jukeboxStyle) {
            case Config.Options.Style.Hotbar -> {
                original.call(instance, text);
                ((GuiAccessor)instance).setOverlayMessageTime(config.options.hotbarTime * 20);
            }
            case Config.Options.Style.Toast -> {
                // Attempt to identify the disc item, default to 'Cat' if lookup fails
                Item disc = null;
                Optional<Registry<Item>> itemRegistry = level.registryAccess().registry(Registries.ITEM);
                if (itemRegistry.isPresent()) disc = itemRegistry.get().get(ResourceLocation.parse(
                        sound.getLocation().toString().replaceAll("\\.", "_")));
                if (disc == null || disc.equals(Items.AIR)) disc = Items.MUSIC_DISC_CAT;

                minecraft.getToasts().addToast(new NowPlayingToast(song.description(), new ItemStack(disc)));

                if (config.options.narrate) minecraft.getNarrator().sayNow(
                        Component.translatable("record.nowPlaying", song.description()));
            }
        }
    }
}
