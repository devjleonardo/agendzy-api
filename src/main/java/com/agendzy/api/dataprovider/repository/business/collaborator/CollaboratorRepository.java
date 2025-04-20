package com.agendzy.api.dataprovider.repository.business.collaborator;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, String> {

    @Query("""
        SELECT c FROM Collaborator c
        JOIN FETCH c.business
        WHERE c.user.id = :userId
    """)
    List<Collaborator> findCollaboratorsByUserId(@Param("userId") String userId);

    @Query("""
        SELECT c FROM Collaborator c
        JOIN FETCH c.business
        WHERE c.business.id = :businessId AND c.user.id = :userId
    """)
    Optional<Collaborator> findByUserIdAndBusinessId(@Param("businessId") String businessId,
                                                     @Param("userId") String userId);

    @Query("""
        SELECT c FROM Collaborator c
        WHERE c.business.id = :businessId
    """)
    List<Collaborator> findAllByBusinessId(@Param("businessId") String businessId);

    @Query("""
        SELECT c FROM Collaborator c
        WHERE c.business.id = :businessId AND c.id = :collaboratorId
    """)
    Optional<Collaborator> findByBusinessIdAndCollaboratorId(@Param("businessId") String businessId,
                                                             @Param("collaboratorId") String collaboratorId);

    @Query("""
        SELECT c FROM Collaborator c
        WHERE c.business.id = :businessId AND c.inviteEmail = :inviteEmail
    """)
    Optional<Collaborator> findByBusinessIdAndInviteEmail(@Param("businessId") String businessId,
                                                          @Param("inviteEmail") String inviteEmail);
}
