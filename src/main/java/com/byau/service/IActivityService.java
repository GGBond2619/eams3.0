package com.byau.service;

import com.byau.domain.Activity;
import java.util.List;

public interface IActivityService {
  List<Activity> findAll();
  
  Activity findActivityByAno(Integer paramInteger);
  
  void updateActivity(Integer paramInteger, Activity paramActivity);
  
  void addActivity(Activity paramActivity);
  
  List<Activity> findDoing();
  
  int findCount();
  
  Activity findStudentsByAno(Integer paramInteger);
  
  void deleteByAno(Integer paramInteger);
}
