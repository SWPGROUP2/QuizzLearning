package dal;

import models.Term;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TermDAO extends MyDAO {

    public List<Term> getTermsByTermSetId(int termSetId, String searchQuery) {
        List<Term> termList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM Terms WHERE termSetId = ?");

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (term LIKE ? OR definition LIKE ?)");
        }

        try ( PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            ps.setInt(paramIndex++, termSetId);

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery.trim() + "%");
                ps.setString(paramIndex++, "%" + searchQuery.trim() + "%");
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Term term = new Term();
                    term.setTermId(rs.getInt("termId"));
                    term.setTermSetId(rs.getInt("termSetId"));
                    term.setTerm(rs.getString("term"));
                    term.setDefinition(rs.getString("definition"));
                    termList.add(term);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return termList;
    }

    public void addTerm(Term term) {
        String sql = "INSERT INTO Terms (TermSetID, Term, Definition) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, term.getTermSetId());
            ps.setString(2, term.getTerm());
            ps.setString(3, term.getDefinition());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Term getTermById(int termId) {
        Term term = null;
        String query = "SELECT TermID, TermSetID, Term, Definition FROM Terms WHERE TermID = ?"; // Truy vấn tìm Term theo TermID

        try ( PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, termId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int termSetId = rs.getInt("TermSetID");
                String termName = rs.getString("Term");
                String definition = rs.getString("Definition");
                term = new Term(termId, termSetId, termName, definition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return term;
    }

    public boolean updateTerm(Term term) {
        String sql = "UPDATE terms SET term = ?, definition = ?, termSetId = ? WHERE termId = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, term.getTerm());
            ps.setString(2, term.getDefinition());
            ps.setInt(3, term.getTermSetId());
            ps.setInt(4, term.getTermId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteTerm(int termId) {
        String query = "DELETE FROM Terms WHERE TermID = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, termId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
