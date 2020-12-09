package com.byau.controller;

import com.byau.domain.Activity;
import com.byau.service.IActivityService;
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
@RequestMapping({"activity"})
public class ActivityController {
    @Autowired
    IActivityService activityService;

    List<Activity> activitymain = new ArrayList<>();

    @RequestMapping({"findAll"})
    public String findAll(Model model) {
        List<Activity> activities = this.activityService.findAll();
        this.activitymain = activities;
        model.addAttribute("activitylist", activities);
        return "activity/activity_excel";
    }

    @RequestMapping({"findDoing"})
    public String findDoing(Model model) {
        List<Activity> activities = this.activityService.findDoing();
        this.activitymain = activities;
        model.addAttribute("activitylist", activities);
        return "activity/activity_excel";
    }

    @RequestMapping({"/updateActivity_page"})
    public String updateActivity_page(Model model, Integer ano, String inUpdatePage) {
        System.out.println("updateActivity_page Controller...");
        Activity activity = this.activityService.findActivityByAno(ano);
        model.addAttribute("activity", activity);
        return "activity/activity_update";
    }

    @RequestMapping({"/updateActivity"})
    public void updateActivity(Model model, Integer ano, Activity activity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("updateActivity Controller...");
        if (ano != null && activity != null)
            this.activityService.updateActivity(ano, activity);
        response.sendRedirect(request.getContextPath() + "/activity/findAll");
    }

    @RequestMapping({"/addActivity"})
    public void addActivity(Activity activity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(activity);
        this.activityService.addActivity(activity);
        response.sendRedirect(request.getContextPath() + "/activity/findAll");
    }

    @RequestMapping({"deleteByAno_excel"})
    public void deleteByAno_excel(Integer ano, HttpServletResponse response, HttpServletRequest request) throws IOException {
        this.activityService.deleteByAno(ano);
        response.sendRedirect(request.getContextPath() + "/activity/findAll");
    }

    @RequestMapping({"/findStudents"})
    public String findStudents(Integer ano, Model model) {
        Activity activity = this.activityService.findStudentsByAno(ano);
        model.addAttribute("students", activity.getStudents());
        model.addAttribute("ano", activity.getAno());
        model.addAttribute("aname", activity.getAname());
        return "activity/activity_students";
    }

    @GetMapping({"export"})
    public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Activity> activitys = null;
        if (this.activitymain.size() != 0) {
            activitys = this.activitymain;
        } else {
            activitys = this.activityService.findAll();
        }
        String checkColumnStr = "";
        for (String check : checkColumn)
            checkColumnStr = checkColumnStr + check;
        System.out.println("checkColumnStr => " + checkColumnStr);
        List<String> attributes = new ArrayList<>();
        if (checkColumnStr.contains("aname"))
            attributes.add("活动名称");

        if (checkColumnStr.contains("begintime"))
            attributes.add("开始时间");

        if (checkColumnStr.contains("endtime"))
            attributes.add("结束时间");

        if (checkColumnStr.contains("site"))
            attributes.add("活动地点");

        if (checkColumnStr.contains("master"))
            attributes.add("主持人");

        if (checkColumnStr.contains("aim"))
            attributes.add("活动目的");

        if (checkColumnStr.contains("ask"))
            attributes.add("活动要求");

        for (String str : attributes)

            System.out.println("attributes => " + str);

        List<List<String>> data = new ArrayList<>();

        for (Activity activity : activitys) {

            List<String> rowInfo = new ArrayList<>();

            if (checkColumnStr.contains("aname"))
                rowInfo.add(activity.getAname());

            if (checkColumnStr.contains("begintime"))
                rowInfo.add(activity.getBegintime());

            if (checkColumnStr.contains("endtime"))
                rowInfo.add(activity.getEndtime());

            if (checkColumnStr.contains("site"))
                rowInfo.add(activity.getSite());

            if (checkColumnStr.contains("master"))
                rowInfo.add(String.valueOf(activity.getMaster()));

            if (checkColumnStr.contains("aim"))
                rowInfo.add(activity.getAim());

            if (checkColumnStr.contains("ask"))
                rowInfo.add(activity.getAsk());

            for (String str : rowInfo)
                System.out.println("rowInfo => " + str);
            data.add(rowInfo);
        }

        Workbook workbook = POIUtil.createExcelFile(attributes, data, "xls");

        if (workbook == null) {

            System.out.println("Error 1");

            throw new RuntimeException("create excel file error");
        }

        String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";

        String fileName = "Activity-" + (new Date()).getTime() + "." + "xls";

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
        int fileLength = (int) file.length();
        response.setContentLength(fileLength);
        try {
            if (fileLength != 0) {
                inputStream = new FileInputStream(file);
                byte[] buf = new byte[6];
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
        response.sendRedirect(request.getContextPath() + "/activity/findAll");
    }
}
