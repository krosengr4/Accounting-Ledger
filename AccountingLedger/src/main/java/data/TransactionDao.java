package data;

import models.Transaction;

import java.util.List;

public interface TransactionDao {

	List<Transaction> getAll();

	Transaction getById(int transactionId);

	List<Transaction> getDeposits();

	List<Transaction> getPayments();

	List<Transaction> getByDate(String minDate, String maxDate);

	List<Transaction> getByVendor(String vendor);

	Transaction addTransaction(Transaction transaction);

}
