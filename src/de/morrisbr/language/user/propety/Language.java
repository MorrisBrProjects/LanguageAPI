package de.morrisbr.language.user.propety;

import de.morrisbr.language.user.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Language {

	private String name;
	private String configPath;

	private HashMap<String, Message> messages;

	public Language(String name, String configPath) {
		this.name = name;
		this.configPath = configPath;

		this.messages = new HashMap<String, Message>();
	}

	public Language(String name) {
		this.name = name;
	}


	public Language() {
	}

	/**
	 * Load The Language.
	 * His put all Messages from this Language Config in Memory.
	 * His create auto a new language YML file.
	 *
	 * @param plugin
	 */
	public void load(JavaPlugin plugin) {
		File config = new File(plugin.getDataFolder(), "translations/" + name + ".yml");
		FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(config);
		this.configPath = config.getAbsolutePath();
		try {
			if (!config.exists()) {
				dataConfig.save(config);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (dataConfig.getKeys(true).isEmpty()) return;

		//create by register 1x die yml f�r die sprache
		for (String key : dataConfig.getKeys(true)) {

			Message message = new Message();

			message.setContent(dataConfig.getString(key));
			getMessagesMap().put(key, message);
		}

	}

	/**
	 * Delete all messages in Memory
	 */
	public void unLoad() {
		messages.clear();
	}

	public List<Message> getMessages() {

		List<Message> messages = new ArrayList<Message>();

		for (Message message : getMessagesMap().values()) {
			messages.add(message);
		}

		return messages;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public String getConfigPath() {
		return configPath;
	}

	public HashMap<String, Message> getMessagesMap() {
		return messages;
	}
	
	public Message getMessage(String keyName) {
		return messages.get(keyName);
	}
	
	public String getMessageContent(String keyName) {
		return getMessage(keyName).getContent();
	}
	
	public void setMessage(String keyName, Message message) {
		messages.put(keyName, message);
	}
	
}
