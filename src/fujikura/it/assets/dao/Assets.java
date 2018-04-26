/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Assets {

    private String sn;
    private String model;
    private String supplier;
    private String location;
    private int warranty;
    private String department;
    private String tm;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn the sn to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the warranty
     */
    public int getWarranty() {
        return warranty;
    }

    /**
     * @param warranty the warranty to set
     */
    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
    
        public String getTm() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String tm = df.format(new Date(System.currentTimeMillis()));
        System.out.println(tm);
        return tm;
    }

    /**
     * @param tm the tm to set
     */
    public void setTm(String tm) {
        this.tm = tm;
    }
}
