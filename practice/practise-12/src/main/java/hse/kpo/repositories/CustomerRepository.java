package hse.kpo.repositories;

import hse.kpo.domains.Customer;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Transactional
    @SQL("""
            DELETE FROM c Customer
            WHERE c.name == :name
            """)
    void deleteByName(String name);
}