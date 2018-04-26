/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.dao;

public class ExcelConstructor {
    private String sn; // +
    private String sup; // +
    private String loc; // +
    private int warranty; // +
    private String tm; // +
    private String id_1CId; // +
    private String mod; // +
    private String dept; // +

    public ExcelConstructor() {}

    public ExcelConstructor(String sn, String sup, String loc, int warranty, String tm, String id_1CId, String mod, String dept) {
        this.sn = sn;
        this.sup = sup;
        this.loc = loc;
        this.warranty = warranty;
        this.tm = tm;
        this.id_1CId = id_1CId;
        this.mod = mod;
        this.dept = dept;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getId_1CId() {
        return id_1CId;
    }

    public void setId_1CId(String id_1CId) {
        this.id_1CId = id_1CId;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "ExcelConstructor{" + "sn=" + sn + ", sup=" + sup + ", loc=" + loc + ", warranty=" + warranty + ", tm=" + tm + ", id_1CId=" + id_1CId + ", mod=" + mod + ", dept=" + dept + '}';
    }
    
    
    
    
}
