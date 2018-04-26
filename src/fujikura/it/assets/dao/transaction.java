/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class transaction {
    private String user;
    private String sn;
    private String action;
    private int id_action;
    private String oneC_id;
    private String tm;
    //
    //
    private String loc;
    private String factory;
    private String dept;
    private String sup;
    private String comment;
    


    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
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
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the id_action
     */
    public int getId_action() {
        return id_action;
    }

    /**
     * @param id_action the id_action to set
     */
    public void setId_action(int id_action) {
        this.id_action = id_action;
    }

    public String getOneC_id() {
        return oneC_id;
    }

    public void setOneC_id(String oneC_id) {
        this.oneC_id = oneC_id;
    }

    //
    //
    //

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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