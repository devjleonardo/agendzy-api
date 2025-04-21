package com.agendzy.api.core.usecase.appointment.interactor.customer;

import com.agendzy.api.core.domain.appointment.Appointment;
import com.agendzy.api.core.domain.appointment.AppointmentStatus;
import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.domain.customer.auth.CustomerContextCurrent;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.appointment.boundary.input.data.CreateAppointmentByCustomerInput;
import com.agendzy.api.core.usecase.appointment.boundary.output.data.AppointmentOutput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.validator.appointment.AppointmentAvailabilityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.*;

@Service
@RequiredArgsConstructor
public class CreateAppointmentByCustomerUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final FindOneGateway<Customer, WhereId> findCustomerById;
    private final FindOneGateway<Professional, WhereBusinessIdAndEntityId> findProfessionalById;
    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceById;
    private final SaveGateway<Appointment> saveAppointment;
    private final AppointmentAvailabilityValidator availabilityValidator;

    public OutputResponse<AppointmentOutput> execute(CreateAppointmentByCustomerInput input) {
        String businessId = input.getBusinessId();

        Customer customer = findCustomer(CustomerContextCurrent.getCustomerId()).getData();
        Business business = findBusiness(businessId).getData();
        Professional professional = findProfessional(businessId, input.getProfessionalId());
        BusinessService service = findService(businessId, input.getServiceId());

        LocalDateTime endTime = input.getStartTime().plus(service.getDuration());

        if (!availabilityValidator.isAvailable(businessId, professional, input.getStartTime(), endTime)) {
            return errorBusinessRule("The selected time slot is not available for this professional.");
        }

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setStartTime(input.getStartTime());
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setNotes(input.getNotes());
        appointment.setCustomerNotes(input.getCustomerNotes());
        appointment.setWalkIn(false);
        appointment.setPreparationTimeInMin(0);
        appointment.setCleanupTimeInMin(0);
        appointment.setConfirmedByCustomer(true);
        appointment.setNotified(false);
        appointment.setBusiness(business);
        appointment.setProfessional(professional);
        appointment.setService(service);

        Appointment saved = saveAppointment.execute(appointment).getData();

        return success(AppointmentOutput.of(saved));
    }

    private OutputResponse<Business> findBusiness(String businessId) {
        var businessResponse =  findBusinessById.execute(new WhereId(businessId));

        if (businessResponse.isError()) {
            return error(businessResponse.getErrors());
        }

        return success(businessResponse.getData());
    }

    private OutputResponse<Customer> findCustomer(String businessId) {
        var customerResponse =  findCustomerById.execute(new WhereId(businessId));

        if (customerResponse.isError()) {
            return error(customerResponse.getErrors());
        }

        return success(customerResponse.getData());
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
}
