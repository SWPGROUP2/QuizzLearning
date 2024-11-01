package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Term;

public class TermDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(TermDAO.class.getName());

    public List<Term> getTermsByTermSetId(int termSetId) {
        List<Term> terms = new ArrayList<>();
        String query = "SELECT TermID, TermSetID, Term, Definition "
                + "FROM Terms "
                + "WHERE TermSetID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, termSetId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Term term = new Term();
                    term.setTermId(rs.getInt("TermID"));
                    term.setTermSetId(rs.getInt("TermSetID"));
                    term.setTerm(rs.getString("Term"));
                    term.setDefinition(rs.getString("Definition"));
                    terms.add(term);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error executing query", ex);
        }
        return terms;
    }

    public Term getTermById(int id) {
        Term term = null;
        String query = "SELECT TermID, TermSetID, Term, Definition "
                + "FROM Terms "
                + "WHERE TermID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    term = new Term();
                    term.setTermId(rs.getInt("TermID"));
                    term.setTermSetId(rs.getInt("TermSetID"));
                    term.setTerm(rs.getString("Term"));
                    term.setDefinition(rs.getString("Definition"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error executing query", ex);
        }
        return term;
    }
}