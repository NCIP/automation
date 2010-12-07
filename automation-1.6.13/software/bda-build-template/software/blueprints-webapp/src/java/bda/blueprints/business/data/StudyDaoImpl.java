package bda.blueprints.business.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.naming.NamingException;

import bda.blueprints.business.domain.State;
import bda.blueprints.business.domain.Study;
import bda.blueprints.common.BaseDao;

public class StudyDaoImpl extends BaseDao implements StudyDao {

	public StudyDaoImpl() {
		// StudyServiceImpl impl = new StudyServiceImpl();
		// impl.runThis();
	}

	public int create(String sql, Study study) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println("study.getName()=" + study.getName());
		System.out.println("study.getResearcher()=" + study.getResearcher());
		Random generator = new Random();
		int r = generator.nextInt();
		String s = Integer.toString(r);
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, s);
			stmt.setString(2, study.getName());
			stmt.setString(3, study.getResearcher());
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException se) {
			// TODO: Clean this
			throw new RuntimeException(se);
		} catch (NamingException e) {
			// TODO: Clean this
			throw new RuntimeException(e);
		} finally {
			closeConnection(rs, stmt, conn);
		}
		return 0;
	}

	public Collection findAll(String sql) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection results = new ArrayList();
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Study study = new Study();
				study.setName(rs.getString("study_name"));
				study.setResearcher(rs.getString("researcher"));
				study.setDateReceived(rs.getString("date_received"));
				results.add(study);
			}
		} catch (SQLException se) {
			// TODO: Clean this
			throw new RuntimeException(se);
		} catch (NamingException e) {
			// TODO: Clean this
			throw new RuntimeException(e);
		} finally {
			closeConnection(rs, stmt, conn);
		}
		return results;
	}

	public Collection findAllStates(String sql) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection states = new ArrayList();
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				State state = new State();
				state.setState(rs.getString("state"));
				state.setDescription(rs.getString("description"));
				states.add(state);
			}
		} catch (SQLException se) {
			// TODO: Clean this
			throw new RuntimeException(se);
		} catch (NamingException e) {
			// TODO: Clean this
			throw new RuntimeException(e);
		} finally {
			closeConnection(rs, stmt, conn);
		}
		return states;
	}

}
