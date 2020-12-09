package com.byau.service.impl;

import com.byau.dao.ICompetitionDao;
import com.byau.dao.IEnjoyCompetitionDao;
import com.byau.domain.Competition;
import com.byau.service.ICompetitionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("competitionService")
public class CompetitionServiceImpl implements ICompetitionService {
    @Autowired
    ICompetitionDao competitionDao;

    @Autowired
    IEnjoyCompetitionDao enjoyCompetitionDao;

    public List<Competition> findCompetitionsByPage(int start, int perPageCompetitions) {
        List<Competition> competitions = this.competitionDao.findCompetitionsByPage(start, perPageCompetitions);
        System.out.println("CompetitionService findCompetitionsByPage");
        return competitions;
    }

    public List<Competition> findAll() {
        List<Competition> competitions = this.competitionDao.findAll();
        System.out.println("CompetitionService findAll");
        return competitions;
    }

    public Competition findCompetitionByCid(Integer cid) {
        System.out.println("CompetitionService findCompetitionByCid");
        return this.competitionDao.findCompetitionByCid(cid);
    }

    public List<Competition> findCompetitionByEle(String cname, String ctype) {
        cname = "%" + cname + "%";
        ctype = "%" + ctype + "%";
        System.out.println("CompetitionService findCompetitionByEle");
        return this.competitionDao.findCompetitionByEle(cname, ctype);
    }

    public List<Competition> findCompetitionByCname(String sname) {
        System.out.println("CompetitionService findCompetitionByCname");
        return this.competitionDao.findCompetitionByCname("%" + sname + "%");
    }

    public int findCount() {
        return this.competitionDao.findCount();
    }

    public void insertCompetition(Competition competition) {
        if (competition.getCid() != null) {
            System.out.println("CompetitionService insertCompetition cid = ''");
            return;
        }
        this.competitionDao.insertCompetition(competition);
        System.out.println("CompetitionService insertCompetition");
    }

    public void deleteCompetition(Integer cid) {
        this.competitionDao.deleteCompetition(cid);
        this.enjoyCompetitionDao.deleteEnjoyCompetitionByCid(cid);
        System.out.println("CompetitionService deleteCompetition");
    }

    public void updateCompetition(Integer cid, Competition competition) {
        this.competitionDao.updateCompetition(cid, competition);
        System.out.println("CompetitionService updateCompetition");
    }
}
