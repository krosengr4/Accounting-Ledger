package data;

import models.Transaction;

import java.util.List;

public interface TransactionDao {

	List<Transaction> getAll();

	List<Transaction> getByMonth();

}
