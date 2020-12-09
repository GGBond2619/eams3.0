package com.byau.domain;
 
 import java.io.Serializable;
 
 public class Device implements Serializable {
   private String dno;
   
   private String dname;
   
   private String dtype;
   
   private Integer dcount;

   public Device() {
   }

   public Device(String dno, String dname, String dtype, Integer dcount) {
     this.dno = dno;
     this.dname = dname;
     this.dtype = dtype;
     this.dcount = dcount;
   }

   public String getDno() {
     return this.dno;
   }
   
   public void setDno(String dno) {
     this.dno = dno;
   }
   
   public String getDname() {
     return this.dname;
   }
   
   public void setDname(String dname) {
     this.dname = dname;
   }
   
   public String getDtype() {
     return this.dtype;
   }
   
   public void setDtype(String dtype) {
     this.dtype = dtype;
   }
   
   public Integer getDcount() {
     return this.dcount;
   }
   
   public void setDcount(Integer dcount) {
     this.dcount = dcount;
   }
   
   public String toString() {
     return "Device{dno='" + this.dno + '\'' + ", dname='" + this.dname + '\'' + ", dtype='" + this.dtype + '\'' + ", dcount=" + this.dcount + '}';
   }
 }
