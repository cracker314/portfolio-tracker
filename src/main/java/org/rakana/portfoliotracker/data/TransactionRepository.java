package org.rakana.portfoliotracker.data;

import org.rakana.portfoliotracker.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
