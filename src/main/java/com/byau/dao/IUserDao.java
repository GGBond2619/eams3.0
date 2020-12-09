package com.byau.dao;

import com.byau.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {
  @Select({"select * from user where username=#{username}"})
  User findUser(@Param("username") String paramString);
  
  @Select({"select * from user where username=#{user.username} and password=#{user.password}"})
  User loginIn(@Param("user") User paramUser);
}
