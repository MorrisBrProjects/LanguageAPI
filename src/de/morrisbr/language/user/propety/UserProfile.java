package de.morrisbr.language.user.propety;

import de.morrisbr.language.LanguagePlugin;
import de.morrisbr.language.database.LiteSQL;
import de.morrisbr.language.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfile {

    private final LanguagePlugin plugin;
    private Language language;
    private User user;

    public UserProfile(LanguagePlugin plugin) {
        this.plugin = plugin;
    }

    public UserProfile(LanguagePlugin plugin, User user) {
        this.plugin = plugin;
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }


    /**
     * Set language in Memory
     *
     * @param language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }


    /**
     * Set language in LiteSQL and MEMORY
     * Warning, Have Use the Plugin Construktor
     * for use this Methode!
     *
     * @param language
     */
    public void changeLanguage(String languageKey) {
        LiteSQL database = plugin.getDataBase();

        Language language = plugin.getRegistry().getLanguageRegistry().getLanguage(languageKey);
        setLanguage(language);

        if (!isAlreadyLanguageSet()) {
            System.out.println("insert");
            database.execute("INSERT INTO Users " + "VALUES ('" + user.getUuid() + "', '" + language.getName() + "')");
        }
    }

    private boolean isAlreadyLanguageSet() {
        LiteSQL database = plugin.getDataBase();

        try {
            ResultSet resultSet = database.executeQuery("SELECT spreek FROM Users WHERE spreek='" + user.getProfile().getLanguage().getName() + "'");
            if (resultSet.next()) {
                resultSet.close();
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    // Warning, Have Use the Plugin Construktor
    //for use this Methode!

    /**
     * Set language in LiteSQL and MEMORY
     *
     * @param language
     */
    public void changeLanguage(Language language) {
        LiteSQL database = plugin.getDataBase();
        setLanguage(language);

        if (!isAlreadyLanguageSet()) {
            database.execute("INSERT INTO Users " + "VALUES ('" + user.getUuid() + "', '" + language.getName() + "')");
        }
    }

    public User getUser() {
        return user;
    }


}
