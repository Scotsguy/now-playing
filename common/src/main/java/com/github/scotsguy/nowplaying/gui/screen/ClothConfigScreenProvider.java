package com.github.scotsguy.nowplaying.gui.screen;

import com.github.scotsguy.nowplaying.config.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class ClothConfigScreenProvider {
    /**
     * Builds and returns a Cloth Config options screen.
     * @param parent the current screen.
     * @return a new options {@link Screen}.
     * @throws NoClassDefFoundError if the Cloth Config API mod is not
     * available.
     */
    static Screen getConfigScreen(Screen parent) {
        Config.Options options = Config.get().options;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(localized("screen", "options"))
                .setSavingRunnable(Config::save);

        ConfigEntryBuilder eb = builder.entryBuilder();
        ConfigCategory modSettings = builder.getOrCreateCategory(localized("config", "options"));

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "music_only_keybind"), options.onlyKeybind)
                .setTooltip(localized("option", "music_only_keybind.tooltip"))
                .setDefaultValue(Config.Options.defaultOnlyKeybind)
                .setSaveConsumer(val -> options.onlyKeybind = val)
                .build());

        modSettings.addEntry(eb.startEnumSelector(
                localized("option", "music_style"),
                        Config.Options.Style.class, options.musicStyle)
                .setEnumNameProvider(Config.Options.Style::name)
                .setDefaultValue(Config.Options.defaultMusicStyle)
                .setSaveConsumer(val -> options.musicStyle = val)
                .build());

        modSettings.addEntry(eb.startEnumSelector(
                        localized("option", "jukebox_style"),
                        Config.Options.Style.class, options.jukeboxStyle)
                .setEnumNameProvider(Config.Options.Style::name)
                .setDefaultValue(Config.Options.defaultJukeboxStyle)
                .setSaveConsumer(val -> options.jukeboxStyle = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "fallback_toast"), options.fallbackToast)
                .setTooltip(localized("option", "fallback_toast.tooltip"))
                .setDefaultValue(Config.Options.defaultFallbackToast)
                .setSaveConsumer(val -> options.fallbackToast = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                localized("option", "silence_woosh"), options.silenceWoosh)
                .setTooltip(localized("option", "silence_woosh.tooltip"))
                .setDefaultValue(Config.Options.defaultSilenceWoosh)
                .setSaveConsumer(val -> options.silenceWoosh = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                        localized("option", "simple_toast"), options.simpleToast)
                .setTooltip(localized("option", "simple_toast.tooltip"))
                .setDefaultValue(Config.Options.defaultSimpleToast)
                .setSaveConsumer(val -> options.simpleToast = val)
                .build());

        modSettings.addEntry(eb.startIntField(
                localized("option", "toast_time"), options.toastTime)
                .setTooltip(localized("option", "toast_time.tooltip"))
                .setDefaultValue(Config.Options.defaultToastTime)
                .setMin(1)
                .setMax(60)
                .setSaveConsumer(val -> options.toastTime = val)
                .build());

        modSettings.addEntry(eb.startIntField(
                        localized("option", "hotbar_time"), options.hotbarTime)
                .setTooltip(localized("option", "hotbar_time.tooltip"))
                .setDefaultValue(Config.Options.defaultHotbarTime)
                .setMin(1)
                .setMax(60)
                .setSaveConsumer(val -> options.hotbarTime = val)
                .build());

        modSettings.addEntry(eb.startBooleanToggle(
                localized("option", "narrate"), options.narrate)
                .setTooltip(localized("option", "narrate.tooltip"))
                .setDefaultValue(Config.Options.defaultNarrate)
                .setSaveConsumer(val -> options.narrate = val)
                .build());

        return builder.build();
    }
}
