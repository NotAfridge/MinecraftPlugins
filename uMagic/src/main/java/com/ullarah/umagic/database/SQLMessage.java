package com.ullarah.umagic.database;

public class SQLMessage {

    String sqlConnectionExecuteFailure() {
        return "Failed to execute statement: ";
    }

    String sqlConnectionCloseFailure() {
        return "Failed to close connection: ";
    }

    public String sqlConnectionFailure() {
        return "Failed to retrieve connection: ";
    }

    String sqlInitialiseError() {
        return "Failed to initialise database: ";
    }

    String sqlLibraryNotFound() {
        return "SQLite JBDC library not found.";
    }

}
