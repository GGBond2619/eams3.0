package com.byau.domain;
 
 import com.byau.domain.Competition;
 import com.byau.domain.Student;
 import java.io.Serializable;
 import java.util.List;
 
 public class EnjoyCompetition implements Serializable {
   private Integer eid;
   
   private Integer cid;
   
   private String sno;
   
   private List<Student> students;
   
   private String cname;
   
   private List<Competition> competitions;
   
   public List<Competition> getCompetitions() {
     return this.competitions;
   }

   public EnjoyCompetition() {
   }

   public EnjoyCompetition(Integer eid, Integer cid, String sno, String cname) {
     this.eid = eid;
     this.cid = cid;
     this.sno = sno;
     this.cname = cname;
   }

   public EnjoyCompetition(Integer eid, Integer cid, String sno, List<Student> students, String cname, List<Competition> competitions) {
     this.eid = eid;
     this.cid = cid;
     this.sno = sno;
     this.students = students;
     this.cname = cname;
     this.competitions = competitions;
   }

   public void setCompetitions(List<Competition> competitions) {
     this.competitions = competitions;
   }
   
   public String getSno() {
     return this.sno;
   }
   
   public void setSno(String sno) {
     this.sno = sno;
   }
   
   public String getCname() {
     return this.cname;
   }
   
   public void setCname(String cname) {
     this.cname = cname;
   }
   
   public List<Student> getStudents() {
     return this.students;
   }
   
   public void setStudents(List<Student> students) {
     this.students = students;
   }
   
   public Integer getEid() {
     return this.eid;
   }
   
   public void setEid(Integer eid) {
     this.eid = eid;
   }
   
   public Integer getCid() {
     return this.cid;
   }
   
   public void setCid(Integer cid) {
     this.cid = cid;
   }
   
   public String toString() {
     return "EnjoyCompetition{eid=" + this.eid + ", cid=" + this.cid + ", sno='" + this.sno + '\'' + ", students=" + this.students + ", cname='" + this.cname + '\'' + ", competitions=" + this.competitions + '}';
   }
 }
