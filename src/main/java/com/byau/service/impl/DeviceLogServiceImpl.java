package com.byau.service.impl;

import com.byau.dao.IDeviceLogDao;
import com.byau.domain.Device;
import com.byau.domain.DeviceLog;
import com.byau.service.IDeviceLogService;
import com.byau.service.IDeviceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deviceLogService")
public class DeviceLogServiceImpl implements IDeviceLogService {
    @Autowired
    IDeviceLogDao deviceLogDao;

    @Autowired
    IDeviceService deviceService;

    public List<DeviceLog> findAll() {
        return this.deviceLogDao.findAll();
    }

    public List<DeviceLog> findDeviceLogByEle(String dname, Integer dltype, String date1, String date2) {
        dname = "%" + dname + "%";
        System.out.println(dltype);
        List<DeviceLog> deviceLogs = null;
        if ((date1 == null || date1 == "") && (date2 == null || date2 == "")) {
            date1 = "2000";
            date2 = "9999";
        } else if (date1 == null || date1 == "") {
            date1 = "2000";
        } else if (date2 == null || date2 == "") {
            date2 = "9999";
        }
        date1 = date1 + " 00:00:00";
        date2 = date2 + " 24:00:00";
        if (dltype.intValue() == 0) {
            deviceLogs = this.deviceLogDao.findDeviceLogByEleNoDltype(dname, date1, date2);
            System.out.println("DeviceLogServiceImpl findDeviceLogByEle : " + dname + " " + date1 + " " + date2);
        } else {
            deviceLogs = this.deviceLogDao.findDeviceLogByEleHasDltype(dname, dltype, date1, date2);
            System.out.println("DeviceLogServiceImpl findDeviceLogByEle : " + dname + " " + dltype + " " + date1 + " " + date2);
        }
        return deviceLogs;
    }

    public DeviceLog findDeviceLogByDlno(Integer dlno) {
        return this.deviceLogDao.findDeviceLogByDlno(dlno);
    }

    public int findCount() {
        return this.deviceLogDao.findCount();
    }

    public void insertDeviceLog(DeviceLog deviceLog) {
        if (deviceLog.getDltype().intValue() > 0) {
            Device device = this.deviceService.findDeviceByDno(deviceLog.getDno());
            device.setDcount(Integer.valueOf(device.getDcount().intValue() + deviceLog.getDlcount().intValue()));
            this.deviceService.updateDevice(device.getDno(), device);
        } else {
            Device device = this.deviceService.findDeviceByDno(deviceLog.getDno());
            device.setDcount(Integer.valueOf(device.getDcount().intValue() - deviceLog.getDlcount().intValue()));
            this.deviceService.updateDevice(device.getDno(), device);
        }
        if (deviceLog.getDlog() == null)
            deviceLog.setDlog("");
        this.deviceLogDao.insertDeviceLog(deviceLog);
    }

    public void deleteDeviceLog(Integer dlno) {
        this.deviceLogDao.deleteDeviceLog(dlno);
    }
}
