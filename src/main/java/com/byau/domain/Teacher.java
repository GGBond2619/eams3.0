package com.byau.domain;
 
 import java.io.Serializable;
 
 public class Teacher implements Serializable {
   private String tno;
   
   private String tpos;
   
   private String tname;
   
   private String ttel;
   
   private String tqq;

   public Teacher() {
   }

   public Teacher(String tno, String tpos, String tname, String ttel, String tqq) {
     this.tno = tno;
     this.tpos = tpos;
     this.tname = tname;
     this.ttel = ttel;
     this.tqq = tqq;
   }

   public String getTno() {
     return this.tno;
   }
   
   public void setTno(String tno) {
     this.tno = tno;
   }
   
   public String getTpos() {
     return this.tpos;
   }
   
   public void setTpos(String tpos) {
     this.tpos = tpos;
   }
   
   public String getTname() {
     return this.tname;
   }
   
   public void setTname(String tname) {
     this.tname = tname;
   }
   
   public String getTtel() {
     return this.ttel;
   }
   
   public void setTtel(String ttel) {
     this.ttel = ttel;
   }
   
   public String getTqq() {
     return this.tqq;
   }
   
   public void setTqq(String tqq) {
     this.tqq = tqq;
   }
   
   public String toString() {
     return "teacher{tno='" + this.tno + '\'' + ", tpos='" + this.tpos + '\'' + ", tname='" + this.tname + '\'' + ", ttel='" + this.ttel + '\'' + ", tqq='" + this.tqq + '\'' + '}';
   }
 }
