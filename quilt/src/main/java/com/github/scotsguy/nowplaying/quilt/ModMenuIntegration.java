package com.github.scotsguy.nowplaying.quilt;

import com.github.scotsguy.nowplaying.NowPlayingConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> AutoConfig.getConfigScreen(NowPlayingConfig.class, screen).get();
    }
}
