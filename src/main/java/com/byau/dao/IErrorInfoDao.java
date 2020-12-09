/*     */ package com.byau.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IErrorInfoDao {
  @Insert({"insert into errorinfo(info,name) values(#{info}, #{name})"})
  void insertErrorInfo(@Param("info") String paramString1, @Param("name") String paramString2);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\IErrorInfoDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */