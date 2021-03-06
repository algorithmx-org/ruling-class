package org.algorithmx.rulii.validation.rules.size;

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
@Repeatable(Size.SizeList.class)
@ValidationRule(Size.SizeValidationRuleBuilder.class)
public @interface Size {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default SizeValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    class SizeValidationRuleBuilder implements BindingValidationRuleBuilder<Size> {

        public SizeValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(Size size, String bindingName) {
            SizeValidationRule rule = new SizeValidationRule(bindingName, size.errorCode(), size.severity(),
                    !NOT_APPLICABLE.equals(size.message()) ? size.message() : null,
                    size.min(), size.max());
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }

    @Target({FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Inherited @Documented
    @ValidationRuleContainer(Size.class)
    @interface SizeList {
        Size[] value();
    }
}
