package org.rakana.portfoliotracker.data;

import org.rakana.portfoliotracker.models.Investor;
import org.rakana.portfoliotracker.models.Portfolio;
import org.rakana.portfoliotracker.models.Security;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {
    Optional<Portfolio> findByInvestorAndSecurity(Investor investor, Security security);
}
