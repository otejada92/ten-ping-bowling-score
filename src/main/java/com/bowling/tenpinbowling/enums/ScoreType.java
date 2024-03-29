package com.bowling.tenpinbowling.enums;

public enum ScoreType {

    NORMAL("normal"),
    STRIKE("strike"),
    SPARE("spare"),
    FOUL("foul"),
    NO_PINGS_KNOCKED("no pings knocked"),
    NONE("none");

    private final String value;

    ScoreType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
