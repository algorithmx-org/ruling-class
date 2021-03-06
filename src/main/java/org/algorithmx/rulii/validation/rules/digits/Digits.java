package org.algorithmx.rulii.validation.rules.digits;

import org.algorithmx.rulii.core.rule.Rule;
import org.algorithmx.rulii.core.rule.RuleBuilder;
import org.algorithmx.rulii.validation.BindingValidationRuleBuilder;
import org.algorithmx.rulii.validation.Severity;
import org.algorithmx.rulii.validation.annotation.ValidationRule;
import org.algorithmx.rulii.validation.annotation.ValidationRuleContainer;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Inherited
@Documented
@Repeatable(Digits.DigitsList.class)
@ValidationRule(Digits.DigitsValidationRuleBuilder.class)
public @interface Digits {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default DigitsValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    /**
     * @return maximum number of integral digits accepted for this number
     */
    int integer();

    /**
     * @return maximum number of fractional digits accepted for this number
     */
    int fraction();

    class DigitsValidationRuleBuilder implements BindingValidationRuleBuilder<Digits> {

        public DigitsValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(Digits digits, String bindingName) {
            DigitsValidationRule rule = new DigitsValidationRule(bindingName, digits.errorCode(),
                    digits.severity(), !NOT_APPLICABLE.equals(digits.message()) ? digits.message() : null,
                    digits.integer(), digits.fraction());
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }

    @Target({FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Inherited @Documented
    @ValidationRuleContainer(Digits.class)
    @interface DigitsList {
        Digits[] value();
    }
}
