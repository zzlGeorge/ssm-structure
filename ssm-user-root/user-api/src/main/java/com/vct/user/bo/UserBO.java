package com.vct.user.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * description: UserBO <br>
 * date: 2020/4/13 21:19 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class UserBO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private String userName;
    //
    private Integer userAge;
    //
    private String userEmail;
    //
    private Date createTime;
    //
    private Date modifyTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}
