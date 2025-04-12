package hse.kpo.repositories;

import hse.kpo.domains.Customer;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @SQL("""
            DELETE FROM c Customer
            WHERE c.name == :name
            RETURNING true
            """)
    Boolean deleteByName(String name);
}