/**
 *
 */
package org.mycompany.connector;
import org.bonitasoft.engine.connector.ConnectorException;
import java.sql.*;

/**
 * The connector execution will follow the steps
 * 1 - setInputParameters() --> the connector receives input parameters values
 * 2 - validateInputParameters() --> the connector can validate input parameters values
 * 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
 * 4 - executeBusinessLogic() --> execute the connector
 * 5 - getOutputParameters() --> output are retrieved from connector
 * 6 - disconnect() --> the connector can close connection to remote server (if any)
 */
public class JDBCInsertConnectorImpl extends AbstractJDBCInsertConnectorImpl {
    private ResultSet data;
    private Connection connection;

    @Override
    protected void executeBusinessLogic() throws ConnectorException {
        try {
            final String command = getScript().toUpperCase().trim();
            if (command.startsWith("SELECT") || command.startsWith("UPDATE")) {
                throw new ConnectorException("This connector only handled insert queries. There is no support for SELECT or UPDATE Queries");
            } else {
                executeCommand(getScript());
                data = select("SELECT LAST_INSERT_ID() as id ;");
                while (data.next()) {
                    setId(new Long(data.getLong(1)));
                }
            }
        } catch (final SQLException sqle) {
            throw new ConnectorException(sqle);
        }
    }

    @Override
    public void connect() throws ConnectorException {
        try {
            Class.forName(getDriver());
            connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
        } catch (final Exception e) {
            throw new ConnectorException(e);
        }

    }

    @Override
    public void disconnect() throws ConnectorException {
        try {
            if (data != null) {
                data.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw new ConnectorException(e);
        }
    }

    private boolean executeCommand(final String command) throws SQLException, ConnectorException {
        Statement statement = null;
        boolean isExecuted = false;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            isExecuted = statement.execute(command);
        } catch (SQLException e) {
            throw new ConnectorException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return isExecuted;
    }

    private ResultSet select(final String query) throws SQLException {
        final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery(query);
    }

}
