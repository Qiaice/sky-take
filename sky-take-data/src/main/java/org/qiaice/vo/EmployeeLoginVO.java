package org.qiaice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeLoginVO {

    private Long eid;

    private String uname;

    private String name;

    private String token;
}
