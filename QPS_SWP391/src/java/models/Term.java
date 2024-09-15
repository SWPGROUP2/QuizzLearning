package models;

public class Term {

    private int termId;
    private int termSetId;
    private String term;
    private String definition;

    public Term() {
    }

    public Term(int termId, int termSetId, String term, String definition) {
        this.termId = termId;
        this.termSetId = termSetId;
        this.term = term;
        this.definition = definition;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getTermSetId() {
        return termSetId;
    }

    public void setTermSetId(int termSetId) {
        this.termSetId = termSetId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Term{" + "termId=" + termId + ", termSetId=" + termSetId + ", term=" + term + ", definition=" + definition + '}';
    }
    
    
}
