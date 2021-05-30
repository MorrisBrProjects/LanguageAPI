package de.morrisbr.language.listener;

import de.morrisbr.language.LanguagePlugin;
import de.morrisbr.language.database.LiteSQL;
import de.morrisbr.language.user.User;
import de.morrisbr.language.user.propety.Language;
import de.morrisbr.language.user.propety.UserProfile;
import de.morrisbr.language.user.propety.registry.LanguageRegistry;
import de.morrisbr.language.user.propety.registry.UserRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final LanguagePlugin plugin;

    public PlayerJoinListener(LanguagePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        LiteSQL database = plugin.getDataBase();
        User user;

        UserRegistry userRegistry = plugin.getRegistry().getUserRegistry();
        LanguageRegistry languageRegistry = plugin.getRegistry().getLanguageRegistry();

        //if(!userRegistry.isUserExistInDataBase(player.getUniqueId().toString())) {
        user = new User();
        user.setUuid(player.getUniqueId().toString());

        UserProfile profile = new UserProfile(plugin, user);
        profile.setLanguage(languageRegistry.getBasicLanguage());

        user.setProfile(profile);

        userRegistry.addUser(user);
        //}

//		if(!userRegistry.isUserExistInMemory(player.getUniqueId().toString())) {
//			
//			return;
//		}

        if (userRegistry.isUserExistInMemory(player.getUniqueId().toString())) {
            user = userRegistry.getUser(player.getUniqueId().toString());

            try {
                ResultSet resultSet = database.executeQuery("SELECT * FROM Users WHERE player_uuid = '" + player.getUniqueId().toString() + "'");
                resultSet.next();

                Language language = languageRegistry.getLanguage(resultSet.getString("spreek"));
                user.getProfile().setLanguage(language);

                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
