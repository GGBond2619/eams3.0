/*     */ package com.byau.dao;

import com.byau.domain.Device;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceDao {
  @Select({"SELECT * FROM device ORDER BY CAST(dno AS DECIMAL(10));"})
  List<Device> findAll();
  
  @Select({"select * from device where dno = #{dno}"})
  Device findDeviceByDno(@Param("dno") String paramString);
  
  @Select({"select * from device where dno like #{dno} and dname like #{dname} and dtype like #{dtype} order by dno"})
  List<Device> findDeviceByEle(@Param("dno") String paramString1, @Param("dname") String paramString2, @Param("dtype") String paramString3);
  
  @Select({"select * from device where dcount < #{dcount}"})
  List<Device> findDeviceByDcount(@Param("dcount") Integer paramInteger);
  
  @Select({"select * from device where dname = #{dname}"})
  Device findDeviceByDname(@Param("dname") String paramString);
  
  @Select({"select count(dno) from device"})
  int findCount();
  
  @Insert({"insert into device(dno,dname,dtype,dcount) values(#{device.dno},#{device.dname},#{device.dtype},#{device.dcount})"})
  void insertDevice(@Param("device") Device paramDevice);
  
  @Delete({"delete from device where dno = #{dno}"})
  void deleteDevice(@Param("dno") String paramString);
  
  @Update({"update device set dname=#{device.dname},dtype=#{device.dtype},dcount=#{device.dcount} where dno = #{dno}"})
  void updateDevice(@Param("dno") String paramString, @Param("device") Device paramDevice);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\dao\IDeviceDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */