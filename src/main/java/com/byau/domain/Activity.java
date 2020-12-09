package com.byau.domain;
 
 import com.byau.domain.Student;

 import java.io.Serializable;
 import java.util.List;
 
 public class Activity implements Serializable {
   private Integer ano;
   
   private String aname;
   
   private String begintime;
   
   private String endtime;
   
   private String site;
   
   private String master;
   
   private String aim;
   
   private String ask;
   
   private List<Student> students;

   public Activity() {
   }

   public Activity(Integer ano, String aname, String begintime, String endtime, String site, String master, String aim, String ask) {
     this.ano = ano;
     this.aname = aname;
     this.begintime = begintime;
     this.endtime = endtime;
     this.site = site;
     this.master = master;
     this.aim = aim;
     this.ask = ask;
   }

   public Activity(Integer ano, String aname, String begintime, String endtime, String site, String master, String aim, String ask, List<Student> students) {
     this.ano = ano;
     this.aname = aname;
     this.begintime = begintime;
     this.endtime = endtime;
     this.site = site;
     this.master = master;
     this.aim = aim;
     this.ask = ask;
     this.students = students;
   }

   public List<Student> getStudents() {
     return this.students;
   }
   
   public void setStudents(List<Student> students) {
     this.students = students;
   }
   
   public String getAname() {
     return this.aname;
   }
   
   public void setAname(String aname) {
     this.aname = aname;
   }
   
   public Integer getAno() {
     return this.ano;
   }
   
   public void setAno(Integer ano) {
     this.ano = ano;
   }
   
   public String getBegintime() {
     return this.begintime;
   }
   
   public void setBegintime(String begintime) {
     this.begintime = begintime;
   }
   
   public String getEndtime() {
     return this.endtime;
   }
   
   public void setEndtime(String endtime) {
     this.endtime = endtime;
   }
   
   public String getSite() {
     return this.site;
   }
   
   public void setSite(String site) {
     this.site = site;
   }
   
   public String getMaster() {
     return this.master;
   }
   
   public void setMaster(String master) {
     this.master = master;
   }
   
   public String getAim() {
     return this.aim;
   }
   
   public void setAim(String aim) {
     this.aim = aim;
   }
   
   public String getAsk() {
     return this.ask;
   }
   
   public void setAsk(String ask) {
     this.ask = ask;
   }
 }

