package com.youxiu326.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lihui on 2019/1/30.
 */
public class SearchEntity implements Serializable {

    private Long id;

    private Date createTime;

    public SearchEntity(){}
    public SearchEntity(Long id, Date createTime) {
        this.id = id;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
