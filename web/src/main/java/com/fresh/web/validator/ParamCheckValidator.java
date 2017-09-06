package com.fresh.web.validator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by ljiajiali on 17-9-6.
 */
public class ParamCheckValidator {
    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public static Validator getInstance() {
        return validator;
    }
}
