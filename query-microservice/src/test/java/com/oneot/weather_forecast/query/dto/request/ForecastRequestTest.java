package com.oneot.weather_forecast.query.dto.request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.validation.*;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class ForecastRequestTest {

    private Validator validator;
    private final String place = "Tallinn";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the ConstraintValidatorFactory to provide PasswordValidator with desired constructor argument
        ConstraintValidatorFactory constraintValidatorFactory = mock(ConstraintValidatorFactory.class);

        when(constraintValidatorFactory.getInstance(NotBlankValidator.class))
                .thenReturn(new NotBlankValidator());

        try (ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .constraintValidatorFactory(constraintValidatorFactory)
                .buildValidatorFactory()) {

            validator = validatorFactory.getValidator();
        }
    }

    @Test
    public void testValidRequest() {
        // Create input values
        ForecastRequest request = new ForecastRequest(place);
        Set<ConstraintViolation<ForecastRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBlankRequestInput() {
        ForecastRequest request = new ForecastRequest(null);
        Set<ConstraintViolation<ForecastRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testJsonCreator() {
        // Create input values
        ForecastRequest request = ForecastRequest.create(place);

        // Assert that the instance is created successfully
        assertNotNull(request);
        assertEquals(place, request.place());
    }

}
