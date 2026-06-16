package me.bananplayss.castlewars.core.kobalib.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KobaDatabase {

    private final File dbFile;
    private HikariDataSource dataSource;
    private final ExecutorService writeExecutor = Executors.newSingleThreadExecutor();

    public KobaDatabase(File dbFile) {
        this.dbFile = dbFile;
    }

    public void init() {
        try {
            if (!dbFile.getParentFile().exists()) {
                dbFile.getParentFile().mkdirs();
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());

            // SQLite optimal settings
            config.setMaximumPoolSize(2);
            config.setMinimumIdle(1);
            config.setMaxLifetime(0);
            config.setConnectionTimeout(10000);
            config.setPoolName("SQLite-Pool");

            // PRAGMA init
            config.setConnectionInitSql(
                    "PRAGMA journal_mode=WAL;" +
                            "PRAGMA synchronous=NORMAL;" +
                            "PRAGMA temp_store=MEMORY;" +
                            "PRAGMA busy_timeout=5000;"
            );

            this.dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Failed to init database", e);
        }
    }

    // =========================
    // WRITE (async, single thread)
    // =========================
    public void executeWrite(SQLConsumer<Connection> action) {
        writeExecutor.submit(() -> {
            try {
                executeWithRetry(conn -> {
                    action.accept(conn);
                    return null;
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void executeRead(SQLConsumer<Connection> action) {
        try (Connection conn = dataSource.getConnection()) {
            action.accept(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // READ (sync or async caller dönt)
    // =========================
    public <T> T executeQuery(SQLFunction<Connection, T> action) {
        try {
            return executeWithRetry(action);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeWriteBlocking(SQLConsumer<Connection> action) {
        try (Connection conn = dataSource.getConnection()) {
            action.accept(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // =========================
    // CORE RETRY LOGIC
    // =========================
    private <T> T executeWithRetry(SQLFunction<Connection, T> action) throws SQLException {
        int retries = 5;
        long delay = 50;

        for (int i = 0; i < retries; i++) {
            try (Connection conn = dataSource.getConnection()) {
                return action.apply(conn);

            } catch (SQLException e) {
                if (isLockError(e)) {
                    sleep(delay);
                    delay *= 2;
                    continue;
                }
                throw e;
            }
        }

        throw new SQLException("Database locked after retries");
    }

    private boolean isLockError(SQLException e) {
        String msg = e.getMessage();
        return msg != null && msg.toLowerCase().contains("database is locked");
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    // =========================
    // SHUTDOWN
    // =========================
    public void shutdown() {
        writeExecutor.shutdown();

        if (dataSource != null) {
            dataSource.close();
        }
    }

    // =========================
    // FUNCTIONAL INTERFACES
    // =========================
    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}