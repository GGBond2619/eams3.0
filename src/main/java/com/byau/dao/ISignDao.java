/*     */ package com.byau.dao;

import com.byau.domain.Sign;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ISignDao {
  @Delete({"delete from signactivity where ano=#{ano} and sno=#{sno}"})
  void deleteByAnoSno(@Param("ano") Integer paramInteger, @Param("sno") String paramString);
  
  @Delete({"delete from signactivity where ano = #{ano}"})
  void deleteByAno(@Param("ano") Integer paramInteger);
  
  @Delete({"delete from signactivity where sno = #{sno}"})
  void deleteBySno(@Param("sno") String paramString);
  
  @Insert({"insert into signactivity(ano,sno) values(#{ano},#{sno})"})
  void insertSign(@Param("ano") Integer paramInteger, @Param("sno") String paramString);
  
  @Select({"select * from signactivity where sno = #{sno}"})
  List<Sign> findSignBySno(@Param("sno") String paramString);
  
  @Select({"select * from signactivity where ano = #{ano}"})
  List<Sign> findSignByAno(@Param("ano") Integer paramInteger);
  
  @Select({"select * from signactivity where sno = #{sno} and ano=#{ano}"})
  Sign findSignByAnoSno(@Param("ano") Integer paramInteger, @Param("sno") String paramString);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\ISignDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */