package dal;

import models.Term;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TermDAO extends MyDAO {
    
    public List<Term> getAllTerms() {
        List<Term> termList = new ArrayList<>();
        String sql = "SELECT * FROM Terms"; // Assuming your table name is 'Terms'

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Term term = new Term();
                term.setTermId(rs.getInt("termId"));
                term.setTermSetId(rs.getInt("termSetId"));
                term.setTerm(rs.getString("term"));
                term.setDefinition(rs.getString("definition"));
                termList.add(term);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return termList;
    }
}
