package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.TermSet;

public class TermSetDAO extends MyDAO {

    public List<TermSet> getAllTermSetsByUser(int userId, String searchQuery) {
        List<TermSet> termSets = new ArrayList<>();

        String sql = "SELECT TermSetID, TermSetName, TermSetDescription FROM TermSets WHERE CreatedBy = ?";

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql += " AND (TermSetName LIKE ? OR TermSetDescription LIKE ?)";
        }

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String likeSearch = "%" + searchQuery + "%";
                ps.setString(2, likeSearch);
                ps.setString(3, likeSearch);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TermSet termSet = new TermSet();
                termSet.setTermSetId(rs.getInt("TermSetID"));
                termSet.setTermSetName(rs.getString("TermSetName"));
                termSet.setTermSetDescription(rs.getString("TermSetDescription"));
                termSets.add(termSet);
            }

            System.out.println("Number of term sets fetched for user " + userId + ": " + termSets.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return termSets;
    }

    public void addTermSet(String termSetName, String termSetDescription, int userId) {
        String sql = "INSERT INTO TermSets (TermSetName, TermSetDescription, CreatedBy) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, termSetName);
            ps.setString(2, termSetDescription);
            ps.setInt(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTermSet(int termSetId) {
        String sql = "DELETE FROM TermSets WHERE TermSetID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, termSetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTermSet(TermSet termSet) {
        String sql = "UPDATE TermSets SET TermSetName = ?, TermSetDescription = ? WHERE TermSetID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, termSet.getTermSetName());
            ps.setString(2, termSet.getTermSetDescription());
            ps.setInt(3, termSet.getTermSetId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TermSet getTermSetById(int termSetId) {
        TermSet termSet = null;
        String sql = "SELECT * FROM TermSets WHERE TermSetID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, termSetId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng TermSet từ dữ liệu trong ResultSet
                termSet = new TermSet(
                        rs.getInt("TermSetID"),
                        rs.getString("TermSetName"),
                        rs.getString("TermSetDescription")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return termSet;
    }

    public int getTotalTermsCount(int termSetId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Terms WHERE TermSetID = ?"; // Adjust according to your table structure

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
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

}
