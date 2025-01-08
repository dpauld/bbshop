package group7.validation.annotation.validator;

import group7.entity.Bottle;
import group7.validation.annotation.ValidVolumeAlcoholic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VolumeAlcoholicValidator implements ConstraintValidator<ValidVolumeAlcoholic, Bottle> {

    @Override
    public boolean isValid(Bottle bottle, ConstraintValidatorContext context) {
        if (bottle == null) {
            return true; // Null object is not validated here
        }
        // If volumePercent > 0.0, isAlcoholic must be true
        if (bottle.getVolumePercent() > 0.0 && !bottle.isAlcoholic()) {
            return false;
        }
        return true;
    }
}
