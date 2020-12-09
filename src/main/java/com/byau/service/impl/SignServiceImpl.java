package com.byau.service.impl;

import com.byau.dao.ISignDao;
import com.byau.service.ISignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl implements ISignService {
    @Autowired
    ISignDao signDao;

    public void deleteByAnoSno(Integer ano, String sno) {
        this.signDao.deleteByAnoSno(ano, sno);
    }

    public void insertSign(Integer ano, String sno) {
        if (this.signDao.findSignByAnoSno(ano, sno) == null)
            this.signDao.insertSign(ano, sno);
    }
}