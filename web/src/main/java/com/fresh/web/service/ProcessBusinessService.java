package com.fresh.web.service;

import com.fresh.web.annotation.ParamCheck;
import com.fresh.web.model.Student;
import org.springframework.stereotype.Service;

/**
 * Created by ljiajiali on 17-9-6.
 */
@Service
public class ProcessBusinessService {
    @ParamCheck
    public void doWork(Student student) {

    }
}
