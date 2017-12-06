package com.fresh.web.service;

import com.fresh.web.annotation.BasicTypeCheck;
import com.fresh.web.annotation.StructureTypeCheck;
import com.fresh.web.model.Student;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by ljiajiali on 17-9-6.
 */
@Service
public class ProcessBusinessService {

    @StructureTypeCheck
    @BasicTypeCheck
    public void doWork(Student student, @NotBlank(message = "姓名不能为空") String a) {

    }

    @BasicTypeCheck
    public void doSpeak(@NotBlank(message = "姓名不能为空") String name, @Max(value = 120, message = "年龄不可超过120岁") int aget) {

    }

    public void doListen(List<Integer> ages) {}
}
