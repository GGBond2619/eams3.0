package com.byau.controller;

import com.byau.domain.Device;
import com.byau.domain.Page;
import com.byau.service.IDeviceService;
import com.byau.util.POIUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/device"})
public class DeviceController {
    @Autowired
    private IDeviceService deviceService;
    private String dno = "";
    private String dname = "";
    private String dtype = "";
    Page p = new Page();

    @GetMapping({"export"})
    public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Device> devices = this.deviceService.findDeviceByEle(this.dno, this.dname, this.dtype);
        String checkColumnStr = "";
        for (String check : checkColumn) checkColumnStr = checkColumnStr + check;
        System.out.println("checkColumnStr => " + checkColumnStr);
        List<String> attributes = new ArrayList<>();
        if (checkColumnStr.contains("dno")) attributes.add("元器件编号");
        if (checkColumnStr.contains("dname")) attributes.add("元器件名称");
        if (checkColumnStr.contains("dtype")) attributes.add("元器件类型");
        if (checkColumnStr.contains("dcount")) attributes.add("剩余数量");
        for (String str : attributes) System.out.println("attributes => " + str);
        List<List<String>> data = new ArrayList<>();
        for (Device device : devices) {
            List<String> rowInfo = new ArrayList<>();
            if (checkColumnStr.contains("dno")) rowInfo.add(device.getDno());
            if (checkColumnStr.contains("dname")) rowInfo.add(device.getDname());
            if (checkColumnStr.contains("dtype")) rowInfo.add(device.getDtype());
            if (checkColumnStr.contains("dcount")) rowInfo.add(String.valueOf(device.getDcount()));
            for (String str : rowInfo) System.out.println("rowInfo => " + str);
            System.out.println("--------------");
            data.add(rowInfo);
        }
        Workbook workbook = POIUtil.createExcelFile(attributes, data, "xls");
        if (workbook == null) {
            System.out.println("Error 1");
            throw new RuntimeException("create excel file error");
        }
        String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
        String fileName = "Device-" + (new Date()).getTime() + "." + "xls";
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
                if (fout != null) fout.close();
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
        int fileLength = (int) file.length();
        response.setContentLength(fileLength);
        try {
            if (fileLength != 0) {
                inputStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                servletOutputStream = response.getOutputStream();
                int readLength;
                while ((readLength = inputStream.read(buf)) != -1) servletOutputStream.write(buf, 0, readLength);
            }
        } catch (IOException e) {
            System.out.println("download file error");
            e.printStackTrace();
            throw new RuntimeException("download file error");
        } finally {
            try {
                if (servletOutputStream != null) servletOutputStream.close();
                if (inputStream != null) {
                    servletOutputStream.flush();
                    inputStream.close();
                }
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/device/findAll");
    }

    @PostMapping({"import"})
    @ResponseBody
    public void importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("DeviceController import");
        if (!excelFile.isEmpty()) {
            String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
            excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
        }
        List<String[]> excelData = POIUtil.readExcelFile(excelFile, 1);
        List<Device> devices = new ArrayList<>();
        for (String[] arr : excelData) {
            Device device = new Device();
            device.setDno(arr[0]);
            device.setDname(arr[1]);
            device.setDtype(arr[2]);
            try {
                device.setDcount(Integer.valueOf(arr[3]));
            } catch (Exception e) {
                e.printStackTrace();
                device.setDcount(Integer.valueOf(-1));
            }
            devices.add(device);
        }
        System.out.println(devices);
        for (Device device : devices) {
            if (this.deviceService.findDeviceByDname(device.getDname()) == null) {
                System.out.println("if (this.deviceService.findDeviceByDname(device.getDname()) == null ");
                this.deviceService.insertDevice(device);
            }
        }
        response.sendRedirect(request.getContextPath() + "/device/findAll");
    }

    @RequestMapping({"/findAll"})
    public String findAll(Model model) {
        System.out.println("DeviceController findAll.");
        this.dno = "";
        this.dname = "";
        this.dtype = "";
        List<Device> devices = this.deviceService.findAll();
        model.addAttribute("devicelist", devices);
        return "device/device_excel";
    }

    @RequestMapping({"/findDeviceByEle"})
    public String findDeviceByEle(Model model, String dno, String dname, String dtype) {
        if (dno == null) {
            dno = "";
        } else {
            model.addAttribute("dno", dno);
        }
        if (dname == null) {
            dname = "";
        } else {
            model.addAttribute("dname", dname);
        }
        if (dtype == null) {
            dtype = "";
        } else {
            model.addAttribute("dtype", dtype);
        }
        this.dno = dno;
        this.dname = dname;
        this.dtype = dtype;
        System.out.println("DeviceController findDeviceByEle.");
        List<Device> devices = this.deviceService.findDeviceByEle(dno, dname, dtype);
        model.addAttribute("devicelist", devices);
        return "device/device_excel";
    }

    @RequestMapping({"/deleteByDno_All"})
    public void deleteByDno_All(String dno, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("DeviceController deleteByDno.");
        this.deviceService.deleteDevice(dno);
        response.sendRedirect(request.getContextPath() + "/device/findAll");
    }

    @RequestMapping({"/deleteByDno_excel"})
    public void deleteByDno_excel(String dno, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("DeviceController deleteByDno.");
        this.deviceService.deleteDevice(dno);
        response.sendRedirect(request.getContextPath() + "/device/findAll");
    }

    @RequestMapping({"/addDevice"})
    public String addDevice(Device device) {
        System.out.println(device);
        this.deviceService.insertDevice(device);
        return "device/device_add";
    }

    @RequestMapping({"/updateDevice_page"})
    public String updateDevice_page(Model model, String dno) {
        System.out.println("updateDevice_page Controller...");
        Device device = this.deviceService.findDeviceByDno(dno);
        model.addAttribute("device", device);
        return "device/device_update";
    }

    @RequestMapping({"/updateDevice"})
    public void updateDevice(Model model, String dno, Device device, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("updateDevice Controller...");
        if (dno != null && device != null) this.deviceService.updateDevice(dno, device);
        response.sendRedirect(request.getContextPath() + "/device/findAll");
    }

    @RequestMapping({"/exportForm"})
    public void exportForm(@RequestParam("checkColumn") String[] checkColumn, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (String str : checkColumn) System.out.println(str);
        response.sendRedirect(request.getContextPath() + "/device/findByPage");
    }
}