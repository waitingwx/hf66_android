package com.cqxy.constants;

/**
 * Created by wx on 2017/11/8.
 */

public class IsRentEvent {
    private boolean isRent;

    public IsRentEvent(boolean isRent) {
        this.isRent = isRent;
    }

    public boolean isRent() {
        return isRent;
    }
}
