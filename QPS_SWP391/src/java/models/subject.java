/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class subject {
    private int subjectId;
    private String subjectName;
    private int categoryId;
    private boolean status;
    private int tagLine;
    private String title;
    private String thumbnail;
    private String description;
    private float salePrice;
    private float price;
    private Date createDate;

    public subject() {
    }

    public subject(int subjectId, String subjectName, int categoryId, boolean status, int tagLine, String title, String thumbnail, String description) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.categoryId = categoryId;
        this.status = status;
        this.tagLine = tagLine;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
    }
    
    public subject(int subjectId, String subjectName, int categoryId, boolean status, int tagLine, String title, String thumbnail, String description, float salePrice, float price) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.categoryId = categoryId;
        this.status = status;
        this.tagLine = tagLine;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.salePrice = salePrice;
        this.price = price;
    }

    public subject(int subjectId, String subjectName, int categoryId, boolean status, int tagLine, String title, String thumbnail, String description, Date createDate) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.categoryId = categoryId;
        this.status = status;
        this.tagLine = tagLine;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTagLine() {
        return tagLine;
    }

    public void setTagLine(int tagLine) {
        this.tagLine = tagLine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    

}
