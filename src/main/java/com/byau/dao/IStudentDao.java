/*     */ package com.byau.dao;

import com.byau.domain.Student;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentDao {
  @Select({"select * from student order by sno"})
  List<Student> findAll();
  
  @Select({"select * from student order by sno limit #{start},#{perPageStudents}"})
  List<Student> findStudentsByPage(@Param("start") int paramInt1, @Param("perPageStudents") int paramInt2);
  
  @Select({"select * from student where sno = #{sno}"})
  Student findStudentBySno(@Param("sno")String paramString);
  
  @Select({"select * from student where sno like #{sno} and sname like #{sname} and sclass like #{sclass} order by sno"})
  List<Student> findStudentByEle(@Param("sno") String paramString1, @Param("sname") String paramString2, @Param("sclass") String paramString3);
  
  @Select({"select * from student where sno like #{sno} limit #{start},#{perPageStudents} order by sno"})
  List<Student> findStudentLikeSno(@Param("sno") String paramString, @Param("start") int paramInt1, @Param("perPageStudents") int paramInt2);
  
  @Select({"select * from student where sname like #{sname} order by sno"})
  List<Student> findStudentBySname(@Param("sname") String paramString);
  
  @Select({"select count(sno) from student"})
  int findCount();
  
  @Select({"select sname from student where sno = #{sno}"})
  String findSnameBySno(@Param("sno") String paramString);
  
  @Insert({"insert into student(sno,sname,stel,ssex,sqq,syear,sclass) values(#{student.sno},#{student.sname},#{student.stel},#{student.ssex},#{student.sqq},#{student.syear},#{student.sclass})"})
  void insertStudent(@Param("student") Student paramStudent);
  
  @Delete({"delete from student where sno = #{sno}"})
  void deleteStudent(@Param("sno")String paramString);
  
  @Update({"update student set sname=#{student.sname},sclass=#{student.sclass},ssex=#{student.ssex},syear=#{student.syear},sqq=#{student.sqq},stel=#{student.stel} where sno = #{sno}"})
  void updateStudent(@Param("sno") String paramString, @Param("student") Student paramStudent);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\IStudentDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */