/*     */ package com.byau.dao;

import com.byau.domain.EnjoyCompetition;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnjoyCompetitionDao {
  @Select({"select * from enjoycompetition"})
  @Results({@Result(id = true, column = "eid", property = "eid"), @Result(column = "cid", property = "cname", one = @One(select = "com.byau.dao.ICompetitionDao.findCnameByCid")), @Result(column = "cid", property = "cid"), @Result(column = "sno", property = "sno"), @Result(column = "sno", property = "students", many = @Many(select = "com.byau.dao.IStudentDao.findStudentBySno"))})
  List<EnjoyCompetition> findAll();
  
  @Select({"select * from enjoycompetition where sno = #{sno} order by cid"})
  List<EnjoyCompetition> findEnjoyCompetitionBySno(@Param("sno")String paramString);
  
  @Select({"select * from enjoycompetition where cid = #{cid} order by sno"})
  List<EnjoyCompetition> findEnjoyCompetitionByCid(@Param("cid")Integer paramInteger);
  
  @Delete({"delete from enjoycompetition where cid=#{cid} and sno=#{sno}"})
  void deleteByCidSno(@Param("cid") Integer paramInteger, @Param("sno") String paramString);
  
  @Insert({"insert into enjoycompetition(cid,sno) values(#{cid},#{sno})"})
  void insertEnjoyCompetition(@Param("cid") Integer paramInteger, @Param("sno") String paramString);
  
  @Delete({"delete from enjoycompetition where sno = #{sno}"})
  void deleteEnjoyCompetitionBySno(@Param("sno") String paramString);
  
  @Delete({"delete from enjoycompetition where cid = #{cid}"})
  void deleteEnjoyCompetitionByCid(@Param("cid") Integer paramInteger);
  
  @Select({"select * from enjoycompetition where sno=#{sno} and cid=#{cid}"})
  EnjoyCompetition findBySnoCid(@Param("sno") String paramString, @Param("cid") Integer paramInteger);

  @Select({"select * from enjoycompetition where sno like #{sno} and cid in (select cid from competition where cname like #{cname})"})
  @Results({@Result(id = true, column = "eid", property = "eid"), @Result(column = "cid", property = "cname", one = @One(select = "com.byau.dao.ICompetitionDao.findCnameByCid")), @Result(column = "cid", property = "cid"), @Result(column = "sno", property = "sno"), @Result(column = "sno", property = "students", many = @Many(select = "com.byau.dao.IStudentDao.findStudentBySno"))})
  List<EnjoyCompetition> findByEle(@Param("sno") String sno,@Param("cname") String cname);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\IEnjoyCompetitionDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */