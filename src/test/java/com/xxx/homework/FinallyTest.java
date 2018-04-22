package com.xxx.homework;

import com.xxx.homework.controller.HandleFileController;
import com.xxx.homework.util.Asserts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class FinallyTest {

    @Resource
    private HandleFileController handleFile;

    @Test
    public void testFinally() {
        String pathname = "/DT/idea/homework/src/main/resources";
        boolean result = handleFile.readAndInsertDatabase(pathname);
        Asserts.assertTrue(result, () -> new RuntimeException("execute fail"));
        System.out.println("read file and insert success");
    }
}
