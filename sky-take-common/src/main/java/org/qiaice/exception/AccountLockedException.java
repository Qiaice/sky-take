package org.qiaice.exception;

public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String message) {
        super(message);
    }
}
