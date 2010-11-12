package bda.blueprints.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
	
	private ServiceLocator() {}

	public static DataSource getDataSource(String dataSourceJndiName) {
		DataSource dataSource = null;
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup(dataSourceJndiName);

		} catch (ClassCastException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} catch (NamingException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		return dataSource;
	}

}
