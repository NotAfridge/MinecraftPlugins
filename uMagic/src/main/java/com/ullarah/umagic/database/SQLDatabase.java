package com.ullarah.umagic.database;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

abstract class SQLDatabase {

    private final Plugin plugin;
    private final String table;
    private final SQLMessage message;
    Connection connection;

    SQLDatabase(Plugin plugin, String table) {
        this.plugin = plugin;
        this.table = table;
        message = new SQLMessage();
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private String getTable() {
        return table;
    }

    private SQLMessage getMessage() {
        return message;
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract Connection getSQLConnection();

    void initSQLConnection() {

        setConnection(getSQLConnection());

        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM " + getTable());
            ResultSet resultSet = preparedStatement.executeQuery();

            try {

                preparedStatement.close();
                resultSet.close();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        } catch (SQLException e) {

            getPlugin().getLogger().log(Level.SEVERE, getMessage().sqlConnectionFailure(), e);

        }

    }

    public void closeSQLConnection() {

        try {

            getSQLConnection().close();

        } catch (SQLException e) {

            getPlugin().getLogger().log(Level.SEVERE, getMessage().sqlConnectionCloseFailure(), e);

        }

    }

    public ResultSet getResult(String statement) {

        try {

            return getSQLConnection().prepareStatement(statement).executeQuery();

        } catch (SQLException e) {

            plugin.getLogger().log(Level.SEVERE, getMessage().sqlConnectionExecuteFailure(), e);

        }

        return null;

    }

    public void runStatement(String statement) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = getSQLConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.execute();

        } catch (SQLException e) {

            getPlugin().getLogger().log(Level.SEVERE, getMessage().sqlConnectionExecuteFailure(), e);

        } finally {

            try {

                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {

                getPlugin().getLogger().log(Level.SEVERE, getMessage().sqlConnectionCloseFailure(), e);

            }

        }

    }

}
