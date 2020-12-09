package com.byau.domain;

import java.io.Serializable;

public class Admin implements Serializable {
    private String sno;

    private byte chairman;

    public Admin() {
    }

    public Admin(String sno, byte chairman) {
        this.sno = sno;
        this.chairman = chairman;
    }

    public String getSno() {
        return this.sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public byte getChairman() {
        return this.chairman;
    }

    public void setChairman(byte chairman) {
        this.chairman = chairman;
    }

    public String toString() {
        return "Admin{sno='" + this.sno + '\'' + ", chairman=" + this.chairman + '}';
    }
}
