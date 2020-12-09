package com.byau.controller;

import com.byau.domain.Competition;
import com.byau.domain.Page;
import com.byau.service.ICompetitionService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/competition"})
public class CompetitionController {
    @Autowired
    private ICompetitionService competitionService;

    private String inUpdatePage = "findByPage";

    private String cid = "";

    private String cname = "";

    private String ctype = "";

    Page p = new Page();

    @GetMapping({"export"})
    public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Competition> competitions = this.competitionService.findCompetitionByEle(this.cname, this.ctype);
        String checkColumnStr = "";
        for (String check : checkColumn)
            checkColumnStr = checkColumnStr + check;
        System.out.println("checkColumnStr => " + checkColumnStr);
        List<String> attributes = new ArrayList<>();
        if (checkColumnStr.contains("cname"))
            attributes.add("比赛名称");
        if (checkColumnStr.contains("ctype"))
            attributes.add("比赛类型");
        if (checkColumnStr.contains("cplace"))
            attributes.add("比赛地点");
        if (checkColumnStr.contains("cbegintime"))
            attributes.add("开始时间");
        for (String str : attributes)
            System.out.println("attributes => " + str);
        List<List<String>> data = new ArrayList<>();
        for (Competition competition : competitions) {
            List<String> rowInfo = new ArrayList<>();
            if (checkColumnStr.contains("cname"))
                rowInfo.add(competition.getCname());
            if (checkColumnStr.contains("ctype"))
                rowInfo.add(competition.getCtype());
            if (checkColumnStr.contains("cplace"))
                rowInfo.add(competition.getCplace());
            if (checkColumnStr.contains("cbegintime"))
                rowInfo.add(competition.getCbegintime());
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
        String fileName = "Competition-" + (new Date()).getTime() + "." + "xls";
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
        response.sendRedirect(request.getContextPath() + "/competition/findAll");
    }

    @PostMapping({"import"})
    @ResponseBody
    public void importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("CompetitionController import");
        if (!excelFile.isEmpty()) {
            String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
            excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
        }
        List<String[]> excelData = POIUtil.readExcelFile(excelFile, 1);
        System.out.println(excelData);
        List<Competition> competitions = new ArrayList<>();
        for (String[] arr : excelData) {
            Competition competition = new Competition();
            competition.setCname(arr[0]);
            competition.setCtype(arr[1]);
            competition.setCplace(arr[2]);
            competition.setCbegintime(arr[3]);
            competitions.add(competition);
        }
        for (Competition competition : competitions) {
            if (this.competitionService.findCompetitionByCname(competition.getCname()) == null)
                this.competitionService.insertCompetition(competition);
        }
        response.sendRedirect(request.getContextPath() + "/competition/findAll");
    }

    @RequestMapping({"/findAll"})
    public String findAll(Model model) {
        System.out.println("CompetitionController findAll.");
        this.cid = "";
        this.cname = "";
        this.ctype = "";
        List<Competition> competitions = this.competitionService.findAll();
        model.addAttribute("competitionlist", competitions);
        return "competition/competition_excel";
    }

    @RequestMapping(value = {"/findByPage"}, method = {RequestMethod.GET})
    public String findCompetitionsByPage(Integer page, Model model) {
        if (page == null)
            page = Integer.valueOf(1);
        try {
            this.p.setTotal(this.competitionService.findAll().size());
            this.p.setCurrentPage(page.intValue());
            List<Competition> list = this.competitionService.findCompetitionsByPage((page.intValue() - 1) * this.p.getPageSize(), this.p.getPageSize());
            this.p.setCurrentPage(page.intValue());
            model.addAttribute("list", list);
            model.addAttribute("page", this.p);
            return "competition/competition_list";
        } catch (Exception e) {
            model.addAttribute("message", "未能获取数据");
            e.printStackTrace();
            System.out.println("findCompetitionsByPage Controller Error!");
            return "message";
        }
    }

    @RequestMapping({"/findCompetitionByEle"})
    public String findCompetitionByEle(Model model, String cid, String cname, String ctype) {
        if (cname == null) {
            cname = "";
        } else {
            model.addAttribute("cname", cname);
        }
        if (ctype == null) {
            ctype = "";
        } else {
            model.addAttribute("ctype", ctype);
        }
        this.cid = cid;
        this.cname = cname;
        this.ctype = ctype;
        System.out.println("CompetitionController findCompetitionByEle.");
        List<Competition> competitions = this.competitionService.findCompetitionByEle(cname, ctype);
        model.addAttribute("competitionlist", competitions);
        return "competition/competition_excel";
    }

    @RequestMapping({"/deleteByCid_All"})
    public void deleteByCid_All(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("CompetitionController deleteByCid.");
        this.competitionService.deleteCompetition(cid);
        response.sendRedirect(request.getContextPath() + "/competition/findAll");
    }

    @RequestMapping({"/deleteByCid_excel"})
    public void deleteByCid_excel(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("CompetitionController deleteByCid.");
        this.competitionService.deleteCompetition(cid);
        response.sendRedirect(request.getContextPath() + "/competition/findAll");
    }

    @RequestMapping({"/addCompetition"})
    public String addCompetition(Competition competition) {
        System.out.println(competition);
        this.competitionService.insertCompetition(competition);
        return "competition/competition_add";
    }

    @RequestMapping({"/updateCompetition_page"})
    public String updateCompetition_page(Model model, Integer cid) {
        System.out.println("updateCompetition_page Controller...");
        Competition competition = this.competitionService.findCompetitionByCid(cid);
        model.addAttribute("competition", competition);

        return "competition/competition_update";
    }

    @RequestMapping({"/updateCompetition"})
    public void updateCompetition(Model model, Integer cid, Competition competition, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("updateCompetition Controller...");
        if (cid != null && competition != null)
            this.competitionService.updateCompetition(cid, competition);
        response.sendRedirect(request.getContextPath() + "/competition/findAll");
    }

    @RequestMapping({"/exportForm"})
    public void exportForm(@RequestParam("checkColumn") String[] checkColumn, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (String str : checkColumn)
            System.out.println(str);
        response.sendRedirect(request.getContextPath() + "/competition/findByPage");
    }
}

