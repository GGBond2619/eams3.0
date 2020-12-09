package com.byau.service;
import com.byau.domain.Teacher;
import java.util.List;

public interface ITeacherService {
  List<Teacher> findAll();
  
  List<Teacher> findTeachersByPage(int paramInt1, int paramInt2);
  
  Teacher findTeacherByTno(String paramString);
  
  List<Teacher> findTeacherByEle(String paramString1, String paramString2, String paramString3);
  
  List<Teacher> findTeacherLikeTno(String paramString, int paramInt1, int paramInt2);
  
  List<Teacher> findTeacherByTname(String paramString);
  
  int findCount();
  
  void insertTeacher(Teacher paramTeacher);
  
  void deleteTeacher(String paramString);
  
  void updateTeacher(String paramString, Teacher paramTeacher);
}
