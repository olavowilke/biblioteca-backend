package br.com.biblioteca.util;

import br.com.biblioteca.exception_handler.exception.UnprocessableEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleaner {

    private static final String DATABASE_SCHEMA = "bibliotecaapi";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    public void clearTables() {
        try (Connection databaseConnection = dataSource.getConnection()) {
            this.connection = databaseConnection;
            checkTestDatabase();
            tryToClearTables();
        } catch (SQLException e) {
            throw new UnprocessableEntityException(e.getMessage());
        } finally {
            this.connection = null;
        }
    }

    private void checkTestDatabase() throws SQLException {
        String catalog = connection.getCatalog();

        if (catalog == null || !catalog.endsWith("test")) {
            throw new UnprocessableEntityException(String.format("Cannot clear database tables because '%s' is not a test database (suffix 'test' not found).", catalog));
        }
    }

    private void tryToClearTables() throws SQLException {
        List<String> tablenames = getTableNames();
        clear(tablenames);
    }

    private List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), DATABASE_SCHEMA, null, new String[]{"TABLE"});

        while (resultSet.next()) {
            tableNames.add(resultSet.getString("TABLE_NAME"));
        }
        tableNames.remove("flyway_schema_history");
        return tableNames;
    }

    public void clear(List<String> tableNames) throws SQLException {
        Statement statement = buildSqlStatement(tableNames);
        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();
        addTruncateStatements(tableNames, statement);
        return statement;
    }

    private void addTruncateStatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                String sql = String.format("TRUNCATE TABLE %s.%s CASCADE", DATABASE_SCHEMA, tableName);
                statement.addBatch(sql(sql));
            } catch (SQLException e) {
                throw new UnprocessableEntityException(e.getMessage());
            }
        });
    }

    private String sql(String sql) {
        logger.debug("Adding SQL: {}", sql);
        return sql;
    }

}
