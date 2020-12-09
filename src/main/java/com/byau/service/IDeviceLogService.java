package com.byau.service;

import com.byau.domain.DeviceLog;
import java.util.List;

public interface IDeviceLogService {
  List<DeviceLog> findAll();
  
  List<DeviceLog> findDeviceLogByEle(String paramString1, Integer paramInteger, String paramString2, String paramString3);
  
  DeviceLog findDeviceLogByDlno(Integer paramInteger);
  
  int findCount();
  
  void insertDeviceLog(DeviceLog paramDeviceLog);
  
  void deleteDeviceLog(Integer paramInteger);
}
