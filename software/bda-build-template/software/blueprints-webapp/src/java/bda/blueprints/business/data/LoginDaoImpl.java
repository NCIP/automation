package bda.blueprints.business.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import bda.blueprints.common.BaseDao;

public class LoginDaoImpl extends BaseDao implements LoginDao {

	public void login(String sql, String username, String password) throws InvalidLoginException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean foundUser = false;
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			while (rs.next()) {
				foundUser = true;
			}
			if (!foundUser) {
				throw new InvalidLoginException();
			}
		} catch (SQLException se) {
			throw new InvalidLoginException();
		} catch (NamingException e) {
			throw new InvalidLoginException();
		} finally {
			closeConnection(rs, stmt, conn);
		}

	}
}
