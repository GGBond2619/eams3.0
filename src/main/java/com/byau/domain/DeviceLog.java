package com.byau.domain;
 
 import java.io.Serializable;
 
 public class DeviceLog implements Serializable {
   private Integer dlno;
   
   private String dno;
   
   private Integer dlcount;
   
   private Integer dltype;
   
   private String dltime;
   
   private String dlog;
   
   private String dname;

   public DeviceLog() {
   }

   public DeviceLog(Integer dlno, String dno, Integer dlcount, Integer dltype, String dltime, String dlog, String dname) {
     this.dlno = dlno;
     this.dno = dno;
     this.dlcount = dlcount;
     this.dltype = dltype;
     this.dltime = dltime;
     this.dlog = dlog;
     this.dname = dname;
   }

   public String getDname() {
     return this.dname;
   }
   
   public void setDname(String dname) {
     this.dname = dname;
   }
   
   public Integer getDlno() {
     return this.dlno;
   }
   
   public void setDlno(Integer dlno) {
     this.dlno = dlno;
   }
   
   public String getDno() {
     return this.dno;
   }
   
   public void setDno(String dno) {
     this.dno = dno;
   }
   
   public Integer getDlcount() {
     return this.dlcount;
   }
   
   public void setDlcount(Integer dlcount) {
     this.dlcount = dlcount;
   }
   
   public Integer getDltype() {
     return this.dltype;
   }
   
   public void setDltype(Integer dltype) {
     this.dltype = dltype;
   }
   
   public String getDltime() {
     return this.dltime;
   }
   
   public void setDltime(String dltime) {
     this.dltime = dltime;
   }
   
   public String getDlog() {
     return this.dlog;
   }
   
   public void setDlog(String dlog) {
     this.dlog = dlog;
   }
   
   public String toString() {
     return "DeviceLog{dlno=" + this.dlno + ", dno='" + this.dno + '\'' + ", dlcount=" + this.dlcount + ", dltype=" + this.dltype + ", dltime='" + this.dltime + '\'' + ", dlog='" + this.dlog + '\'' + ", dname='" + this.dname + '\'' + '}';
   }
 }
