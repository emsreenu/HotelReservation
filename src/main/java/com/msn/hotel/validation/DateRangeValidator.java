package com.msn.hotel.validation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.msn.hotel.controller.ReservationController;
import com.msn.hotel.model.ReservationRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ReservationRequest> {

	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    @Override
    public boolean isValid(ReservationRequest request, ConstraintValidatorContext context) {
    	log.info("validating daterange ");
    	if (request == null) return true;

        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();

        // If either date is null, leave null-checking to @NotNull
        if (start == null || end == null) return true;

        boolean valid = end.isAfter(start);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "End date must be after start date"
            ).addPropertyNode("endDate")
             .addConstraintViolation();
        }
        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        if (days > 30) valid=false;
        log.info("validaiton result:"+valid);
        return valid;
    }
}
