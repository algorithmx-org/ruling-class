package org.algorithmx.rulii.validation.rules.pattern;

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
@Repeatable(Pattern.PatternList.class)
@ValidationRule(Pattern.PatternValidationRuleBuilder.class)
public @interface Pattern {

    String NOT_APPLICABLE = "N/A";

    String errorCode() default PatternValidationRule.ERROR_CODE;

    String message() default NOT_APPLICABLE;

    Severity severity() default Severity.ERROR;

    /**
     * @return the regular expression to match
     */
    String regex();

    boolean caseSensitive() default true;

    class PatternValidationRuleBuilder implements BindingValidationRuleBuilder<Pattern> {

        public PatternValidationRuleBuilder() {
            super();
        }

        @Override
        public Rule[] build(Pattern pattern, String bindingName) {
            PatternValidationRule rule = new PatternValidationRule(bindingName, pattern.errorCode(), pattern.severity(),
                    !NOT_APPLICABLE.equals(pattern.message()) ? pattern.message() : null,
                    pattern.caseSensitive(), pattern.regex());
            Rule[] result = {RuleBuilder.build(rule)};
            return result;
        }
    }

    @Target({FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Inherited @Documented
    @ValidationRuleContainer(Pattern.class)
    @interface PatternList {
        Pattern[] value();
    }
}
