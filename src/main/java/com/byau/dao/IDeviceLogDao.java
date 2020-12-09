/*     */ package com.byau.dao;

import com.byau.domain.DeviceLog;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceLogDao {
  @Select({"select devicelog.*,dname from devicelog,device where devicelog.dno = device.dno order by dltime desc"})
  List<DeviceLog> findAll();
  
  @Select({"select * from deviceLog where dlno = #{dlno}"})
  DeviceLog findDeviceLogByDlno(@Param("dlno") Integer paramInteger);
  
  @Select({"select count(dlno) from deviceLog"})
  int findCount();
  
  @Insert({"insert into deviceLog(dno,dlcount,dltype,dltime,dlog) values(#{deviceLog.dno},#{deviceLog.dlcount},#{deviceLog.dltype},#{deviceLog.dltime},#{deviceLog.dlog})"})
  void insertDeviceLog(@Param("deviceLog") DeviceLog paramDeviceLog);
  
  @Delete({"delete from deviceLog where dlno = #{dlno}"})
  void deleteDeviceLog(@Param("dlno") Integer paramInteger);
  
  @Select({"select devicelog.*,dname from devicelog,device where devicelog.dno = device.dno and dname like #{dname} and dltype=#{dltype} and dltime > #{date1} and dltime < #{date2} order by dltime desc"})
  List<DeviceLog> findDeviceLogByEleHasDltype(@Param("dname") String paramString1, @Param("dltype") Integer paramInteger, @Param("date1") String paramString2, @Param("date2") String paramString3);
  
  @Select({"select * from devicelog,device where devicelog.dno = device.dno and dname like #{dname} and dltime > #{date1} and dltime < #{date2} order by dltime desc"})
  List<DeviceLog> findDeviceLogByEleNoDltype(@Param("dname") String paramString1, @Param("date1") String paramString2, @Param("date2") String paramString3);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\IDeviceLogDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */