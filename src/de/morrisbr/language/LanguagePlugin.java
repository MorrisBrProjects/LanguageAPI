package de.morrisbr.language;

import de.morrisbr.language.database.LiteSQL;
import de.morrisbr.language.listener.PlayerJoinListener;
import de.morrisbr.language.user.propety.registry.Registry;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LanguagePlugin extends JavaPlugin {

	private LiteSQL database;
	private Registry registry;

	@Override
	public void onEnable() {
		database = new LiteSQL(this);
		database.connect();

		registry = new Registry(this);

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

		getRegistry().getLanguageRegistry().register("DEMO_DEMO", this);
		getRegistry().getLanguageRegistry().setDefaultLanguage("DEMO_DEMO");

		for (Player player : getServer().getOnlinePlayers()) {
			player.kickPlayer("§7[§a§lLanguageAPI§f§7] §cAll Players was kicked, Please Rejoin!");
		}
	}


	@Override
	public void onDisable() {
		database.disconnect();
	}


	public LiteSQL getDataBase() {
		return database;
	}

	public Registry getRegistry() {
		return registry;
	}


}
