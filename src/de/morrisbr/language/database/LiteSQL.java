package de.morrisbr.language.database;

import de.morrisbr.language.LanguagePlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class LiteSQL {

    private Connection connection;
    private Statement statemant;
    private final LanguagePlugin plugin;

    public LiteSQL(LanguagePlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        connection = null;

        try {

            plugin.getDataFolder().mkdir();
            Path path = Paths.get(plugin.getDataFolder().toString(), "UserDatabase.db");
            File file = path.toFile();
            if (!file.exists()) {
                file.createNewFile();
            }

            String url = "jdbc:sqlite:" + file.getPath();
            connection = DriverManager.getConnection(url);

            statemant = connection.createStatement();

            System.out.println("Verbindung zur Datanbank hergestellt!");

            execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "	player_uuid	VARCHAR(50)," +
                    "	spreek	VARCHAR(10)" +
                    ");");

//			execute("CREATE TABLE IF NOT EXISTS Users (" + 
//					"	player_uuid	VARCHAR(50) UNIQUE," + 
//					"	spreek	VARCHAR(10)" + 
//					");");


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Verbindung zur Datanbank getrennt!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql) {
        try {
            statemant.execute(sql);
            statemant.close(); //da
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            return statemant.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
