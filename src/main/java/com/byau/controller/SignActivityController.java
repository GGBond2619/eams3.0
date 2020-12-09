package com.byau.controller;

import com.byau.domain.Activity;
import com.byau.domain.Student;
import com.byau.service.IActivityService;
import com.byau.service.ISignService;
import com.byau.service.IStudentService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"sign"})
public class SignActivityController {
    @Autowired
    ISignService signService;
    @Autowired
    IActivityService activityService;
    @Autowired
    IStudentService studentService;

    @GetMapping({"export"})
    public void createAndDownloadExcelFile(@RequestParam("ano") Integer ano, @RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(ano);
        Activity activity = this.activityService.findStudentsByAno(ano);
        System.out.println(activity);
        List<Student> students = activity.getStudents();
        String checkColumnStr = "";
        for (String check : checkColumn) checkColumnStr = checkColumnStr + check;
        System.out.println("checkColumnStr => " + checkColumnStr);
        List<String> attributes = new ArrayList<>();
        if (checkColumnStr.contains("aname")) attributes.add("活动名称");
        if (checkColumnStr.contains("sno")) attributes.add("学号");
        if (checkColumnStr.contains("sname")) attributes.add("姓名");
        if (checkColumnStr.contains("ssex")) attributes.add("性别");
        if (checkColumnStr.contains("sclass")) attributes.add("班级");
        if (checkColumnStr.contains("syear")) attributes.add("年级");
        if (checkColumnStr.contains("stel")) attributes.add("手机号");
        if (checkColumnStr.contains("sqq")) attributes.add("qq账号");
        for (String str : attributes) System.out.println("attributes => " + str);
        List<List<String>> data = new ArrayList<>();
        for (Student student : students) {
            List<String> rowInfo = new ArrayList<>();
            if (checkColumnStr.contains("aname")) rowInfo.add(activity.getAname());
            if (checkColumnStr.contains("sno")) rowInfo.add(student.getSno());
            if (checkColumnStr.contains("sname")) rowInfo.add(student.getSname());
            if (checkColumnStr.contains("ssex")) rowInfo.add(student.getSsex());
            if (checkColumnStr.contains("sclass")) rowInfo.add(student.getSclass());
            if (checkColumnStr.contains("syear")) rowInfo.add(String.valueOf(student.getSyear()));
            if (checkColumnStr.contains("stel")) rowInfo.add(student.getStel());
            if (checkColumnStr.contains("sqq")) rowInfo.add(student.getSqq());
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
        String fileName = "activity_student-" + (new Date()).getTime() + "." + "xls";
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
        response.sendRedirect(request.getContextPath() + "/student/findAll");
    }

    @RequestMapping({"deleteByAnoSno"})
    public void deleteByAnoSno(Integer ano, String sno, String page, String studentpage, HttpServletResponse response, HttpServletRequest request) throws IOException {
        this.signService.deleteByAnoSno(ano, sno);
        if ("student".equals(page)) {
            if ("list".equals(studentpage)) {
                response.sendRedirect(request.getContextPath() + "/student/findByPage");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/student/findAll");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/activity/findAll");
    }

    @RequestMapping({"addStudent_page"})
    public String addStudent_page(Integer ano, Model model) {
        Activity activity = this.activityService.findActivityByAno(ano);
        model.addAttribute("activity", activity);
        return "signactivity/sign_add";
    }

    @RequestMapping({"addStudent"})
    public String addStudent(Integer ano, String sno, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = this.studentService.findStudentBySno(sno);
        Activity activity = this.activityService.findActivityByAno(ano);
        model.addAttribute("activity", activity);
        if (student == null) {
            model.addAttribute("msg", "学号不存在！ 请重新输出");
        } else {
            this.signService.insertSign(ano, sno);
            model.addAttribute("msg", "新增成功 可继续添加");
        }
        return "signactivity/sign_add";
    }
}