/*     */ package com.byau.controller;
 
 import com.byau.service.IActivityService;
 import com.byau.service.ICompetitionService;
 import com.byau.service.IStudentService;
 import com.byau.service.ITeacherService;
 import java.io.IOException;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 @RequestMapping({"/index"})
 public class IndexController {
   @Autowired
   private IStudentService studentService;
   
   @Autowired
   private ITeacherService teacherService;
   
   @Autowired
   private ICompetitionService competitionService;
   
   @Autowired
   private IActivityService activityService;
   
   @RequestMapping({"/studentcount"})
   public void findStudentCount(Model model, HttpServletResponse response) {
     int count = this.studentService.findCount();
     try {
       response.getWriter().print(count);
     } catch (IOException e) {
       e.printStackTrace();
     } 
   }
   
   @RequestMapping({"/teachercount"})
   public void findTeacherCount(Model model, HttpServletResponse response) {
     int count = this.teacherService.findCount();
     try {
       response.getWriter().print(count);
     } catch (IOException e) {
       e.printStackTrace();
     } 
   }
   
   @RequestMapping({"/competitioncount"})
   public void findConpetitionCount(Model model, HttpServletResponse response) {
     int count = this.competitionService.findCount();
     try {
       response.getWriter().print(count);
     } catch (IOException e) {
       e.printStackTrace();
     } 
   }
   
   @RequestMapping({"/activitycount"})
   public void findActivityCount(Model model, HttpServletResponse response) {
     int count = this.activityService.findCount();
     try {
       response.getWriter().print(count);
     } catch (IOException e) {
       e.printStackTrace();
     } 
   }
 }
