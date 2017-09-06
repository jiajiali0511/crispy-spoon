package com.fresh.web.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Created by ljiajiali on 17-9-6.
 */
@Data
public class Student {
    @Max(value = 120, message = "年龄不可超过120")
    private int age;
    @NotBlank(message = "学生姓名不为空")
    private String name;
}
