/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Admin
 */
public class DiemSV {
    private String hoten,masv;
    private float TiengAnh,TinHoc,GDTC;

    public DiemSV() {
    }

    public DiemSV(String hoten, String masv, float TiengAnh, float TinHoc, float GDTC) {
        this.hoten = hoten;
        this.masv = masv;
        this.TiengAnh = TiengAnh;
        this.TinHoc = TinHoc;
        this.GDTC = GDTC;
    }

    public DiemSV(float TiengAnh, float TinHoc, float GDTC) {
        this.TiengAnh = TiengAnh;
        this.TinHoc = TinHoc;
        this.GDTC = GDTC;
    }
    
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public float getTiengAnh() {
        return TiengAnh;
    }

    public void setTiengAnh(float TiengAnh) {
        this.TiengAnh = TiengAnh;
    }

    public float getTinHoc() {
        return TinHoc;
    }

    public void setTinHoc(float TinHoc) {
        this.TinHoc = TinHoc;
    }

    public float getGDTC() {
        return GDTC;
    }

    public void setGDTC(float GDTC) {
        this.GDTC = GDTC;
    }
    public float getDiemTB(){
        return (TiengAnh+TinHoc+GDTC)/3;
    }
}
