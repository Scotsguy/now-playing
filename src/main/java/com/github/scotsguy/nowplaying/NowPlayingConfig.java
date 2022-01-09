package com.github.scotsguy.nowplaying;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

@Config(name = "now-playing")
@SuppressWarnings("unused")
public class NowPlayingConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Style musicStyle = Style.Toast;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Style jukeboxStyle = Style.Hotbar;

    @ConfigEntry.Gui.Tooltip
    public boolean silenceWoosh = false;

    public enum Style implements SelectionListEntry.Translatable {
        Hotbar {
            @Override
            public @NotNull String getKey() {
                return "now_playing.config.style.hotbar";
            }
        },
        Toast {
            @Override
            public @NotNull String getKey() {
                return "now_playing.config.style.toast";
            }
        },
        Disabled {
            @Override
            public @NotNull String getKey() {
                return "now_playing.config.style.disabled";
            }
        }
    }
}
