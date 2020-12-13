package com.byau.service;

import com.byau.domain.Competition;
import com.byau.domain.EnjoyCompetition;
import com.byau.domain.Student;

import java.util.List;

public interface IEnjoyCompetitionService {
    List<EnjoyCompetition> findAll();

    void deleteByCidSno(Integer paramInteger, String paramString);

    void insertEnjoyCompetition(Integer paramInteger, String paramString);

    Student findCompetitionsBySno(String paramString);

    Competition findStudentByCid(Integer paramInteger);

    List<EnjoyCompetition> findByEle(String sno, String cname);
}
