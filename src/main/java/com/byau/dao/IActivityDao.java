/*     */ package com.byau.dao;

import com.byau.domain.Activity;
import com.byau.domain.Sign;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityDao {
  @Select({"select * from activity order by begintime desc"})
  List<Activity> findAll();


  @Select({"select * from activity where ano = #{ano}"})
  Activity findActivityByAno(@Param("ano") Integer paramInteger);
  
  @Insert({"insert into activity(aname,begintime,endtime,site,master,aim,ask) values(#{activity.aname},#{activity.begintime},#{activity.endtime},#{activity.site},#{activity.master},#{activity.aim},#{activity.ask})"})
  void addActivity(@Param("activity") Activity paramActivity);
  
  @Update({"update activity set aname=#{activity.aname},begintime=#{activity.begintime},endtime=#{activity.endtime},site=#{activity.site},master=#{activity.master},aim=#{activity.aim},ask=#{activity.ask} where ano = #{ano}"})
  void updateActivity(@Param("ano") Integer paramInteger, @Param("activity") Activity paramActivity);
  
  @Select({"select count(ano) from activity"})
  int findCount();
  
  @Select({"select * from activity where begintime<#{now} and endtime>#{now} order by begintime desc"})
  List<Activity> findDoing(@Param("now") String paramString);
  
  @Select({"select * from signactivity where ano = #{ano}"})
  List<Sign> findSignByAno(@Param("ano") Integer paramInteger);
  
  @Select({"select aname from activity where ano=#{ano}"})
  String findAnameByAno(@Param("ano") Integer paramInteger);
  
  @Delete({"delete from activity where ano = #{ano}"})
  void deleteByAno(@Param("ano") Integer paramInteger);
}
