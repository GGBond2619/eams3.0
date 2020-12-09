 package com.byau.controller;
 
 import com.byau.domain.Page;
 import com.byau.domain.Student;
 import com.byau.service.IEnjoyCompetitionService;
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
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.multipart.MultipartFile;
 
 @Controller
 @RequestMapping({"/student"})
 public class StudentController {
   @Autowired
   private IStudentService studentService;
   
   @Autowired
   IEnjoyCompetitionService enjoyCompetitionService;
   
   @Autowired
   ISignService signService;
   
   private String inUpdatePage = "findByPage";
   
   private String sno = "";
   
   private String sname = "";
   
   private String sclass = "";
   
   Page p = new Page();
   
   @GetMapping({"export"})
   public void createAndDownloadExcelFile(@RequestParam("checkColumn") String[] checkColumn, HttpServletRequest request, HttpServletResponse response) throws IOException {
     List<Student> students = this.studentService.findStudentByEle(this.sno, this.sname, this.sclass);
     String checkColumnStr = "";
     for (String check : checkColumn)
       checkColumnStr = checkColumnStr + check; 
     System.out.println("checkColumnStr => " + checkColumnStr);
     List<String> attributes = new ArrayList<>();
     if (checkColumnStr.contains("sno"))
       attributes.add("学号"); 
     if (checkColumnStr.contains("sname"))
       attributes.add("姓名"); 
     if (checkColumnStr.contains("ssex"))
       attributes.add("性别"); 
     if (checkColumnStr.contains("sclass"))
       attributes.add("班级"); 
     if (checkColumnStr.contains("syear"))
       attributes.add("年级"); 
     if (checkColumnStr.contains("stel"))
       attributes.add("手机号"); 
     if (checkColumnStr.contains("sqq"))
       attributes.add("qq账号"); 
     for (String str : attributes)
       System.out.println("attributes => " + str); 
     List<List<String>> data = new ArrayList<>();
     for (Student student : students) {
       List<String> rowInfo = new ArrayList<>();
       if (checkColumnStr.contains("sno"))
         rowInfo.add(student.getSno()); 
       if (checkColumnStr.contains("sname"))
         rowInfo.add(student.getSname()); 
       if (checkColumnStr.contains("ssex"))
         rowInfo.add(student.getSsex()); 
       if (checkColumnStr.contains("sclass"))
         rowInfo.add(student.getSclass()); 
       if (checkColumnStr.contains("syear"))
         rowInfo.add(String.valueOf(student.getSyear())); 
       if (checkColumnStr.contains("stel"))
         rowInfo.add(student.getStel()); 
       if (checkColumnStr.contains("sqq"))
         rowInfo.add(student.getSqq()); 
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
     String fileName = "Student-" + (new Date()).getTime() + "." + "xls";
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
     response.sendRedirect(request.getContextPath() + "/student/findAll");
   }
   
   @PostMapping({"import"})
   @ResponseBody
   public void importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("StudentController import");
     if (!excelFile.isEmpty()) {
       String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
       excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
     } 
     List<String[]> excelData = POIUtil.readExcelFile(excelFile, 1);
     System.out.println(excelData);
     List<Student> students = new ArrayList<>();
     for (String[] arr : excelData) {
       Student student = new Student();
       student.setSno(arr[0]);
       student.setSname(arr[1]);
       student.setSsex(arr[2]);
       student.setSclass(arr[3]);
       try {
         student.setSyear(Integer.valueOf(arr[4]));
       } catch (Exception e) {
         student.setSyear(0);
       } 
       student.setStel(arr[5]);
       student.setSqq(arr[6]);
       students.add(student);
     } 
     for (Student student : students) {
       if (this.studentService.findStudentBySno(student.getSno()) == null)
         this.studentService.insertStudent(student); 
     } 
     response.sendRedirect(request.getContextPath() + "/student/findAll");
   }
   
   @RequestMapping({"/findAll"})
   public String findAll(Model model) {
     System.out.println("StudentController findAll.");
     this.sno = "";
     this.sname = "";
     this.sclass = "";
     List<Student> students = this.studentService.findAll();
     model.addAttribute("studentlist", students);
     return "student/student_excel";
   }
   
   @RequestMapping(value = {"/findByPage"}, method = {RequestMethod.GET})
   public String findStudentsByPage(Integer page, Model model) {
     if (page == null)
       page = Integer.valueOf(1); 
     try {
       this.p.setTotal(this.studentService.findAll().size());
       this.p.setCurrentPage(page.intValue());
       List<Student> list = this.studentService.findStudentsByPage((page.intValue() - 1) * this.p.getPageSize(), this.p.getPageSize());
       this.p.setCurrentPage(page.intValue());
       model.addAttribute("list", list);
       model.addAttribute("page", this.p);
       return "student/student_list";
     } catch (Exception e) {
       model.addAttribute("message", "未能获取数据");
       e.printStackTrace();
       System.out.println("findStudentsByPage Controller Error!");
       return "message";
     } 
   }
   
   @RequestMapping({"/findStudentByEle"})
   public String findStudentByEle(Model model, String sno, String sname, String sclass) {
     if (sno == null) {
       sno = "";
     } else {
       model.addAttribute("sno", sno);
     } 
     if (sname == null) {
       sname = "";
     } else {
       model.addAttribute("sname", sname);
     } 
     if (sclass == null) {
       sclass = "";
     } else {
       model.addAttribute("sclass", sclass);
     } 
     this.sno = sno;
     this.sname = sname;
     this.sclass = sclass;
     System.out.println("StudentController findStudentByEle.");
     List<Student> students = this.studentService.findStudentByEle(sno, sname, sclass);
     model.addAttribute("studentlist", students);
     return "student/student_excel";
   }
   
   @RequestMapping(value = {"/findStudentsLikeSno"}, method = {RequestMethod.GET})
   public String findStudentsLikeSno(String sno, Integer page, Model model) {
     System.out.println(sno);
     if (sno == null) {
       sno = "";
       System.out.println("sno.isEmpty");
     } 
     if (page == null)
       page = Integer.valueOf(1); 
     try {
       this.p.setTotal(this.studentService.findAll().size());
       this.p.setCurrentPage(page.intValue());
       List<Student> list = this.studentService.findStudentLikeSno(sno, (page.intValue() - 1) * this.p.getPageSize(), this.p.getPageSize());
       model.addAttribute("list", list);
       model.addAttribute("page", this.p);
       return "student/student_list";
     } catch (Exception e) {
       model.addAttribute("message", "未能获取数据");
       e.printStackTrace();
       System.out.println("findStudentsByPage Controller Error!");
       return "message";
     } 
   }
   
   @RequestMapping({"/deleteBySno_All"})
   public void deleteBySno_All(String sno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("StudentController deleteBySno.");
     this.studentService.deleteStudent(sno);
     response.sendRedirect(request.getContextPath() + "/student/findAll");
   }
   
   @RequestMapping({"/deleteBySno_page"})
   public void deleteBySno_page(String sno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("StudentController deleteBySno.");
     this.studentService.deleteStudent(sno);
     response.sendRedirect(request.getContextPath() + "/student/findByPage?page=" + this.p.getCurrentPage());
   }
   
   @RequestMapping({"/deleteBySno_excel"})
   public void deleteBySno_excel(String sno, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("StudentController deleteBySno.");
     this.studentService.deleteStudent(sno);
     response.sendRedirect(request.getContextPath() + "/student/findAll");
   }
   
   @RequestMapping({"/batchDeletes"})
   public void batchDeletes(HttpServletRequest request, HttpServletResponse response) throws IOException {
     String items = request.getParameter("delitems");
     System.out.println(items);
     String[] strs = items.split(",");
     for (int i = 0; i < strs.length; i++) {
       try {
         System.out.println("StudentController batchDeletes");
         this.studentService.deleteStudent(strs[i]);
       } catch (Exception e) {
         e.printStackTrace();
       } 
     } 
     response.sendRedirect(request.getContextPath() + "/student/findByPage");
   }
   
   @RequestMapping({"/addStudent"})
   public String addStudent(Student student) {
     System.out.println(student);
     this.studentService.insertStudent(student);
     return "student/student_add";
   }
   
   @RequestMapping({"/updateStudent_page"})
   public String updateStudent_page(Model model, String sno, String inUpdatePage) {
     System.out.println("updateStudent_page Controller...");
     Student student = this.studentService.findStudentBySno(sno);
     if (inUpdatePage != null)
       this.inUpdatePage = inUpdatePage; 
     model.addAttribute("student", student);
     return "student/student_update";
   }
   
   @RequestMapping({"/updateStudent"})
   public void updateStudent(Model model, String sno, Student student, HttpServletRequest request, HttpServletResponse response) throws IOException {
     System.out.println("updateStudent Controller...");
     if (sno != null && student != null)
       this.studentService.updateStudent(sno, student); 
     if (this.inUpdatePage.equals("excel")) {
       response.sendRedirect(request.getContextPath() + "/student/findAll");
     } else {
       response.sendRedirect(request.getContextPath() + "/student/findByPage");
     } 
   }
   
   @RequestMapping({"/exportForm"})
   public void exportForm(@RequestParam("checkColumn") String[] checkColumn, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
     for (String str : checkColumn)
       System.out.println(str); 
     response.sendRedirect(request.getContextPath() + "/student/findByPage");
   }
   
   @RequestMapping({"findCompetitions_page"})
   public String findCompetitions_page(String sno, String page, Model model) {
     Student student = this.enjoyCompetitionService.findCompetitionsBySno(sno);
     System.out.println(student);
     if (student == null) {
       model.addAttribute("msg", "该学生无参赛信息！");
     } else {
       model.addAttribute("cspage", page);
       model.addAttribute("sname", student.getSname());
       model.addAttribute("competitionlist", student.getCompetitions());
       model.addAttribute("sno", student.getSno());
     } 
     return "student/competition_excel";
   }
   
   @RequestMapping({"btnreturn"})
   public void btnreturn(String page, HttpServletRequest request, HttpServletResponse response) throws IOException {
     if (page.equals("list")) {
       response.sendRedirect(request.getContextPath() + "/student/findByPage");
     } else {
       response.sendRedirect(request.getContextPath() + "/student/findAll");
     } 
   }
   
   @RequestMapping({"findActivitys_page"})
   public String findActivitys_page(String sno, String page, Model model) {
     Student student = this.studentService.findActivityBySno(sno);
     System.out.println(student);
     if (student == null) {
       model.addAttribute("msg", "该学生无活动信息！");
     } else {
       model.addAttribute("page", page);
       model.addAttribute("sname", student.getSname());
       model.addAttribute("activitylist", student.getActivities());
       model.addAttribute("sno", student.getSno());
     } 
     return "student/activity_excel";
   }
 }


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\controller\StudentController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */