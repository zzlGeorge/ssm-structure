package com.vct.ssm.dao;

import com.vct.ssm.domain.UserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author mading
 * @email zhang0909990@qq.com
 * @date 2020-03-22 21:11:41
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
