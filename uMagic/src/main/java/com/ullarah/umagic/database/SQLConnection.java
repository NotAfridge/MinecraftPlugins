package com.ullarah.umagic.database;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLConnection extends SQLDatabase {

    private Plugin plugin;
    private String database;
    private String table;

    public SQLConnection(Plugin plugin, String database, String table) {
        super(plugin, table);
        this.plugin = plugin;
        this.database = database;
        this.table = table;

        startSQLConnection();
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private String getDatabase() {
        return database;
    }

    private String getTable() {
        return table;
    }

    public Connection getSQLConnection() {

        boolean databaseFileCreation = true;

        File dataDir = getPlugin().getDataFolder();
        if (!dataDir.exists()) databaseFileCreation = dataDir.mkdir();

        File databaseFile = new File(dataDir + File.separator + getDatabase() + ".db");
        if (!databaseFile.exists()) databaseFileCreation = true;

        if (databaseFileCreation) try {

            if (getConnection() != null && !getConnection().isClosed()) return getConnection();

            Class.forName("org.sqlite.JDBC");
            setConnection(DriverManager.getConnection("jdbc:sqlite:" + databaseFile));

            return getConnection();

        } catch (SQLException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlInitialiseError(), ex);

        } catch (ClassNotFoundException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlLibraryNotFound());

        }

        return null;

    }

    private void startSQLConnection() {

        connection = getSQLConnection();

        try {

            Statement s = connection.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS " + getTable() + " (" +
                    "`data` varchar(32) NOT NULL,`world` varchar(32) NOT NULL," +
                    "`locX` int(11) NOT NULL,`locY` int(11) NOT NULL,`locZ` int(11) NOT NULL," +
                    "PRIMARY KEY (`data`));");
            s.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        initSQLConnection();

    }

}