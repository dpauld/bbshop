package group7.validation.annotation;

import jakarta.validation.Payload;

public @interface ValidVolumeAlcoholic {
    String message() default "If volumePercent > 0.0, isAlcoholic must be true";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
