package com.vct.user.dao;

import com.vct.user.domain.UserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author mading
 * @email zhang0909990@qq.com
 * @date 2020-04-13 21:30:43
 */
@Mapper
public interface UserDao {

	UserDO get(Long id);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
