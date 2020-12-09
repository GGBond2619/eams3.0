package com.byau.domain;
 
 import com.byau.domain.Activity;
 import com.byau.domain.Competition;
 import java.io.Serializable;
 import java.util.List;
 
 public class Student implements Serializable {
   private String sno;
   
   private String sname;
   
   private String stel;
   
   private String ssex;
   
   private String sqq;
   
   private Integer syear;
   
   private String sclass;
   
   private List<Competition> competitions;
   
   private List<Activity> activities;
   
   public List<Activity> getActivities() {
     return this.activities;
   }
   
   public void setActivities(List<Activity> activities) {
     this.activities = activities;
   }
   
   public int getSyear() {
     return this.syear.intValue();
   }
   
   public void setSyear(int syear) {
     this.syear = Integer.valueOf(syear);
   }
   
   public void setSyear(Integer syear) {
     this.syear = syear;
   }
   
   public List<Competition> getCompetitions() {
     return this.competitions;
   }
   
   public void setCompetitions(List<Competition> competitions) {
     this.competitions = competitions;
   }
   
   public String getSclass() {
     return this.sclass;
   }
   
   public void setSclass(String sclass) {
     this.sclass = sclass;
   }
   
   public String getSno() {
     return this.sno;
   }
   
   public void setSno(String sno) {
     this.sno = sno;
   }
   
   public String getSname() {
     return this.sname;
   }
   
   public void setSname(String sname) {
     this.sname = sname;
   }
   
   public String getStel() {
     return this.stel;
   }
   
   public void setStel(String stel) {
     this.stel = stel;
   }
   
   public String getSsex() {
     return this.ssex;
   }
   
   public void setSsex(String ssex) {
     this.ssex = ssex;
   }
   
   public String getSqq() {
     return this.sqq;
   }
   
   public void setSqq(String sqq) {
     this.sqq = sqq;
   }

   public Student() {
   }

   public Student(String sno, String sname, String stel, String ssex, String sqq, Integer syear, String sclass) {
     this.sno = sno;
     this.sname = sname;
     this.stel = stel;
     this.ssex = ssex;
     this.sqq = sqq;
     this.syear = syear;
     this.sclass = sclass;
   }

   public Student(String sno, String sname, String stel, String ssex, String sqq, Integer syear, String sclass, List<Competition> competitions, List<Activity> activities) {
     this.sno = sno;
     this.sname = sname;
     this.stel = stel;
     this.ssex = ssex;
     this.sqq = sqq;
     this.syear = syear;
     this.sclass = sclass;
     this.competitions = competitions;
     this.activities = activities;
   }

   public String toString() {
     return "Student{sno='" + this.sno + '\'' + ", sname='" + this.sname + '\'' + ", stel='" + this.stel + '\'' + ", ssex='" + this.ssex + '\'' + ", sqq='" + this.sqq + '\'' + ", syear=" + this.syear + ", sclass='" + this.sclass + '\'' + '}';
   }
 }
