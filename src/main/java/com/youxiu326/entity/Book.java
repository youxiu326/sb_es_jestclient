package com.youxiu326.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lihui on 2019/1/30.
 */
public class Book extends SearchEntity {

    /**
     * 书籍编号
     */
    private String name;

    /**
     * 书籍编号
     */
    private String code;

    /**
     * 书籍价格
     */
    private float price;

    /**
     * 书籍自动上架时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoOnLineDate;

    public Book(){}

    /**
     * 构造函数
     * @param id
     * @param createTime
     * @param code
     * @param name
     * @param price 价格
     * @param autoOnLineDate 自动上架时间
     */
    public Book(Long id, Date createTime, String code, String name, float price,Date autoOnLineDate) {
        super(id,createTime);
        this.name = name;
        this.price = price;
        this.code = code;
        this.autoOnLineDate = autoOnLineDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getAutoOnLineDate() {
        return autoOnLineDate;
    }

    public void setAutoOnLineDate(Date autoOnLineDate) {
        this.autoOnLineDate = autoOnLineDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }
}
