package configurations;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConfig {

	private static final BasicDataSource basicDataSource = new BasicDataSource();

	public static BasicDataSource setDataSource() {
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/accounting_ledger");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword(System.getenv("SQL_PASSWORD"));
		return basicDataSource;
	}

}
