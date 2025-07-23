package data.mysql;

import configurations.DatabaseConfig;
import data.TransactionDao;
import models.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
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
		String query = "SELECT * FROM transactions;";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);

			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Transaction transaction = mapRow(results);
				transactionList.add(transaction);
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return transactionList;
	}

	@Override
	public Transaction getById(int transactionId) {
		String query = "SELECT * FROM transactions " +
							   "WHERE transaction_id = ?;";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, transactionId);

			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return mapRow(result);
			} else {
				System.out.println("Couldn't find transaction with that ID...");
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public List<Transaction> getByAmount(String lessOrGreater) {
		List<Transaction> transactionList = new ArrayList<>();
		String query = "SELECT * FROM transactions " +
							   "WHERE amount ? 0;";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, lessOrGreater);

			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Transaction transaction = mapRow(results);
				transactionList.add(transaction);
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return transactionList;
	}

	@Override
	public List<Transaction> getByMonth(String minDate, String maxDate) {
		List<Transaction> transactionList = new ArrayList<>();
		String query = "SELECT * FROM transactions " +
							   "WHERE date BETWEEN '?' AND '?';";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, minDate);
			statement.setString(2, maxDate);

			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Transaction transaction = mapRow(results);
				transactionList.add(transaction);
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return transactionList;
	}

	@Override
	public List<Transaction> getByVendor(String vendor) {
		List<Transaction> transactionList = new ArrayList<>();
		String query = "SELECT * FROM transactions " +
							   "WHERE vendor LIKE ?;";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, "%" + vendor + "%");

			ResultSet results = statement.executeQuery();
			while(results.next()) {
				transactionList.add(mapRow(results));
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return transactionList;
	}

	@Override
	public Transaction addTransaction(Transaction transaction) {
		String query = "INSERT INTO transactions (date, time, description, vendor, amount) " +
							   "VALUES (?, ?, ?, ?, ?);";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, Date.valueOf(LocalDate.now()));
			statement.setTime(2, Time.valueOf(LocalTime.now()));
			statement.setString(3, transaction.getDescription());
			statement.setString(4, transaction.getDescription());
			statement.setDouble(5, transaction.getAmount());

			int rows = statement.executeUpdate();
			if(rows > 0) {
				System.out.println("Success! The transaction was added!");
				ResultSet key = statement.getGeneratedKeys();

				if(key.next()) {
					int transactionId = key.getInt(1);
					return getById(transactionId);
				}
			} else {
				System.err.println("ERROR! Could not add transaction!!!");
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
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
