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

        return builder.build();
    }
}
