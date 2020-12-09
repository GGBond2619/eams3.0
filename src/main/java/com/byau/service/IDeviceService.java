package com.byau.service;

import com.byau.domain.Device;
import java.util.List;

public interface IDeviceService {
  List<Device> findAll();
  
  Device findDeviceByDno(String paramString);
  
  List<Device> findDeviceByEle(String paramString1, String paramString2, String paramString3);
  
  List<Device> findDeviceByDcount(Integer paramInteger);
  
  Device findDeviceByDname(String paramString);
  
  int findCount();
  
  void insertDevice(Device paramDevice);
  
  void deleteDevice(String paramString);
  
  void updateDevice(String paramString, Device paramDevice);
}
