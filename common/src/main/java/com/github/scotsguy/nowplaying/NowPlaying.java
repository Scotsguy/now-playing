/*
 * Copyright (c) 2022-2025 AppleTheGolden
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.scotsguy.nowplaying;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.gui.toast.NowPlayingToast;
import com.github.scotsguy.nowplaying.mixin.GuiAccessor;
import com.github.scotsguy.nowplaying.sound.Sound;
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
        display(name, Sound.getMusicDisc(name.getString()), Config.options().musicStyle);
    }

    public static void display(Component name, Item disc, Config.Options.Style style) {
        Component message = Component.translatable("record.nowPlaying", name);

        Minecraft mc = Minecraft.getInstance();
        Config.Options options = Config.options();

        switch(style) {
            case Toast -> {
                mc.getToastManager().addToast(new NowPlayingToast(name, new ItemStack(disc),
                        options.toastTime * 1000L, options.toastScale));
                if (options.narrate) mc.getNarrator().sayNow(message);
            }
            case Hotbar -> {
                if (isHotbarVisible(mc.screen)) {
                    mc.gui.setOverlayMessage(message, true);
                    ((GuiAccessor)mc.gui).setOverlayMessageTime(options.hotbarTime * 20);
                } else if (options.fallbackToast) {
                    mc.getToastManager().addToast(new NowPlayingToast(name, new ItemStack(disc),
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
