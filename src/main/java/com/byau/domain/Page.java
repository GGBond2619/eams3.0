package com.byau.domain;

import java.io.Serializable;

public class Page implements Serializable {
   private int currentPage = 1;
   
   private int totalPages;
   
   private int total;
   
   private int pageSize = 10;
   
   private int nextPage;
   
   private int prefPage;

    public Page() {
    }

    public Page(int currentPage, int totalPages, int total, int pageSize, int nextPage, int prefPage) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.total = total;
        this.pageSize = pageSize;
        this.nextPage = nextPage;
        this.prefPage = prefPage;
    }

    public int getCurrentPage() {
     return this.currentPage;
   }
   
   public void setCurrentPage(int currentPage) {
     this.currentPage = currentPage;
   }
   
   public int getTotalPages() {
     this.totalPages = (this.total % this.pageSize == 0) ? (this.total / this.pageSize) : (this.total / this.pageSize + 1);
     return this.totalPages;
   }
   
   public int getTotal() {
     return this.total;
   }
   
   public void setTotal(int total) {
     this.total = total;
   }
   
   public int getPageSize() {
     return this.pageSize;
   }
   
   public void setPageSize(int pageSize) {
     this.pageSize = pageSize;
   }
   
   public int getNextPage() {
     if (this.currentPage < this.totalPages) {
       this.nextPage = this.currentPage + 1;
     } else {
       this.nextPage = this.currentPage;
     } 
     return this.nextPage;
   }
   
   public int getPrefPage() {
     if (this.currentPage > 1) {
       this.prefPage = this.currentPage - 1;
     } else {
       this.prefPage = this.currentPage;
     } 
     return this.prefPage;
   }
 }
