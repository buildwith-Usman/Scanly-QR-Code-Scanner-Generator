package com.infinity.interactive.scanqr.generateqr.utility;

public final class ResultOfTypeAndValue {
    private final int type;
    private final String value;

    public ResultOfTypeAndValue(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
