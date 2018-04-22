package com.xxx.homework;

import com.xxx.homework.db.SqlPoolFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestGetConnection {

    @Resource
    private SqlPoolFactory sqlPoolFactory;

    @Test
    public void testGetConnection() {
        System.out.println(">>>> " + sqlPoolFactory.take());
    }
}
