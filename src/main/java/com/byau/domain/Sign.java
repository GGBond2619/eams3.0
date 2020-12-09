package com.byau.domain;

import java.io.Serializable;

public class Sign implements Serializable {
    private Integer sid;

    private String sno;

    private Integer ano;

    public Sign(Integer sid, String sno, Integer ano) {
        this.sid = sid;
        this.sno = sno;
        this.ano = ano;
    }

    public Sign() {
    }

    public Integer getSid() {
        return this.sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSno() {
        return this.sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Integer getAno() {
        return this.ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
