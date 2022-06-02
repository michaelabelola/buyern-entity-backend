package com.buyern.buyern.Enums;

public class State {
    public enum Asset{
        ONLINE, OFFLINE, TRACKING
    }
    public enum Entity{
        OPEN, CLOSE, IN_REVIEW, INACTIVE
    }
    public enum Order{
        PLACED, PROCESSING, COMPLETED, ERROR, CANCELLED
    }
}
