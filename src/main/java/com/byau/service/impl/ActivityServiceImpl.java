package com.byau.service.impl;

import com.byau.dao.IActivityDao;
import com.byau.dao.IStudentDao;
import com.byau.domain.Activity;
import com.byau.domain.Sign;
import com.byau.service.IActivityService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("activityService")
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    IActivityDao activityDao;

    @Autowired
    IStudentDao studentDao;

    public List<Activity> findAll() {
        return this.activityDao.findAll();
    }

    public Activity findActivityByAno(Integer ano) {
        return this.activityDao.findActivityByAno(ano);
    }

    public void updateActivity(Integer ano, Activity activity) {
        this.activityDao.updateActivity(ano, activity);
    }

    public void addActivity(Activity activity) {
        this.activityDao.addActivity(activity);
    }

    public List<Activity> findDoing() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dltime = dateFormat.format(now);
        System.out.println(dltime);
        return this.activityDao.findDoing(dltime);
    }

    public int findCount() {
        return this.activityDao.findCount();
    }

    public Activity findStudentsByAno(Integer ano) {
        List<Sign> signs = this.activityDao.findSignByAno(ano);
        List<String> snos = new ArrayList<>();
        for (Sign sign : signs)
            snos.add(sign.getSno());
        Activity activity = new Activity();
        activity.setAno(ano);
        activity.setAname(this.activityDao.findAnameByAno(ano));
        activity.setStudents(new ArrayList());
        for (String sno : snos)
            activity.getStudents().add(this.studentDao.findStudentBySno(sno));
        return activity;
    }

    public void deleteByAno(Integer ano) {
        this.activityDao.deleteByAno(ano);
    }
}
