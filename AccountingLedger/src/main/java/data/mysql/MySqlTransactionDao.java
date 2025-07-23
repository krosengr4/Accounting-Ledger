package data.mysql;

import configurations.DatabaseConfig;
import data.TransactionDao;
import models.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlTransactionDao extends MySqlBaseDao implements TransactionDao {

	static BasicDataSource dataSource = DatabaseConfig.setDataSource();

	public MySqlTransactionDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Transaction> getAll() {
		List<Transaction> transactionList = new ArrayList<>();


		return transactionList;
	}

	private Transaction mapRow(ResultSet result) throws SQLException {
		int transactionId = result.getInt("transaction_id");
		LocalDate date = result.getTimestamp("date").toLocalDateTime().toLocalDate();
		LocalTime time = result.getTimestamp("time").toLocalDateTime().toLocalTime();
		String description = result.getString("description");
		String vendor = result.getString("vendor");
		double amount = result.getDouble("amount");

		return new Transaction(transactionId, date, time, description, vendor, amount);
	}

}
