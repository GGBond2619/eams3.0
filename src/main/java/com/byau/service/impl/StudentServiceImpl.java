package com.byau.service.impl;

import com.byau.dao.IActivityDao;
import com.byau.dao.IEnjoyCompetitionDao;
import com.byau.dao.ISignDao;
import com.byau.dao.IStudentDao;
import com.byau.domain.Activity;
import com.byau.domain.Sign;
import com.byau.domain.Student;
import com.byau.service.IStudentService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements IStudentService {
    @Autowired
    IStudentDao studentDao;

    @Autowired
    IEnjoyCompetitionDao enjoyCompetitionDao;

    @Autowired
    ISignDao signDao;

    @Autowired
    IActivityDao activityDao;

    public List<Student> findStudentsByPage(int start, int perPageStudents) {
        
        List<Student> students = this.studentDao.findStudentsByPage(start, perPageStudents);
        
        System.out.println("StudentServiceImpl findStudentsByPage");
        
        return students;
    }

    public List<Student> findAll() {
        
        List<Student> students = this.studentDao.findAll();
        
        System.out.println("StudentServiceImpl findAll");
        
        return students;
    }

    public Student findStudentBySno(String sno) {
        
        System.out.println("StudentServiceImpl findStudentBySno");
        
        return this.studentDao.findStudentBySno(sno);
    }

    public List<Student> findStudentByEle(String sno, String sname, String sclass) {
        
        sno = "%" + sno + "%";
        
        sname = "%" + sname + "%";
        
        sclass = "%" + sclass + "%";
        
        System.out.println("StudentServiceImpl findStudentByEle");
        
        return this.studentDao.findStudentByEle(sno, sname, sclass);
    }

    public List<Student> findStudentLikeSno(String sno, int start, int perPageStudents) {
        
        sno = "%" + sno + "%";
        
        System.out.println("StudentServiceImpl findStudentLikeSno");
        
        return this.studentDao.findStudentLikeSno(sno, start, perPageStudents);
    }

    public List<Student> findStudentBySname(String sname) {
        
        System.out.println("StudentServiceImpl findStudentBySname");
        
        return this.studentDao.findStudentBySname("%" + sname + "%");
    }

    public Student findActivityBySno(String sno) {
        
        List<Sign> signs = this.signDao.findSignBySno(sno);
        
        List<Integer> anos = new ArrayList<>();
        
        for (Sign sign : signs)
            
            anos.add(sign.getAno());
        
        Student student = new Student();
        
        student.setSno(sno);
        
        student.setSname(this.studentDao.findSnameBySno(sno));
        
        List<Activity> activities = new ArrayList<>();
        
        for (Integer i : anos)
            
            activities.add(this.activityDao.findActivityByAno(i));
        
        student.setActivities(activities);
        
        return student;
    }

    public int findCount() {
        
        return this.studentDao.findCount();
    }

    public void insertStudent(Student student) {
        
        if (student.getSno() == "") {
            
            System.out.println("StudentServiceImpl insertStudent sno = ''");
            return;
        }
        
        this.studentDao.insertStudent(student);
        
        System.out.println("StudentServiceImpl insertStudent");
    }

    public void deleteStudent(String sno) {
        
        this.studentDao.deleteStudent(sno);
        
        this.enjoyCompetitionDao.deleteEnjoyCompetitionBySno(sno);
        
        this.signDao.deleteBySno(sno);
        
        System.out.println("StudentServiceImpl deleteStudent");
    }

    public void updateStudent(String sno, Student student) {
        
        this.studentDao.updateStudent(sno, student);
        
        System.out.println("StudentServiceImpl updateStudent");
    }
}
