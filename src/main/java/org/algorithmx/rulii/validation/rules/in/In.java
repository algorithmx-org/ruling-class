package org.algorithmx.rulii.validation.rules.in;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
@Repeatable(In.InList.class)
@ValidationRule(In.InValidationRuleBuilder.class)
public @interface In {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default InValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    String[] values();

    Class<?> type() default void.class;

    class InValidationRuleBuilder implements BindingValidationRuleBuilder<In> {

        public InValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(In in, String bindingName) {
            // TODO : Pass in ConverterRegistry and convert string to desired type.
            Set<String> values = new HashSet<>(Arrays.asList(in.values()));
            InValidationRule rule = new InValidationRule(bindingName, in.errorCode(),
                    in.severity(), !NOT_APPLICABLE.equals(in.message()) ? in.message() : null, values);
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }

    @Target({FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Inherited @Documented
    @ValidationRuleContainer(In.class)
    @interface InList {
        In[] value();
    }
}
