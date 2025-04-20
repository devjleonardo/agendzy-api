package com.agendzy.api.dataprovider.repository.customer;

import com.agendzy.api.core.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("""
        SELECT c FROM Customer c
        WHERE c.user.id = :userId
    """)
    Optional<Customer> findByUserId(@Param("userId") String userId);

}
