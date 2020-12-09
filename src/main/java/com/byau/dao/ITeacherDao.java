/*     */ package com.byau.dao;

import com.byau.domain.Teacher;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeacherDao {
  @Select({"select * from teacher ORDER BY CAST(tno AS DECIMAL(10))"})
  List<Teacher> findAll();
  
  @Select({"select * from teacher ORDER BY CAST(tno AS DECIMAL) limit #{start},#{perPageTeachers}"})
  List<Teacher> findTeachersByPage(@Param("start") int paramInt1, @Param("perPageTeachers") int paramInt2);
  
  @Select({"select * from teacher where tno = #{tno}"})
  Teacher findTeacherByTno(@Param("tno")String paramString);
  
  @Select({"select * from teacher where tno like #{tno} and tname like #{tname} and tpos like #{tpos} ORDER BY CAST(tno AS DECIMAL(10))"})
  List<Teacher> findTeacherByEle(@Param("tno") String paramString1, @Param("tname") String paramString2, @Param("tpos") String paramString3);
  
  @Select({"select * from teacher where tno like #{tno} limit #{start},#{perPageTeachers} ORDER BY CAST(tno AS DECIMAL(10))"})
  List<Teacher> findTeacherLikeTno(@Param("tno") String paramString, @Param("start") int paramInt1, @Param("perPageTeachers") int paramInt2);
  
  @Select({"select * from teacher where tname like #{tname} ORDER BY CAST(tno AS DECIMAL(10))"})
  List<Teacher> findTeacherByTname(@Param("tname")String paramString);
  
  @Select({"select count(tno) from teacher"})
  int findCount();
  
  @Insert({"insert into teacher(tno,tname,tpos,ttel,tqq) values(#{teacher.tno},#{teacher.tname},#{teacher.tpos},#{teacher.ttel},#{teacher.tqq})"})
  void insertTeacher(@Param("teacher") Teacher paramTeacher);
  
  @Delete({"delete from teacher where tno = #{tno}"})
  void deleteTeacher(@Param("tno")String paramString);
  
  @Update({"update teacher set tname=#{teacher.tname},tpos=#{teacher.tpos},tqq=#{teacher.tqq},ttel=#{teacher.ttel} where tno = #{tno}"})
  void updateTeacher(@Param("tno") String paramString, @Param("teacher") Teacher paramTeacher);
}

