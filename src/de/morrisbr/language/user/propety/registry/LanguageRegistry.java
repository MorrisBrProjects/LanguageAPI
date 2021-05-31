package de.morrisbr.language.user.propety.registry;

import de.morrisbr.language.LanguagePlugin;
import de.morrisbr.language.database.LiteSQL;
import de.morrisbr.language.user.propety.Language;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LanguageRegistry {

	private Language basicLanguage;
	private final Registry registry;

	private final HashMap<String, Language> languages = new HashMap<String, Language>();



	public LanguageRegistry(Registry registry) {
		this.registry = registry;
		this.basicLanguage = new Language("DEMO_DEMO", "DEMO_DEMO.yml");
	}


	/**
	 * Get the Language Map whif all Languages and keyNames
	 *
	 * @return HashMap<String, Language>
	 */

	public HashMap<String, Language> getLanguagesMap() {
		return languages;
	}

	/**
	 *
	 * Get a list of all registered languages
	 *
	 * @return List<Language>
	 */
	public List<Language> getLanguages() {
		return (List<Language>) getLanguagesMap().values();
	}


	/**
	 *
	 * Get a SET of all the names of the registered languages
	 *
	 * @return Set<String>
	 */
	public Set<String> getLanguagesAsName() {
		return getLanguagesMap().keySet();
	}


	/**
	 *
	 * Get Language by Language Name
	 *
	 * @param languageKey
	 * @return Language
	 */

	public Language getLanguage(String languageKey) {
		return languages.get(languageKey);
	}


	public boolean isLanguageExist(String languageKey) {
		return getLanguage(languageKey) != null;
	}

	public boolean isLanguageExist(Language language) {
		return getLanguage(language.getName()) != null;
	}


	/**
	 * Get a Language by Config path.
	 *
	 * @param languagePath
	 * @return Language
	 */
	public Language getLanguageByPath(String languagePath) {
		for (Language language : languages.values()) {
			if (language.getConfigPath().equals(languagePath)) {
				return language;
			}
		}
		return null;
	}


	/**
	 *
	 * Register a Language.
	 * After the Methode can you use
	 * this Language.
	 *
	 * @param spreek
	 * @param plugin
	 */

	public void register(String spreek, JavaPlugin plugin) {

		//plugin.getDataFolder().mkdirs();

		Path path = Paths.get(plugin.getDataFolder().toString()).resolve("translations");
		path.toFile().mkdirs();

		Language language = new Language(spreek, path.resolve(spreek + ".yml").toString());
		language.load(plugin);

		languages.put(spreek, language);
		System.out.println(">>> " + language.getConfigPath());
	}



	/**
	 *
	 * Unregister a Language.
	 * You can`t after this Methode
	 * this Language call!
	 *
	 * @param languageKey
	 */

	public void unRegister(String languageKey) {
		getLanguage(languageKey).unLoad();
		getLanguagesMap().remove(languageKey);
	}


	public Language getLanguageFromDataBase(String uuid) {

		LanguagePlugin languagePlugin = getRegistry().getPlugin();
		LiteSQL database = languagePlugin.getDataBase();

		try {
			ResultSet resultSet = database.executeQuery("SELECT * FROM Users WHERE player_uuid = '" + uuid + "'");
			resultSet.next();

			Language language = getLanguage(resultSet.getString("spreek"));

			resultSet.close();
			return language;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *
	 * Set the Default Language.
	 *
	 * The Default Language is need
	 * to set a not registed Player
	 * a Language.
	 *
	 * @param name
	 */
	public void setDefaultLanguage(String name) {
		//basic = new Language(name, configPath);
		basicLanguage = getLanguage(name);
	}


	/**
	 *
	 * Get the basic Language.
	 *
	 * @return Language
	 */
	public Language getBasicLanguage() {
		return basicLanguage;
	}


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
