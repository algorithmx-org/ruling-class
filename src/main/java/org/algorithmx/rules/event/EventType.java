package org.algorithmx.rules.event;

public enum EventType {

    RULE_START, RULE_PRE_CONDITION, RULE_GIVEN_CONDITION, RULE_ACTION, RULE_OTHERWISE_ACTION, RULE_END,
    RULE_SET_START, RULE_SET_PRE_CONDITION, RULE_SET_PRE_ACTION, RULE_SET_ERROR, RULE_SET_STOP_CONDITION,
    RULE_SET_POST_ACTION, RULE_SET_END;
}
