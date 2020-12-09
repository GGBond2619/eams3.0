/*     */ package com.byau.dao;

import com.byau.domain.Competition;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompetitionDao {
  @Select({"select * from competition order by cid"})
  List<Competition> findAll();
  
  @Select({"select * from competition order by cid limit #{start},#{perPageCompetitions}"})
  List<Competition> findCompetitionsByPage(@Param("start") int paramInt1, @Param("perPageCompetitions") int paramInt2);
  
  @Select({"select * from competition where cid = #{cid}"})
  Competition findCompetitionByCid(@Param("cid") Integer paramInteger);
  
  @Select({"select * from competition where cname like #{cname} and ctype like #{ctype} order by cid"})
  List<Competition> findCompetitionByEle(@Param("cname") String paramString1, @Param("ctype") String paramString2);
  
  @Select({"select * from competition where cid like #{cid} limit #{start},#{perPageCompetitions} order by cid"})
  List<Competition> findCompetitionLikeCid(@Param("cid") Integer paramInteger, @Param("start") int paramInt1, @Param("perPageCompetitions") int paramInt2);
  
  @Select({"select * from competition where cname like #{cname} order by cid"})
  List<Competition> findCompetitionByCname(@Param("cname") String paramString);
  
  @Select({"select count(cid) from competition"})
  int findCount();
  
  @Select({"select cname from competition where cid = #{cid}"})
  String findCnameByCid(@Param("cid") Integer paramInteger);
  
  @Insert({"insert into competition(cname,cplace,ctype,cbegintime) values(#{competition.cname},#{competition.cplace},#{competition.ctype},#{competition.cbegintime})"})
  void insertCompetition(@Param("competition") Competition paramCompetition);
  
  @Delete({"delete from competition where cid = #{cid}"})
  void deleteCompetition(@Param("cid") Integer paramInteger);
  
  @Update({"update competition set cname=#{competition.cname},ctype=#{competition.ctype},cbegintime=#{competition.cbegintime},cplace=#{competition.cplace} where cid = #{cid}"})
  void updateCompetition(@Param("cid") Integer paramInteger, @Param("competition") Competition paramCompetition);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\ICompetitionDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */