package com.example.project1.model;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

public class ThanhToan  {
    int id;
    String ten;
    int sdt;
    String gmail;
    String ngay;
    String diachi;

    public ThanhToan(int id, String ten, int sdt, String gmail, String ngay, String diachi) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.gmail = gmail;
        this.ngay = ngay;
        this.diachi = diachi;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
