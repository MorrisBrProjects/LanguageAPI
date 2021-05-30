package de.morrisbr.language.user.propety.registry;

import de.morrisbr.language.LanguagePlugin;

public class Registry {

    private final LanguageRegistry languageRegistry;
    private final UserRegistry userRegistry;
    private final LanguagePlugin plugin;

    public Registry(LanguagePlugin plugin) {
        this.plugin = plugin;

        this.languageRegistry = new LanguageRegistry(this);
        this.userRegistry = new UserRegistry(this);
    }

    public LanguageRegistry getLanguageRegistry() {
        return languageRegistry;
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public LanguagePlugin getPlugin() {
        return plugin;
    }

}
