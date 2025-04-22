package org.qiaice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Employee {

    @TableId(value = "id")
    private Long eid;

    @TableField(value = "username")
    private String uname;

    private String name;

    @TableField(value = "password")
    private String passwd;

    private String phone;

    @TableField(value = "sex")
    private String gender;

    private String idNumber;

    private Integer status;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
