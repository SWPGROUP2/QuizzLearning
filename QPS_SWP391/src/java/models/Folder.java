/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author admin
 */
public class Folder {
   
    private int folderID;
    private int userID;
    private String folderSetName;
    private List<FolderTermSets> termSetId;

    // Constructors
    public Folder() {
    }

    public Folder(int folderID, int userID, String folderSetName) {
        this.folderID = folderID;
        this.userID = userID;
        this.folderSetName = folderSetName;
    }

    public Folder(int folderID, int userID, String folderSetName, List<FolderTermSets> termSetId) {
        this.folderID = folderID;
        this.userID = userID;
        this.folderSetName = folderSetName;
        this.termSetId = termSetId;
    }
    
  

    // Getters and Setters
    public int getFolderID() {
        return folderID;
    }

    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFolderSetName() {
        return folderSetName;
    }

    public void setFolderSetName(String folderSetName) {
        this.folderSetName = folderSetName;
    }

    public List<FolderTermSets> getTermSetId() {
        return termSetId;
    }

    public void setTermSetId(List<FolderTermSets> termSetId) {
        this.termSetId = termSetId;
    }

    
    
    

    @Override
    public String toString() {
        return "Folder{" + "folderID=" + folderID + ", userID=" + userID + ", folderSetName=" + folderSetName + '}';
    }
    
    
}


