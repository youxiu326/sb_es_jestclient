package com.youxiu326;

import com.youxiu326.entity.Book;
import com.youxiu326.exection.JestExcetion;
import com.youxiu326.service.JestClientService;
import io.searchbox.client.JestResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbEsJestclientApplicationTests {


    @Autowired
    private JestClientService jestClientService;

    @Test
    public void testIndex() throws JestExcetion, ParseException {

        Book book1 = new Book(
                1L,
                new Date(),
                "001",
                "悲惨世界",
                20.1F,
                new Date());


        Book book2 = new Book(
                2L,
                new Date(),
                "002",
                "动物世界与悲惨世界",
                40.1F,
                new Date());

        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date date1 = sbf.parse("2018-06-06 12:01:01");

        Book book3 = new Book(
                3L,
                new Date(),
                "003",
                "大头儿子与小头爸爸",
                48.1F,
                date1);


        jestClientService.index(book1, book1.getId().toString());
        jestClientService.index(book2, book2.getId().toString());
        JestResult index = jestClientService.index(book3, book3.getId().toString());
        if (index.isSucceeded()){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
    }

    @Test
    public void testQuery(){

    }

}
