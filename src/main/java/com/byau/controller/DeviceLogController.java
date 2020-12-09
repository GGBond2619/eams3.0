 package com.byau.controller;
 
 import com.byau.domain.Device;
 import com.byau.domain.DeviceLog;
 import com.byau.service.IDeviceLogService;
 import com.byau.service.IDeviceService;
 import com.byau.util.POIUtil;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.poi.ss.usermodel.Workbook;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 
 @Controller
 @RequestMapping({"devicelog"})
 public class DeviceLogController {
   @Autowired
   IDeviceLogService deviceLogService;
   
   @Autowired
   IDeviceService deviceService;
   
   List<DeviceLog> deviceLogsmain = new ArrayList<>();
   
   @GetMapping({"export"})
   public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
     List<DeviceLog> deviceLogs = this.deviceLogsmain;
     String checkColumnStr = "";
     for (String check : checkColumn)
       checkColumnStr = checkColumnStr + check; 
     System.out.println("checkColumnStr => " + checkColumnStr);
     List<String> attributes = new ArrayList<>();
     if (checkColumnStr.contains("dname"))
       attributes.add("元器件名称"); 
     if (checkColumnStr.contains("dltype"))
       attributes.add("修改类型"); 
     if (checkColumnStr.contains("dlcount"))
       attributes.add("修改数量"); 
     if (checkColumnStr.contains("dltime"))
       attributes.add("修改时间"); 
     if (checkColumnStr.contains("dlog"))
       attributes.add("备注信息"); 
     for (String str : attributes)
       System.out.println("attributes => " + str); 
     List<List<String>> data = new ArrayList<>();
     for (DeviceLog devicelog : deviceLogs) {
       List<String> rowInfo = new ArrayList<>();
       if (checkColumnStr.contains("dname"))
         rowInfo.add(devicelog.getDname()); 
       if (checkColumnStr.contains("dltype"))
         if (devicelog.getDltype().intValue() == 1) {
           rowInfo.add("增加");
         } else {
           rowInfo.add("减少");
         }  
       if (checkColumnStr.contains("dlcount"))
         rowInfo.add(String.valueOf(devicelog.getDlcount())); 
       if (checkColumnStr.contains("dltime"))
         rowInfo.add(devicelog.getDltime()); 
       if (checkColumnStr.contains("dlog"))
         rowInfo.add(devicelog.getDlog()); 
       for (String str : rowInfo)
         System.out.println("rowInfo => " + str); 
       System.out.println("--------------");
       data.add(rowInfo);
     } 
     Workbook workbook = POIUtil.createExcelFile(attributes, data, "xls");
     if (workbook == null) {
       System.out.println("Error 1");
       throw new RuntimeException("create excel file error");
     } 
     String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
     String fileName = "DeviceLog-" + (new Date()).getTime() + "." + "xls";
     FileOutputStream fout = null;
     try {
       fout = new FileOutputStream(storePath + fileName);
       System.out.println("fout => " + fout);
       workbook.write(fout);
     } catch (IOException e) {
       System.out.println("IOException !!!!");
       e.printStackTrace();
     } finally {
       try {
         if (fout != null)
           fout.close(); 
       } catch (IOException e) {
         e.printStackTrace();
       } 
     } 
     File file = new File(storePath + fileName);
     if (!file.exists()) {
       System.out.println("Error 2");
       throw new RuntimeException("file do not exist");
     } 
     InputStream inputStream = null;
     ServletOutputStream servletOutputStream = null;
     response.reset();
     response.setCharacterEncoding("utf-8");
     response.setContentType("application/vnd.ms-excel");
     response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
     int fileLength = (int)file.length();
     response.setContentLength(fileLength);
     try {
       if (fileLength != 0) {
         inputStream = new FileInputStream(file);
         byte[] buf = new byte[4096];
         servletOutputStream = response.getOutputStream();
         int readLength;
         while ((readLength = inputStream.read(buf)) != -1)
           servletOutputStream.write(buf, 0, readLength); 
       } 
     } catch (IOException e) {
       System.out.println("download file error");
       e.printStackTrace();
       throw new RuntimeException("download file error");
     } finally {
       try {
         if (servletOutputStream != null)
           servletOutputStream.close(); 
         if (inputStream != null) {
           servletOutputStream.flush();
           inputStream.close();
         } 
         file.delete();
       } catch (IOException e) {
         e.printStackTrace();
       } 
     } 
     response.sendRedirect(request.getContextPath() + "/devicelog/findAll");
   }
   
   @RequestMapping({"adddevice"})
   public String addDevice(Model model) {
     List<Device> devices = this.deviceService.findAll();
     model.addAttribute("devicelist", devices);
     return "devicelog/devicelog_add";
   }
   
   @RequestMapping({"adddevicelog_add"})
   public String addDeviceLog(DeviceLog deviceLog, Model model) {
     Date now = new Date();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String dltime = dateFormat.format(now);
     deviceLog.setDltime(dltime);
     deviceLog.setDltype(Integer.valueOf(1));
     this.deviceLogService.insertDeviceLog(deviceLog);
     List<Device> devices = this.deviceService.findAll();
     model.addAttribute("devicelist", devices);
     return "device/device_excel";
   }
   
   @RequestMapping({"subdevice"})
   public String subDevice(Model model) {
     List<Device> devices = this.deviceService.findAll();
     model.addAttribute("devicelist", devices);
     return "devicelog/devicelog_sub";
   }
   
   @RequestMapping({"adddevicelog_sub"})
   public String subDeviceLog(DeviceLog deviceLog, Model model) {
     Date now = new Date();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String dltime = dateFormat.format(now);
     deviceLog.setDltime(dltime);
     deviceLog.setDltype(Integer.valueOf(-1));
     this.deviceLogService.insertDeviceLog(deviceLog);
     List<Device> devices = this.deviceService.findAll();
     model.addAttribute("devicelist", devices);
     return "device/device_excel";
   }
   
   @RequestMapping({"/findAll"})
   public String findAll(Model model) {
     System.out.println("DeviceLogController findAll.");
     List<DeviceLog> devicelogs = this.deviceLogService.findAll();
     this.deviceLogsmain = devicelogs;
     model.addAttribute("deviceloglist", devicelogs);
     return "devicelog/devicelog_excel";
   }
   
   @RequestMapping({"/deleteByDlno"})
   public String deleteByDlno(Integer dlno, Model model) {
     this.deviceLogService.deleteDeviceLog(dlno);
     List<DeviceLog> devicelogs = this.deviceLogService.findAll();
     model.addAttribute("deviceloglist", devicelogs);
     return "devicelog/devicelog_excel";
   }
   
   @RequestMapping({"/findDeviceLogByEle"})
   public String findDeviceLogByEle(String dname, Integer dltype, String date1, String date2, Model model) {
     if (dname == null)
       dname = ""; 
     List<DeviceLog> deviceLogs = this.deviceLogService.findDeviceLogByEle(dname, dltype, date1, date2);
     this.deviceLogsmain = deviceLogs;
     model.addAttribute("deviceloglist", deviceLogs);
     return "devicelog/devicelog_excel";
   }
 }