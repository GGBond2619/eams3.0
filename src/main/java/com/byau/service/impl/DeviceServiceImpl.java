package com.byau.service.impl;

import com.byau.dao.IDeviceDao;
import com.byau.domain.Device;
import com.byau.service.IDeviceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    IDeviceDao deviceDao;

    public List<Device> findAll() {
        System.out.println("DeviceServiceImpl findAll");
        return this.deviceDao.findAll();
    }

    public Device findDeviceByDno(String dno) {
        System.out.println("DeviceServiceImpl findDeviceByDno");
        return this.deviceDao.findDeviceByDno(dno);
    }

    public List<Device> findDeviceByEle(String dno, String dname, String dtype) {
        System.out.println("DeviceServiceImpl findDeviceByEle");
        dno = "%" + dno + "%";
        dname = "%" + dname + "%";
        dtype = "%" + dtype + "%";
        return this.deviceDao.findDeviceByEle(dno, dname, dtype);
    }

    public List<Device> findDeviceByDcount(Integer dcount) {
        System.out.println("DeviceServiceImpl findDeviceByDcount");
        return this.deviceDao.findDeviceByDcount(dcount);
    }

    public Device findDeviceByDname(String dname) {
        System.out.println("DeviceServiceImpl findDeviceByDname");
        return this.deviceDao.findDeviceByDname(dname);
    }

    public int findCount() {
        System.out.println("DeviceServiceImpl findCount");
        return this.deviceDao.findCount();
    }

    public void insertDevice(Device device) {
        System.out.println("DeviceServiceImpl insertDevice");
        this.deviceDao.insertDevice(device);
    }

    public void deleteDevice(String dno) {
        System.out.println("DeviceServiceImpl deleteDevice dno=" + dno);
        this.deviceDao.deleteDevice(dno);
    }

    public void updateDevice(String dno, Device device) {
        System.out.println("DeviceServiceImpl updateDevice dno=" + dno);
        this.deviceDao.updateDevice(dno, device);
    }
}
