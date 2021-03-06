package org.algorithmx.rulii.validation.rules.negative;

import org.algorithmx.rulii.core.rule.Rule;
import org.algorithmx.rulii.core.rule.RuleBuilder;
import org.algorithmx.rulii.validation.BindingValidationRuleBuilder;
import org.algorithmx.rulii.validation.Severity;
import org.algorithmx.rulii.validation.annotation.ValidationRule;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
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
@Inherited @Documented
@ValidationRule(NegativeOrZero.NegativeOrZeroValidationRuleBuilder.class)
public @interface NegativeOrZero {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default NegativeOrZeroValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    class NegativeOrZeroValidationRuleBuilder implements BindingValidationRuleBuilder<NegativeOrZero> {

        public NegativeOrZeroValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(NegativeOrZero negativeOrZero, String bindingName) {
            NegativeOrZeroValidationRule rule = new NegativeOrZeroValidationRule(bindingName, negativeOrZero.errorCode(),
                    negativeOrZero.severity(),
                    !NOT_APPLICABLE.equals(negativeOrZero.message()) ? negativeOrZero.message() : null);
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }
}
