package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.github.scotsguy.nowplaying.mixin.GuiAccessor;
import com.github.scotsguy.nowplaying.util.ModLogger;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.github.scotsguy.nowplaying.util.Localization.localized;
import static com.github.scotsguy.nowplaying.util.Localization.translationKey;

public class NowPlaying {
    public static final String MOD_ID = "now-playing";
    public static final String MOD_ID_NEOFORGE = "now_playing";
    public static final String MOD_NAME = "Now Playing";
    public static final ModLogger LOG = new ModLogger(MOD_NAME);
    public static final KeyMapping DISPLAY_KEY = new KeyMapping(
            translationKey("key", "display"), InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(), translationKey("key_group"));

    public static Component lastMusic;

    public static void init() {
        Config.getAndSave();
    }

    public static void onEndTick(Minecraft mc) {
        while (DISPLAY_KEY.consumeClick()) {
            displayLastMusic();
        }
    }

    public static void displayLastMusic() {
        if (lastMusic != null) {
            displayMusic(lastMusic);
        } else {
            Minecraft.getInstance().gui.setOverlayMessage(
                    localized("message", "not_found").withStyle(ChatFormatting.RED), true);
        }
    }

    public static void displayMusic(Component name) {
        display(name, Items.MUSIC_DISC_CAT, Config.get().options.musicStyle);
    }

    public static void display(Component name, Item disc, Config.Options.Style style) {
        Component message = Component.translatable("record.nowPlaying", name);

        Minecraft mc = Minecraft.getInstance();
        Config.Options options = Config.get().options;

        switch(style) {
            case Toast -> {
                mc.getToasts().addToast(new NowPlayingToast(name, new ItemStack(disc),
                        options.toastTime * 1000L, options.toastScale));
                if (options.narrate) mc.getNarrator().sayNow(message);
            }
            case Hotbar -> {
                if (isHotbarVisible(mc.screen)) {
                    mc.gui.setOverlayMessage(message, true);
                    ((GuiAccessor)mc.gui).setOverlayMessageTime(options.hotbarTime * 20);
                } else if (options.fallbackToast) {
                    mc.getToasts().addToast(new NowPlayingToast(name, new ItemStack(disc),
                            options.toastTime * 1000L, options.toastScale));
                }
                if (options.narrate) mc.getNarrator().sayNow(message);
            }
        }
    }

    public static boolean isHotbarVisible(Screen screen) {
        return (screen == null || screen instanceof ChatScreen);
    }
}
