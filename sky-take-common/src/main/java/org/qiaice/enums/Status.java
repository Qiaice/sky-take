package org.qiaice.enums;

import lombok.Getter;

@Getter
public enum Status {
    DISABLED(0),
    ENABLED(1),
    ;

    private final Integer code;

    Status(Integer code) {
        this.code = code;
    }
}
