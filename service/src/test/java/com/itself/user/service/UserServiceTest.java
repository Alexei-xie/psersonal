package com.itself.user.service;

import com.itself.ServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: duJi
 * @Date: 2024-05-21
 **/
@SpringBootTest(value = "com.itself.user",classes = ServiceApplication.class)
class UserServiceTest{

    @Autowired
    private UserService userService;
    @Test
    void queryPageData() {
        System.out.println(userService.listAll());
    }

    @Test
    void listAll() {
    }

    @Test
    void listPage() {
    }

    @Test
    void importData() {
    }

    @Test
    void importDataSheets() {
    }
}