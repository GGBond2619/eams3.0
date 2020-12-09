package com.byau.service;
import com.byau.domain.Student;
import java.util.List;

public interface IStudentService {
  List<Student> findAll();
  
  List<Student> findStudentsByPage(int paramInt1, int paramInt2);
  
  Student findStudentBySno(String paramString);
  
  List<Student> findStudentByEle(String paramString1, String paramString2, String paramString3);
  
  List<Student> findStudentLikeSno(String paramString, int paramInt1, int paramInt2);
  
  List<Student> findStudentBySname(String paramString);
  
  Student findActivityBySno(String paramString);
  
  int findCount();
  
  void insertStudent(Student paramStudent);
  
  void deleteStudent(String paramString);
  
  void updateStudent(String paramString, Student paramStudent);
}
