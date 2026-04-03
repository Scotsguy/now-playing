/*
 * Copyright (c) 2022-2026 AppleTheGolden
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

package com.github.scotsguy.nowplaying.gui.screen;

import com.github.scotsguy.nowplaying.config.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class ClothScreenProvider {
    /**
     * Builds and returns a Cloth Config options screen.
     * @param parent the current screen.
     * @return a new options {@link Screen}.
     * @throws NoClassDefFoundError if the Cloth Config API mod is not
     * available.
     */
    static Screen getConfigScreen(Screen parent) {
        Config.Options options = Config.options();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(localized("screen", "options"))
                .setSavingRunnable(Config::save);

        ConfigEntryBuilder eb = builder.entryBuilder();
        ConfigCategory modSettings = builder.getOrCreateCategory(localized("config", "options"));

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "onlyKeybind"), options.onlyKeybind)
                .setTooltip(localized("option", "onlyKeybind.tooltip"))
                .setDefaultValue(Config.Options.onlyKeybindDefault)
                .setSaveConsumer(val -> options.onlyKeybind = val)
                .build());

        modSettings.addEntry(eb.startEnumSelector(
                localized("option", "musicStyle"),
                        Config.Options.Style.class, options.musicStyle)
                .setEnumNameProvider(Config.Options.Style::name)
                .setDefaultValue(Config.Options.musicStyleDefault)
                .setSaveConsumer(val -> options.musicStyle = val)
                .build());

        modSettings.addEntry(eb.startEnumSelector(
                        localized("option", "jukeboxStyle"),
                        Config.Options.Style.class, options.jukeboxStyle)
                .setEnumNameProvider(Config.Options.Style::name)
                .setDefaultValue(Config.Options.jukeboxStyleDefault)
                .setSaveConsumer(val -> options.jukeboxStyle = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "fallbackToast"), options.fallbackToast)
                .setTooltip(localized("option", "fallbackToast.tooltip"))
                .setDefaultValue(Config.Options.fallbackToastDefault)
                .setSaveConsumer(val -> options.fallbackToast = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                localized("option", "silenceWoosh"), options.silenceWoosh)
                .setTooltip(localized("option", "silenceWoosh.tooltip"))
                .setDefaultValue(Config.Options.silenceWooshDefault)
                .setSaveConsumer(val -> options.silenceWoosh = val)
                .build());

        modSettings.addEntry(eb.startFloatField(
                        localized("option", "toastScale"), options.toastScale)
                .setTooltip(localized("option", "toastScale.tooltip"))
                .setDefaultValue(Config.Options.toastScaleDefault)
                .setMin(0.25F)
                .setMax(2F)
                .setSaveConsumer(val -> options.toastScale = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "simpleToast"), options.simpleToast)
                .setTooltip(localized("option", "simpleToast.tooltip"))
                .setDefaultValue(Config.Options.simpleToastDefault)
                .setSaveConsumer(val -> options.simpleToast = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "darkToast"), options.darkToast)
                .setTooltip(localized("option", "darkToast.tooltip"))
                .setDefaultValue(Config.Options.darkToastDefault)
                .setSaveConsumer(val -> options.darkToast = val)
                .build());

        modSettings.addEntry(eb.startIntField(
                localized("option", "toastTime"), options.toastTime)
                .setTooltip(localized("option", "toastTime.tooltip"))
                .setDefaultValue(Config.Options.toastTimeDefault)
                .setMin(1)
                .setMax(60)
                .setSaveConsumer(val -> options.toastTime = val)
                .build());

        modSettings.addEntry(eb.startIntField(
                        localized("option", "hotbarTime"), options.hotbarTime)
                .setTooltip(localized("option", "hotbarTime.tooltip"))
                .setDefaultValue(Config.Options.hotbarTimeDefault)
                .setMin(1)
                .setMax(60)
                .setSaveConsumer(val -> options.hotbarTime = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                localized("option", "narrate"), options.narrate)
                .setTooltip(localized("option", "narrate.tooltip"))
                .setDefaultValue(Config.Options.narrateDefault)
                .setSaveConsumer(val -> options.narrate = val)
                .build());

        return builder.build();
    }
}
