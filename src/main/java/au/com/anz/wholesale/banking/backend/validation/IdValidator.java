package au.com.anz.wholesale.banking.backend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Customer validator to input Ids.
 */
public class IdValidator implements ConstraintValidator<CustomValidator, Long> {

    /**
     * validate method.
     * @param value value.
     * @param context context.
     * @return boolean isValid.
     */
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        // TODO custom logic to validate the formats
        return true;
    }
}
