package de.morrisbr.language.user.propety.registry;

import de.morrisbr.language.LanguagePlugin;
import de.morrisbr.language.database.LiteSQL;
import de.morrisbr.language.user.User;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserRegistry {

	private final Registry registry;
	private final HashMap<String, User> users = new HashMap<String, User>();

	public UserRegistry(Registry registry) {
		this.registry = registry;
	}


	/**
	 * Add user to Memory.
	 * Add user to DataBase.
	 * <p>
	 * (User Register)
	 *
	 * @param user
	 */
	public void addUser(User user) {

		LiteSQL databank = getRegistry().getPlugin().getDataBase();

		LanguagePlugin plugin = registry.getPlugin();
		ConsoleCommandSender consoleSender = plugin.getServer().getConsoleSender();
		Player player = plugin.getServer().getPlayer(UUID.fromString(user.getUuid()));

		users.put(user.getUuid(), user);

		if (!isUserExistInDataBase(user.getUuid())) {
			databank.execute("INSERT OR IGNORE INTO Users " + "VALUES ('" + user.getUuid() + "', '" + user.getProfile().getLanguage().getName() + "')");
		} else
			consoleSender.sendMessage(ChatColor.YELLOW + "LanguageAPI: The User(" + player.getName() + ", " + user.getUuid() + ") is already registered!");
	}


	/**
	 *
	 * Get a User from uuid.
	 * Not stable, can return NULL
	 *
	 * @param uuid
	 * @return User
	 */
	public User getUser(String uuid) {
		return users.get(uuid);
	}


	/**
	 *
	 * Check is User in Memory registed.
	 * User is Registed, when his Joined.
	 *
	 * Returnt: True - when is in Memory.
	 *
	 * Returnt: False - when is not in Memory.
	 *
	 * @param uuid
	 * @return boolean
	 */
	public boolean isUserExistInMemory(String uuid) {
		return getUser(uuid) != null;
	}


	/**
	 *
	 * Check is User in DataBase registed.
	 * User is Registed, when his Joined.
	 *
	 * Returnt: True - when is in DataBase.
	 *
	 * Returnt: False - when is not in DataBase.
	 *
	 * @param uuid
	 * @return boolean
	 */
	public boolean isUserExistInDataBase(String uuid) {

		LiteSQL database = getRegistry().getPlugin().getDataBase();

		try {
			ResultSet resultSet = database.executeQuery("SELECT * FROM Users WHERE player_uuid='" + uuid + "'");
			if (resultSet.next())
				resultSet.close();
			return true;
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}


//	public boolean isUserExistInDataBase(String uuid) {
//
//	LiteSQL database = getRegistry().getPlugin().getDataBase();
//
//	try {
//
//		boolean isExist;
//
//		ResultSet resultSet = database.executeQuery("SELECT count(spreek),spreek FROM Users WHERE player_uuid = " + uuid);
//		resultSet.next();
//
//		isExist = resultSet.getInt("count(spreek)") == 1;
//		resultSet.close();
//
//		return isExist;
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//		return false;
//	}
//}

	/**
	 *
	 * Get the Registry.
	 *
	 * @return Registry
	 */
	public Registry getRegistry() {
		return registry;
	}

}
