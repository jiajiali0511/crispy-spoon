package com.fresh.web.service;

import com.fresh.web.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.sql.Struct;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProcessBusinessServiceTest {

    @Resource
    private ProcessBusinessService processBusinessService;

    @Test
    public void testDoWork() throws Exception {
        Student student = new Student();
        student.setAge(130);
        student.setName("");
        processBusinessService.doWork(student);
    }
}