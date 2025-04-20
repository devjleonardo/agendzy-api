package com.agendzy.api.dataprovider.repository.common;

import com.agendzy.api.core.domain.common.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByEmail(String email);

}
