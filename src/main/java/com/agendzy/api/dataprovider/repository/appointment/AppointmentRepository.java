package com.agendzy.api.dataprovider.repository.appointment;

import com.agendzy.api.core.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.business.id = :businessId
        AND a.professional.id = :professionalId
        AND a.startTime >= :startOfDay
        AND a.startTime < :endOfDay
    """)
    List<Appointment> findByBusinessIdAndProfessionalIdAndDate(@Param("businessId") String businessId,
                                                               @Param("professionalId") String professionalId,
                                                               @Param("startOfDay") LocalDateTime startOfDay,
                                                               @Param("endOfDay") LocalDateTime endOfDay
    );
}
