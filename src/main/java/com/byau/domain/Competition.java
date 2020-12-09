package com.byau.domain;
 
 import com.byau.domain.Student;
 import java.io.Serializable;
 import java.util.List;
 
 public class Competition implements Serializable {
   private Integer cid;
   
   private String cname;
   
   private String ctype;
   
   private String cbegintime;
   
   private String cplace;
   
   private List<Student> students;

   public Competition() {
   }

   public Competition(Integer cid, String cname, String ctype, String cbegintime, String cplace) {
     this.cid = cid;
     this.cname = cname;
     this.ctype = ctype;
     this.cbegintime = cbegintime;
     this.cplace = cplace;
   }

   public Competition(Integer cid, String cname, String ctype, String cbegintime, String cplace, List<Student> students) {
     this.cid = cid;
     this.cname = cname;
     this.ctype = ctype;
     this.cbegintime = cbegintime;
     this.cplace = cplace;
     this.students = students;
   }

   public void setCid(Integer cid) {
     this.cid = cid;
   }
   
   public List<Student> getStudents() {
     return this.students;
   }
   
   public void setStudents(List<Student> students) {
     this.students = students;
   }
   
   public Integer getCid() {
     return this.cid;
   }
   
   public void setCid(int cid) {
     this.cid = Integer.valueOf(cid);
   }
   
   public String getCname() {
     return this.cname;
   }
   
   public void setCname(String cname) {
     this.cname = cname;
   }
   
   public String getCtype() {
     return this.ctype;
   }
   
   public void setCtype(String ctype) {
     this.ctype = ctype;
   }
   
   public String getCbegintime() {
     return this.cbegintime;
   }
   
   public void setCbegintime(String cbegintime) {
     this.cbegintime = cbegintime;
   }
   
   public String getCplace() {
     return this.cplace;
   }
   
   public void setCplace(String cplace) {
     this.cplace = cplace;
   }
   
   public String toString() {
     return "Competition{cid=" + this.cid + ", cname='" + this.cname + '\'' + ", ctype='" + this.ctype + '\'' + ", cbegintime='" + this.cbegintime + '\'' + ", cplace='" + this.cplace + '\'' + '}';
   }
 }
