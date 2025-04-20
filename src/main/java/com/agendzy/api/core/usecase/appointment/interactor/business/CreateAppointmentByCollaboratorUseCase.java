    package com.agendzy.api.core.usecase.appointment.interactor.business;

import com.agendzy.api.core.domain.appointment.Appointment;
import com.agendzy.api.core.domain.appointment.AppointmentStatus;
import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.appointment.boundary.input.data.CreateAppointmentByCollaboratorInput;
import com.agendzy.api.core.usecase.appointment.boundary.output.data.AppointmentOutput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.validator.appointment.AppointmentAvailabilityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.errorBusinessRule;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class CreateAppointmentByCollaboratorUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final FindOneGateway<Professional, WhereBusinessIdAndEntityId> findProfessionalById;
    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceById;
    private final FindOneGateway<Customer, WhereId> findCustomerById;
    private final SaveGateway<Appointment> saveAppointment;
    private final AppointmentAvailabilityValidator availabilityValidator;

    public OutputResponse<AppointmentOutput> execute(String businessId, CreateAppointmentByCollaboratorInput input) {
        Business business = findBusiness(businessId);
        Professional professional = findProfessional(businessId, input.getProfessionalId());
        BusinessService service = findService(businessId, input.getServiceId());
        Customer customer = findCustomer(input);

        if (customer == null && (input.getCustomerName() == null || input.getCustomerPhone() == null)) {
            return errorBusinessRule("For anonymous appointments, customer name and phone must be provided.");
        }

        LocalDateTime endTime = calculateEndTime(input, service);

        if (!availabilityValidator.isAvailable(businessId, professional, input.getStartTime(), endTime)) {
            return errorBusinessRule("The selected time slot is not available for this professional.");
        }

        Appointment appointment = buildAppointment(input, business, professional, service, customer, endTime);

        Appointment savedAppointment = saveAppointment.execute(appointment).getData();

        return success(AppointmentOutput.of(savedAppointment));
    }

    private Business findBusiness(String businessId) {
        return findBusinessById.execute(new WhereId(businessId)).getData();
    }

    private Professional findProfessional(String businessId, String professionalId) {
        return findProfessionalById
            .execute(new WhereBusinessIdAndEntityId(businessId, professionalId))
            .getData();
    }

    private BusinessService findService(String businessId, String serviceId) {
        return findServiceById
            .execute(new WhereBusinessIdAndEntityId(businessId, serviceId))
            .getData();
    }

    private Customer findCustomer(CreateAppointmentByCollaboratorInput input) {
        if (input.getCustomerId() != null) {
            return findCustomerById.execute(new WhereId(input.getCustomerId())).getData();
        }

        return null;
    }

    private LocalDateTime calculateEndTime(CreateAppointmentByCollaboratorInput input, BusinessService service) {
        return input.getStartTime().plus(service.getDuration());
    }

    private Appointment buildAppointment(CreateAppointmentByCollaboratorInput input,
                                         Business business,
                                         Professional professional,
                                         BusinessService service,
                                         Customer customer,
                                         LocalDateTime endTime) {

        Appointment appointment = new Appointment();
        appointment.setCustomerName(input.getCustomerName());
        appointment.setCustomerPhone(input.getCustomerPhone());
        appointment.setStartTime(input.getStartTime());
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setNotes(input.getNotes());
        appointment.setCustomerNotes(input.getCustomerNotes());
        appointment.setWalkIn(true);
        appointment.setPreparationTimeInMin(0);
        appointment.setCleanupTimeInMin(0);
        appointment.setConfirmedByCustomer(false);
        appointment.setNotified(false);
        appointment.setBusiness(business);
        appointment.setProfessional(professional);
        appointment.setService(service);
        appointment.setCustomer(customer);
        return appointment;
    }

}
