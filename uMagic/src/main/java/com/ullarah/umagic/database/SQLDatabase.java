package com.ullarah.umagic.database;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

abstract class SQLDatabase {

    Connection connection;
    private Plugin plugin;
    private String table;

    SQLDatabase(Plugin plugin, String table) {
        this.plugin = plugin;
        this.table = table;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private String getTable() {
        return table;
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

            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTable());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionSuccess(getTable()));

            close(ps, rs, null);

        } catch (SQLException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionFailure(), ex);

        }

    }

    public ResultSet getResult(String statement) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = getSQLConnection();
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {

            plugin.getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionExecuteFailure(), ex);

        } finally {

            close(preparedStatement, resultSet, connection);

        }

        return resultSet;

    }


    public boolean insertRow(String column, String value) {

        return runStatement("INSERT INTO " + getTable() + " (" + column + ") VALUES (" + value + ");");

    }

    public boolean insertRow(String[] columns, String[] values) {

        return runStatement("i", "INSERT INTO " + getTable() + " %%columns%% %%values%%", columns, values);

    }

    public boolean updateRow(String column, String oldValue, String newValue) {

        return runStatement("UPDATE " + getTable() + " SET " + column + " = " + newValue
                + " WHERE " + column + " = " + oldValue);

    }

    public boolean updateRow(String[] columns, String[] oldValues, String[] newValues) {

        return runStatement("u", "UPDATE " + getTable() + " SET ", columns, oldValues, newValues);

    }

    public boolean deleteRow(String column, String value) {

        return runStatement("DELETE FROM " + getTable() + " WHERE " + column + " = " + value);

    }

    public boolean deleteRow(String[] columns, String[] values) {

        return runStatement("d", "DELETE FROM " + getTable() + " WHERE ", columns, values);

    }

    public boolean runStatement(String statement) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = getSQLConnection();
            preparedStatement = connection.prepareStatement(statement);

        } catch (SQLException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionExecuteFailure(), ex);
            return false;

        } finally {

            close(preparedStatement, null, connection);

        }

        return true;

    }

    private boolean runStatement(String type, String statement, String[] columns, String[]... values) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = getSQLConnection();

            switch (type.toLowerCase()) {

                case "i":
                    if (columns.length != values.length) {
                        getPlugin().getLogger().log(Level.SEVERE, SQLMessage.noColumnValueMatch());
                        return false;
                    }

                    preparedStatement = connection.prepareStatement(
                            statement.replaceFirst("%%columns%%", "(" + StringUtils.join(columns, ",") + ") ")
                                    .replaceFirst("%%values%%", "VALUES (" + StringUtils.join(values, ",") + ")"));

                    for (String[] valueArray : values)
                        for (int i = 0; i < columns.length; i++) {

                            String value = valueArray[i];

                            if (value.matches("\\d+")) preparedStatement.setInt(i + 1, Integer.parseInt(value));
                            else preparedStatement.setString(i + 1, value);

                        }
                    break;

                case "u":
                    String[] oldValues = values[0];
                    String[] newValues = values[1];

                    if ((columns.length != oldValues.length) || (columns.length != newValues.length)) {
                        getPlugin().getLogger().log(Level.SEVERE, SQLMessage.noColumnValueMatch());
                        return false;
                    }

                    for (int i = 0; i < columns.length; i++) {

                        String oldValue = oldValues[i];
                        String newValue = newValues[i];

                        if (oldValue.matches("\\d+") && newValue.matches("\\d+")) {

                            statement = i < columns.length
                                    ? statement + columns[i] + " = " + newValue + ", "
                                    : statement + columns[i] + " = " + newValue;

                        }

                    }

                    statement = statement + " WHERE ";

                    for (int i = 0; i < columns.length; i++) {

                        String oldValue = oldValues[i];
                        String newValue = newValues[i];

                        if (oldValue.matches("\\d+") && newValue.matches("\\d+")) {

                            statement = i < columns.length
                                    ? statement + columns[i] + " = " + oldValue + ", "
                                    : statement + columns[i] + " = " + oldValue;

                        }

                    }
                    preparedStatement = connection.prepareStatement(statement);
                    break;

                case "d":
                    if (columns.length != values.length) {
                        getPlugin().getLogger().log(Level.SEVERE, SQLMessage.noColumnValueMatch());
                        return false;
                    }

                    for (int i = 0; i < columns.length; i++) {

                        statement = i < columns.length
                                ? statement + columns[i] + " = " + values[i] + " AND "
                                : statement + columns[i] + " = " + values[i];

                    }
                    preparedStatement = connection.prepareStatement(statement);
                    break;

            }

        } catch (SQLException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionExecuteFailure(), ex);
            return false;

        } finally {

            close(preparedStatement, null, connection);

        }

        return true;

    }

    private void close(PreparedStatement ps, ResultSet rs, Connection cn) {

        try {

            if (ps != null) ps.close();
            if (rs != null) rs.close();
            if (cn != null) cn.close();

        } catch (SQLException ex) {

            getPlugin().getLogger().log(Level.SEVERE, SQLMessage.sqlConnectionCloseFailure(), ex);

        }

    }

}
