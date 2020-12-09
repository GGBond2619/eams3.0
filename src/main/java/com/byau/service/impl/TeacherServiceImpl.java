package com.byau.service.impl;

import com.byau.dao.ITeacherDao;
import com.byau.domain.Teacher;
import com.byau.service.ITeacherService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("teacherService")
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    ITeacherDao teacherDao;

    public List<Teacher> findTeachersByPage(int start, int perPageTeachers) {
        List<Teacher> teachers = this.teacherDao.findTeachersByPage(start, perPageTeachers);
        System.out.println("TeacherService findTeachersByPage");
        return teachers;
    }

    public List<Teacher> findAll() {
        List<Teacher> teachers = this.teacherDao.findAll();
        System.out.println("TeacherService findAll");
        return teachers;
    }

    public Teacher findTeacherByTno(String tno) {
        System.out.println("TeacherService findTeacherByTno");
        return this.teacherDao.findTeacherByTno(tno);
    }

    public List<Teacher> findTeacherByEle(String tno, String tname, String tpos) {
        tno = "%" + tno + "%";
        tname = "%" + tname + "%";
        tpos = "%" + tpos + "%";
        System.out.println("TeacherService findTeacherByEle");
        return this.teacherDao.findTeacherByEle(tno, tname, tpos);
    }

    public List<Teacher> findTeacherLikeTno(String tno, int start, int perPageTeachers) {
        tno = "%" + tno + "%";
        System.out.println("TeacherService findTeacherLikeTno");
        return this.teacherDao.findTeacherLikeTno(tno, start, perPageTeachers);
    }

    public List<Teacher> findTeacherByTname(String tname) {
        System.out.println("TeacherService findTeacherByTname");
        return this.teacherDao.findTeacherByTname("%" + tname + "%");
    }

    public int findCount() {
        return this.teacherDao.findCount();
    }

    public void insertTeacher(Teacher teacher) {
        if (teacher.getTno() == "") {
            System.out.println("TeacherService insertTeacher tno = ''");
            return;
        }
        this.teacherDao.insertTeacher(teacher);
        System.out.println("TeacherService insertTeacher");
    }

    public void deleteTeacher(String tno) {
        this.teacherDao.deleteTeacher(tno);
        System.out.println("TeacherService deleteTeacher:" + tno);
    }

    public void updateTeacher(String tno, Teacher teacher) {
        this.teacherDao.updateTeacher(tno, teacher);
        System.out.println("TeacherService updateTeacher");
    }
}
