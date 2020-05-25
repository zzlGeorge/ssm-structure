package com.vct.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vct.user.bo.UserBO;
import com.vct.user.dao.UserDao;
import com.vct.user.domain.UserDO;
import com.vct.user.service.api.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: UserServiceImpl <br>
 * date: 2020/4/13 21:37 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */

@Service
public class UserServiceImpl implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public UserBO getUserById(Long userId) {
        UserDO userDO = userDao.get(userId);
        UserBO userBO = null;
        logger.info("getUserById={},结果={}", userId, JSON.toJSONString(userDO));
        if(userDO!=null){
            userBO = new UserBO();
            userBO.setCreateTime(userDO.getCreateTime());
            userBO.setId(userDO.getId());
            userBO.setModifyTime(userDO.getModifyTime());
            userBO.setUserAge(userDO.getUserAge());
            userBO.setUserEmail(userDO.getUserEmail());
            userBO.setUserName(userDO.getUserName());
        }
        return userBO;
    }

}
