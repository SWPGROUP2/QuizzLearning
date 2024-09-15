package models;

public class FolderTermSets {
    private int folderId;
    private int termSetId;

    public FolderTermSets() {
    }

    public FolderTermSets(int folderId, int termSetId) {
        this.folderId = folderId;
        this.termSetId = termSetId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getTermSetId() {
        return termSetId;
    }

    public void setTermSetId(int termSetId) {
        this.termSetId = termSetId;
    }

    @Override
    public String toString() {
        return "FolderTermSets{" + "folderId=" + folderId + ", termSetId=" + termSetId + '}';
    }
    
}
