package org.algorithmx.rulii.validation.rules.past;

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
@ValidationRule(PastOrPresent.PastOrPresentValidationRuleBuilder.class)
public @interface PastOrPresent {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default PastOrPresentValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    class PastOrPresentValidationRuleBuilder implements BindingValidationRuleBuilder<PastOrPresent> {

        public PastOrPresentValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(PastOrPresent pastOrPresent, String bindingName) {
            PastOrPresentValidationRule rule = new PastOrPresentValidationRule(bindingName, pastOrPresent.errorCode(),
                    pastOrPresent.severity(),
                    !NOT_APPLICABLE.equals(pastOrPresent.message()) ? pastOrPresent.message() : null);
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }
}
