package com.agendzy.api.dataprovider.repository.business.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<BusinessService, String> {

    @Query("""
        SELECT bs FROM BusinessService bs
        WHERE bs.business.id = :businessId
    """)
    List<BusinessService> findAllByBusinessId(@Param("businessId") String businessId);

    @Query("""
        SELECT bs FROM BusinessService bs
        WHERE bs.business.id = :businessId AND bs.id = :serviceId
    """)
    Optional<BusinessService> findByBusinessIdAndServiceId(@Param("businessId") String businessId,
                                                           @Param("serviceId") String serviceId);

}
