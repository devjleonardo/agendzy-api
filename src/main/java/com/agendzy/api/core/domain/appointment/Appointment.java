package com.agendzy.api.core.domain.appointment;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.common.BaseEntity;
import com.agendzy.api.core.domain.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment extends BaseEntity {

    private String customerName; // usado quando customer == null

    private String customerPhone; // para confirmação via WhatsApp

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String notes; // visível para o profissional

    private String customerNotes; // visível apenas para o cliente

    private boolean confirmedByCustomer;

    private boolean notified; // lembrete enviado?

    private Integer preparationTimeInMin; // tempo extra antes

    private Integer cleanupTimeInMin; // tempo extra depois

    private boolean walkIn; // se foi criado diretamente pelo profissional

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(optional = false)
    private Business business;

    @ManyToOne(optional = false)
    private Professional professional;

    @ManyToOne(optional = false)
    private BusinessService service;

    @ManyToOne
    private Customer customer;

    @Override
    public boolean equals(Object otherAppointment) {
        return super.equals(otherAppointment);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
