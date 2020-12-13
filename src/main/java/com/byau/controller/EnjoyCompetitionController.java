package com.byau.controller;

import com.byau.domain.Competition;
import com.byau.domain.EnjoyCompetition;
import com.byau.domain.Student;
import com.byau.service.ICompetitionService;
import com.byau.service.IEnjoyCompetitionService;
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

@RequestMapping({"enjoyCompetition"})
@Controller
public class EnjoyCompetitionController {
    String sno = "";
    String cname = "";
    @Autowired
    IEnjoyCompetitionService enjoyCompetitionService;

    @Autowired
    ICompetitionService competitionService;

    @Autowired
    IStudentService studentService;

    Competition competitionmain = null;

    @RequestMapping({"deleteByCidSno"})
    public void deleteByCidSno(String page, String studentpage, Integer cid, String sno, HttpServletResponse response, HttpServletRequest request) throws IOException {
        this.enjoyCompetitionService.deleteByCidSno(cid, sno);
        if ("student".equals(page)) {
            if ("list".equals(studentpage)) {
                response.sendRedirect(request.getContextPath() + "/student/findByPage");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/student/findAll");
            return;
        }
        if ("competition".equals(page)) {
            response.sendRedirect(request.getContextPath() + "/competition/findAll");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/enjoyCompetition/findAll");
    }

    @RequestMapping({"findByCid"})
    public String findByCid(Integer cid, Model model) {
        Competition competition = this.enjoyCompetitionService.findStudentByCid(cid);
        this.competitionmain = competition;
        List<Student> students = competition.getStudents();
        model.addAttribute("cid", competition.getCid());
        model.addAttribute("studentlist", students);
        model.addAttribute("cname", competition.getCname());
        return "enjoycompetition/enjoycompetition_excel";
    }
//
//    @GetMapping({"export"})
//    public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Competition competition = this.competitionmain;
//        List<Student> students = competition.getStudents();
//        String checkColumnStr = "";
//        for (String check : checkColumn)
//            checkColumnStr = checkColumnStr + check;
//        System.out.println("checkColumnStr => " + checkColumnStr);
//        List<String> attributes = new ArrayList<>();
//        if (checkColumnStr.contains("cid"))
//            attributes.add("竞赛编号");
//        if (checkColumnStr.contains("cname"))
//            attributes.add("竞赛名称");
//        if (checkColumnStr.contains("sno"))
//            attributes.add("学号");
//        if (checkColumnStr.contains("sname"))
//            attributes.add("姓名");
//        if (checkColumnStr.contains("ssex"))
//            attributes.add("性别");
//        if (checkColumnStr.contains("sclass"))
//            attributes.add("班级");
//        if (checkColumnStr.contains("syear"))
//            attributes.add("年级");
//        if (checkColumnStr.contains("stel"))
//            attributes.add("手机号");
//        if (checkColumnStr.contains("sqq"))
//            attributes.add("qq账号");
//        for (String str : attributes)
//            System.out.println("attributes => " + str);
//        List<List<String>> data = new ArrayList<>();
//        for (Student student : students) {
//            List<String> rowInfo = new ArrayList<>();
//            if (checkColumnStr.contains("cid"))
//                rowInfo.add(String.valueOf(competition.getCid()));
//            if (checkColumnStr.contains("cname"))
//                rowInfo.add(competition.getCname());
//            if (checkColumnStr.contains("sno"))
//                rowInfo.add(student.getSno());
//            if (checkColumnStr.contains("sname"))
//                rowInfo.add(student.getSname());
//            if (checkColumnStr.contains("ssex"))
//                rowInfo.add(student.getSsex());
//            if (checkColumnStr.contains("sclass"))
//                rowInfo.add(student.getSclass());
//            if (checkColumnStr.contains("syear"))
//                rowInfo.add(String.valueOf(student.getSyear()));
//            if (checkColumnStr.contains("stel"))
//                rowInfo.add(student.getStel());
//            if (checkColumnStr.contains("sqq"))
//                rowInfo.add(student.getSqq());
//            for (String str : rowInfo)
//                System.out.println("rowInfo => " + str);
//            System.out.println("--------------");
//            data.add(rowInfo);
//        }
//        Workbook workbook = POIUtil.createExcelFile(attributes, data, "xls");
//        if (workbook == null) {
//            System.out.println("Error 1");
//            throw new RuntimeException("create excel file error");
//        }
//        String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
//        String fileName = "EnjoyCompetition-" + (new Date()).getTime() + "." + "xls";
//        FileOutputStream fout = null;
//        try {
//            fout = new FileOutputStream(storePath + fileName);
//            System.out.println("fout => " + fout);
//            workbook.write(fout);
//        } catch (IOException e) {
//            System.out.println("IOException !!!!");
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fout != null)
//                    fout.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        File file = new File(storePath + fileName);
//        if (!file.exists()) {
//            System.out.println("Error 2");
//            throw new RuntimeException("file do not exist");
//        }
//        InputStream inputStream = null;
//        ServletOutputStream servletOutputStream = null;
//        response.reset();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/vnd.ms-excel");
//        response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//        int fileLength = (int) file.length();
//        response.setContentLength(fileLength);
//        try {
//            if (fileLength != 0) {
//                inputStream = new FileInputStream(file);
//                byte[] buf = new byte[4096];
//                servletOutputStream = response.getOutputStream();
//                int readLength;
//                while ((readLength = inputStream.read(buf)) != -1)
//                    servletOutputStream.write(buf, 0, readLength);
//            }
//        } catch (IOException e) {
//            System.out.println("download file error");
//            e.printStackTrace();
//            throw new RuntimeException("download file error");
//        } finally {
//            try {
//                if (servletOutputStream != null)
//                    servletOutputStream.close();
//                if (inputStream != null) {
//                    servletOutputStream.flush();
//                    inputStream.close();
//                }
//                file.delete();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        response.sendRedirect(request.getContextPath() + "/enjoycompetition/findAll");
//    }
@RequestMapping({"findByEle"})
public String findStudentByEle(Model model, String sno, String cname) {
    if (sno == null) {
        sno = "";
    } else {
        model.addAttribute("sno", sno);
    }
    if (cname == null) {
        cname = "";
    } else {
        model.addAttribute("cname", cname);
    }
    this.sno = sno;
    this.cname = cname;
    System.out.println("EnjoyCompetitionController findByEle.");
    List<EnjoyCompetition> enjoyCompetitions = this.enjoyCompetitionService.findByEle(sno, cname);
    model.addAttribute("enjoycompetitionlist", enjoyCompetitions);
    return "enjoycompetition/enjoycompetition_all";
}
    @RequestMapping({"findAll"})
    public String findAll(Model model) {
        List<EnjoyCompetition> enjoyCompetitions = this.enjoyCompetitionService.findAll();
        for (EnjoyCompetition enjoyCompetition : enjoyCompetitions)
            System.out.println(enjoyCompetition);
        model.addAttribute("enjoycompetitionlist", enjoyCompetitions);
        return "enjoycompetition/enjoycompetition_all";
    }

    @RequestMapping({"addStudent_page"})
    public String addStudent_page(Integer cid, Model model) {
        Competition competition = this.competitionService.findCompetitionByCid(cid);
        model.addAttribute("competition", competition);
        return "enjoycompetition/enjoycompetition_add";
    }

    @RequestMapping({"addStudent"})
    public String addStudent(Integer cid, String sno, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = this.studentService.findStudentBySno(sno);
        Competition competition = this.competitionService.findCompetitionByCid(cid);
        model.addAttribute("competition", competition);
        if (student == null) {
            model.addAttribute("msg", "学号不存在！ 请重新输出");
        } else {
            this.enjoyCompetitionService.insertEnjoyCompetition(cid, sno);
            model.addAttribute("msg", "新增成功 可继续添加");
        }
        return "enjoycompetition/enjoycompetition_add";
    }
}
