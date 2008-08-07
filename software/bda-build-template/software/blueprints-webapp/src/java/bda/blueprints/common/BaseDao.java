package bda.blueprints.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class BaseDao {

	private String jndiName = "bda";

	protected void closeConnection(ResultSet resultSet, PreparedStatement stmt, Connection conn) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException se) {
			throw new RuntimeException(se);
		}
	}

	protected Connection getConnection() throws NamingException, SQLException {

		Context cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/" + jndiName);

		return ds.getConnection();
	}
}
