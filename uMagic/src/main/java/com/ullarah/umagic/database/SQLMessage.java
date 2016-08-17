package com.ullarah.umagic.database;

class SQLMessage {

    static String sqlConnectionExecuteFailure() {
        return "Failed to execute statement: ";
    }

    static String sqlConnectionCloseFailure() {
        return "Failed to close connection: ";
    }

    static String sqlConnectionFailure() {
        return "Failed to retrieve connection: ";
    }

    static String sqlConnectionSuccess(String table) {
        return "Failed to find table: " + table;
    }

    static String sqlInitialiseError() {
        return "Failed to initialise database: ";
    }

    static String sqlLibraryNotFound() {
        return "SQLite JBDC library not found.";
    }

    static String noColumnValueMatch() {
        return "Values do not match number of Columns.";
    }

}
