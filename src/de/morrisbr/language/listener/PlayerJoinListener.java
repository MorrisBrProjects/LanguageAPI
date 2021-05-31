package de.morrisbr.language.listener;

import de.morrisbr.language.LanguagePlugin;
import de.morrisbr.language.user.User;
import de.morrisbr.language.user.propety.Language;
import de.morrisbr.language.user.propety.UserProfile;
import de.morrisbr.language.user.propety.registry.LanguageRegistry;
import de.morrisbr.language.user.propety.registry.UserRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final LanguagePlugin plugin;

    public PlayerJoinListener(LanguagePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        User user;
        UserProfile profile;
        Language language;

        UserRegistry userRegistry = plugin.getRegistry().getUserRegistry();
        LanguageRegistry languageRegistry = plugin.getRegistry().getLanguageRegistry();

        user = new User();
        user.setUuid(player.getUniqueId().toString());
        profile = new UserProfile(plugin, user);
        profile.setLanguage(languageRegistry.getBasicLanguage());
        user.setProfile(profile);

        userRegistry.addUser(user);


        if (userRegistry.isUserExistInMemory(player.getUniqueId().toString())) {
            user = userRegistry.getUser(player.getUniqueId().toString());
            language = languageRegistry.getLanguageFromDataBase(user.getUuid());

            user.getProfile().setLanguage(language);
        }


    }

}
