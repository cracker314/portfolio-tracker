package org.rakana.portfoliotracker.data;

import org.rakana.portfoliotracker.models.Security;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends CrudRepository<Security, Integer> {
    Security findByName(String name); // primarily used to get the security with name: Cash
}
