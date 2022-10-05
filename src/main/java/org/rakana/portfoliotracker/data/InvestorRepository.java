package org.rakana.portfoliotracker.data;

import org.rakana.portfoliotracker.models.Investor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends CrudRepository<Investor, Integer> {
}
