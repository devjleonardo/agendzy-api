package com.agendzy.api.dataprovider.repository.business.professional;

import com.agendzy.api.core.domain.business.professional.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, String> {

    @Query("""
        SELECT p FROM Professional p
        WHERE p.business.id = :businessId AND p.id = :professionalId
    """)
    Optional<Professional> findByBusinessIdAndProfessionalId(@Param("businessId") String businessId,
                                                             @Param("professionalId") String professionalId);

}
