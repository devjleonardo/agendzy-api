package com.agendzy.api.dataprovider.repository.business;

import com.agendzy.api.core.domain.business.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {

}
