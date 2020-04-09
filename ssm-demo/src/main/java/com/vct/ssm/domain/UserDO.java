package com.vct.ssm.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @author mading
 * @email zhang0909990@qq.com
 * @date 2020-03-22 21:11:41
 */
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private String userName;
    //
    private String email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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
