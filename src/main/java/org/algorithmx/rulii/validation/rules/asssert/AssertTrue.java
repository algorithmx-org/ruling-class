package org.algorithmx.rulii.validation.rules.asssert;

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
@ValidationRule(AssertTrue.AssertTrueValidationRuleBuilder.class)
public @interface AssertTrue {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default AssertTrueValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    class AssertTrueValidationRuleBuilder implements BindingValidationRuleBuilder<AssertTrue> {

        public AssertTrueValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(AssertTrue assertTrue, String bindingName) {
            AssertTrueValidationRule rule = new AssertTrueValidationRule(bindingName, assertTrue.errorCode(),
                    assertTrue.severity(), !NOT_APPLICABLE.equals(assertTrue.message()) ? assertTrue.message() : null);
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }
}
