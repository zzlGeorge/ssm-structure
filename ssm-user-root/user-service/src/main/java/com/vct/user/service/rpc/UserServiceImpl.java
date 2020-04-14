package com.vct.user.service.rpc;

import com.vct.user.bo.UserBO;
import com.vct.user.dao.UserDao;
import com.vct.user.domain.UserDO;
import com.vct.user.service.api.IUserService;
import org.apache.log4j.Logger;
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

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public UserBO getUserById(Long userId) {
        UserDO userDO = userDao.get(userId);
        UserBO userBO = null;
        logger.error("getUserById={}");
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
