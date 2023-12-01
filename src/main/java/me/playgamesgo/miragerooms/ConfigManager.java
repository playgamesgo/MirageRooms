package me.playgamesgo.miragerooms;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;

public class ConfigManager {

    public static void init(Config configFile) {
        configFile.setReloadSettings(ReloadSettings.INTELLIGENT);

        configFile.write();
    }
}