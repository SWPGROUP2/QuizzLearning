package models;

import java.sql.Timestamp;

public class TermSet {
    private int termSetId;
    private String termSetName;
    private String termSetDescription;

    public TermSet() {}

    public TermSet(int termSetId, String termSetName, String termSetDescription) {
        this.termSetId = termSetId;
        this.termSetName = termSetName;
        this.termSetDescription = termSetDescription;
    }

    public int getTermSetId() {
        return termSetId;
    }

    public void setTermSetId(int termSetId) {
        this.termSetId = termSetId;
    }

    public String getTermSetName() {
        return termSetName;
    }

    public void setTermSetName(String termSetName) {
        this.termSetName = termSetName;
    }

    public String getTermSetDescription() {
        return termSetDescription;
    }

    public void setTermSetDescription(String termSetDescription) {
        this.termSetDescription = termSetDescription;
    }

}
