package data;

import models.Transaction;

import java.util.List;

public interface TransactionDao {

	List<Transaction> getAll();

	List<Transaction> getByAmount(String lessOrGreater);

	List<Transaction> getByMonth(String minDate, String maxDate);

	List<Transaction> getByVendor(String vendor);

	Transaction addTransaction(Transaction transaction);

}
