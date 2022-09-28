package it.itwtech.ateauth.responses;

import java.util.Collections;
import java.util.List;

import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.validation.ValidationFailure;

public class ValidationFailureResponse extends ApiResponse{
	  public ValidationFailureResponse(List<ValidationFailure> validationErrors) {
	    super(RestApiResponseStatus.VALIDATION_FAILURE);
	    this.validationFailures = validationErrors;
	  }

	  public ValidationFailureResponse(String field, String code) {
	    super(RestApiResponseStatus.VALIDATION_FAILURE);
	    ValidationFailure validationFailure = new ValidationFailure(field, code);
	    this.validationFailures = Collections.singletonList(validationFailure);
	  }

	  private List<ValidationFailure> validationFailures;

	  public List<ValidationFailure> getValidationFailures() {
	    return validationFailures;
	  }

	  public void setValidationFailures(List<ValidationFailure> validationFailures) {
	    this.validationFailures = validationFailures;
	  }

	}
