package com.vct.user.service.api;

import com.vct.user.bo.UserBO;

/**
 * description: IUserService <br>
 * date: 2020/4/13 21:18 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public interface IUserService {
    UserBO getUserById(Long userId);
}
