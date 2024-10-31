package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.TermSet;

public class TermSetDAO extends MyDAO {
   public List<TermSet> getAllTermSets() {
        List<TermSet> termSets = new ArrayList<>();
        String sql = "SELECT TermSetID, TermSetName, TermSetDescription FROM TermSets";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TermSet termSet = new TermSet();
                termSet.setTermSetId(rs.getInt("TermSetID"));
                termSet.setTermSetName(rs.getString("TermSetName"));
                termSet.setTermSetDescription(rs.getString("TermSetDescription"));   
                termSets.add(termSet);
            }
              System.out.println("Number of term sets fetched: " + termSets.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return termSets;
    }
   public int getTotalTermsCount(int termSetId) {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM Terms WHERE TermSetID = ?"; // Adjust according to your table structure
    
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, termSetId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return count;
}
   
  public static void main(String[] args) {
        TermSetDAO termSetDAO = new TermSetDAO();
        List<TermSet> termSets = termSetDAO.getAllTermSets();

        for (TermSet ts : termSets) {
            System.out.println("Term Set ID: " + ts.getTermSetId() +
                               ", Name: " + ts.getTermSetName() +
                               ", Description: " + ts.getTermSetDescription());
        }
    }

}
