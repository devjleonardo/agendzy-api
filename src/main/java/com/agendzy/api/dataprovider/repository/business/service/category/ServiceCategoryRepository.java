package com.agendzy.api.dataprovider.repository.business.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<BusinessServiceCategory, String> {

    @Query("""
        SELECT c FROM BusinessServiceCategory c
        WHERE c.business.id = :businessId
    """)
    List<BusinessServiceCategory> findAllByBusinessId(@Param("businessId") String businessId);

    @Query("""
        SELECT c FROM BusinessServiceCategory c
        WHERE c.business.id = :businessId AND c.id = :categoryId
    """)
    Optional<BusinessServiceCategory> findByBusinessIdAndCategoryId(@Param("businessId") String businessId,
                                                                    @Param("entityId") String categoryId);

}
