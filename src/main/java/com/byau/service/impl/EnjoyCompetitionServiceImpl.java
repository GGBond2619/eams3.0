package com.byau.service.impl;

import com.byau.dao.ICompetitionDao;
import com.byau.dao.IEnjoyCompetitionDao;
import com.byau.dao.IStudentDao;
import com.byau.domain.Competition;
import com.byau.domain.EnjoyCompetition;
import com.byau.domain.Student;
import com.byau.service.IEnjoyCompetitionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("enjoyCompetitionService")
public class EnjoyCompetitionServiceImpl implements IEnjoyCompetitionService {
    @Autowired
    IEnjoyCompetitionDao enjoyCompetitionDao;

    @Autowired
    IStudentDao studentDao;

    @Autowired
    ICompetitionDao competitionDao;

    public List<EnjoyCompetition> findAll() {
        return this.enjoyCompetitionDao.findAll();
    }

    public void deleteByCidSno(Integer cid, String sno) {
        this.enjoyCompetitionDao.deleteByCidSno(cid, sno);
    }

    public void insertEnjoyCompetition(Integer cid, String sno) {
        if (this.enjoyCompetitionDao.findBySnoCid(sno, cid) == null)
            this.enjoyCompetitionDao.insertEnjoyCompetition(cid, sno);
    }

    public Student findCompetitionsBySno(String sno) {
        List<EnjoyCompetition> enjoyCompetitions = this.enjoyCompetitionDao.findEnjoyCompetitionBySno(sno);
        if (enjoyCompetitions == null)
            return null;
        List<Integer> cids = new ArrayList<>();
        for (EnjoyCompetition enjoyCompetition : enjoyCompetitions)
            cids.add(enjoyCompetition.getCid());
        Student student = new Student();
        student.setSno(sno);
        student.setSname(this.studentDao.findSnameBySno(sno));
        student.setCompetitions(new ArrayList());
        for (Integer i : cids)
            student.getCompetitions().add(this.competitionDao.findCompetitionByCid(i));
        return student;
    }

    public Competition findStudentByCid(Integer cid) {
        List<EnjoyCompetition> enjoyCompetitions = this.enjoyCompetitionDao.findEnjoyCompetitionByCid(cid);
        if (enjoyCompetitions == null)
            return null;
        List<String> snos = new ArrayList<>();
        for (EnjoyCompetition enjoyCompetition : enjoyCompetitions) {
            snos.add(enjoyCompetition.getSno());
            System.out.println(enjoyCompetition.getSno() + "=======");
        }
        Competition competition = new Competition();
        competition.setCid(cid);
        competition.setCname(this.competitionDao.findCnameByCid(cid));
        competition.setStudents(new ArrayList());
        for (String sno : snos)
            competition.getStudents().add(this.studentDao.findStudentBySno(sno));
        System.out.println(competition.getStudents());
        return competition;
    }
}
