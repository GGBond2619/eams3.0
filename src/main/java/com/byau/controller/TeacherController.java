 package com.byau.controller;
 
 import com.byau.domain.Page;
 import com.byau.domain.Teacher;
 import com.byau.service.ITeacherService;
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
 @RequestMapping({"/teacher"})
 public class TeacherController {
   @Autowired
   private ITeacherService teacherService;
   
   private String inUpdatePage = "findByPage";
   
   private String tno = "";
   
   private String tname = "";
   
   private String tpos = "";
   
   Page p = new Page();
   
   @GetMapping({"export"})
   public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
     List<Teacher> teachers = this.teacherService.findTeacherByEle(this.tno, this.tname, this.tpos);
     String checkColumnStr = "";
     for (String check : checkColumn)
       checkColumnStr = checkColumnStr + check; 
     System.out.println("checkColumnStr => " + checkColumnStr);
     List<String> attributes = new ArrayList<>();
     if (checkColumnStr.contains("tno"))
       attributes.add("教师号"); 
     if (checkColumnStr.contains("tname"))
       attributes.add("姓名"); 
     if (checkColumnStr.contains("tpos"))
       attributes.add("职位"); 
     if (checkColumnStr.contains("ttel"))
       attributes.add("手机号"); 
     if (checkColumnStr.contains("tqq"))
       attributes.add("qq账号"); 
     for (String str : attributes)
       System.out.println("attributes => " + str); 
     List<List<String>> data = new ArrayList<>();
     for (Teacher teacher : teachers) {
       List<String> rowInfo = new ArrayList<>();
       if (checkColumnStr.contains("tno"))
         rowInfo.add(teacher.getTno()); 
       if (checkColumnStr.contains("tname"))
         rowInfo.add(teacher.getTname()); 
       if (checkColumnStr.contains("tpos"))
         rowInfo.add(teacher.getTpos()); 
       if (checkColumnStr.contains("ttel"))
         rowInfo.add(teacher.getTtel()); 
       if (checkColumnStr.contains("tqq"))
         rowInfo.add(teacher.getTqq()); 
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
     String fileName = "Teacher-" + (new Date()).getTime() + "." + "xls";
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
     response.sendRedirect(request.getContextPath() + "/teacher/findAll");
   }
   
   @PostMapping({"import"})
   @ResponseBody
   public void importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("TeacherController import");
     if (!excelFile.isEmpty()) {
       String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
       excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
     } 
     List<String[]> excelData = POIUtil.readExcelFile(excelFile, 1);
     System.out.println(excelData);
     List<Teacher> teachers = new ArrayList<>();
     for (String[] arr : excelData) {
       Teacher teacher = new Teacher();
       teacher.setTno(arr[0]);
       teacher.setTname(arr[1]);
       teacher.setTpos(arr[2]);
       teacher.setTtel(arr[3]);
       teacher.setTqq(arr[4]);
       teachers.add(teacher);
     } 
     for (Teacher teacher : teachers) {
       if (this.teacherService.findTeacherByTno(teacher.getTno()) == null)
         this.teacherService.insertTeacher(teacher); 
     } 
     response.sendRedirect(request.getContextPath() + "/teacher/findAll");
   }
   
   @RequestMapping({"/findAll"})
   public String findAll(Model model) {
     System.out.println("TeacherController findAll.");
     this.tno = "";
     this.tname = "";
     this.tpos = "";
     List<Teacher> teachers = this.teacherService.findAll();
     model.addAttribute("teacherlist", teachers);
     return "teacher/teacher_excel";
   }
   
   @RequestMapping(value = {"/findByPage"}, method = {RequestMethod.GET})
   public String findTeachersByPage(Integer page, Model model) {
     if (page == null)
       page = Integer.valueOf(1); 
     try {
       this.p.setTotal(this.teacherService.findAll().size());
       this.p.setCurrentPage(page.intValue());
       List<Teacher> list = this.teacherService.findTeachersByPage((page.intValue() - 1) * this.p.getPageSize(), this.p.getPageSize());
       this.p.setCurrentPage(page.intValue());
       model.addAttribute("list", list);
       model.addAttribute("page", this.p);
       return "teacher/teacher_list";
     } catch (Exception e) {
       model.addAttribute("message", "未能获取数据");
       e.printStackTrace();
       System.out.println("findTeachersByPage Controller Error!");
       return "message";
     } 
   }
   
   @RequestMapping({"/findTeacherByEle"})
   public String findTeacherByEle(Model model, String tno, String tname, String tpos) {
     if (tno == null) {
       tno = "";
     } else {
       model.addAttribute("tno", tno);
     } 
     if (tname == null) {
       tname = "";
     } else {
       model.addAttribute("tname", tname);
     } 
     if (tpos == null) {
       tpos = "";
     } else {
       model.addAttribute("tpos", tpos);
     } 
     this.tno = tno;
     this.tname = tname;
     this.tpos = tpos;
     System.out.println("TeacherController findTeacherByEle.");
     List<Teacher> teachers = this.teacherService.findTeacherByEle(tno, tname, tpos);
     model.addAttribute("teacherlist", teachers);
     return "teacher/teacher_excel";
   }
   
   @RequestMapping(value = {"/findTeachersLikeTno"}, method = {RequestMethod.GET})
   public String findTeachersLikeTno(String tno, Integer page, Model model) {
     System.out.println(tno);
     if (tno == null) {
       tno = "";
       System.out.println("tno.isEmpty");
     } 
     if (page == null)
       page = Integer.valueOf(1); 
     try {
       this.p.setTotal(this.teacherService.findAll().size());
       this.p.setCurrentPage(page.intValue());
       List<Teacher> list = this.teacherService.findTeacherLikeTno(tno, (page.intValue() - 1) * this.p.getPageSize(), this.p.getPageSize());
       model.addAttribute("list", list);
       model.addAttribute("page", this.p);
       return "teacher/teacher_list";
     } catch (Exception e) {
       model.addAttribute("message", "未能获取数据");
       e.printStackTrace();
       System.out.println("findTeachersByPage Controller Error!");
       return "message";
     } 
   }
   
   @RequestMapping({"/deleteByTno_All"})
   public void deleteByTno_All(String tno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("TeacherController deleteByTno.");
     this.teacherService.deleteTeacher(tno);
     response.sendRedirect(request.getContextPath() + "/teacher/findAll");
   }
   
   @RequestMapping({"/deleteByTno_page"})
   public void deleteByTno_page(String tno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("TeacherController deleteByTno.");
     this.teacherService.deleteTeacher(tno);
     response.sendRedirect(request.getContextPath() + "/teacher/findByPage?page=" + this.p.getCurrentPage());
   }
   
   @RequestMapping({"/deleteByTno_excel"})
   public void deleteByTno_excel(String tno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("TeacherController deleteByTno: tno=" + tno);
     this.teacherService.deleteTeacher(tno);
     response.sendRedirect(request.getContextPath() + "/teacher/findAll");
   }
   
   @RequestMapping({"/batchDeletes"})
   public void batchDeletes(HttpServletRequest request, HttpServletResponse response) throws IOException {
     String items = request.getParameter("delitems");
     System.out.println(items);
     String[] strs = items.split(",");
     for (int i = 0; i < strs.length; i++) {
       try {
         System.out.println("TeacherController batchDeletes");
         this.teacherService.deleteTeacher(strs[i]);
       } catch (Exception e) {
         e.printStackTrace();
       } 
     } 
     response.sendRedirect(request.getContextPath() + "/teacher/findByPage");
   }
   
   @RequestMapping({"/addTeacher"})
   public String addTeacher(Teacher teacher) {
     System.out.println(teacher);
     this.teacherService.insertTeacher(teacher);
     return "teacher/teacher_add";
   }
   
   @RequestMapping({"/updateTeacher_page"})
   public String updateTeacher_page(Model model, String tno, String inUpdatePage) {
     System.out.println("updateTeacher_page Controller...");
     Teacher teacher = this.teacherService.findTeacherByTno(tno);
     if (inUpdatePage != null)
       this.inUpdatePage = inUpdatePage; 
     model.addAttribute("teacher", teacher);
     return "teacher/teacher_update";
   }
   
   @RequestMapping({"/updateTeacher"})
   public void updateTeacher(Model model, String tno, Teacher teacher, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("updateTeacher Controller...");
     if (tno != null && teacher != null)
       this.teacherService.updateTeacher(tno, teacher); 
     if (this.inUpdatePage.equals("excel")) {
       response.sendRedirect(request.getContextPath() + "/teacher/findAll");
     } else {
       response.sendRedirect(request.getContextPath() + "/teacher/findByPage");
     } 
   }
   
   @RequestMapping({"/exportForm"})
   public void exportForm(@RequestParam("checkColumn") String[] checkColumn, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
     for (String str : checkColumn)
       System.out.println(str); 
     response.sendRedirect(request.getContextPath() + "/teacher/findByPage");
   }
 }
