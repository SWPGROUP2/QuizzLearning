/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TeacherSubjectsDAO extends MyDAO {

    public List<Integer> getAssignedSubjectIdsByTeacherId(int teacherId) {
        List<Integer> subjectIds = new ArrayList<>();
        String query = "SELECT subjectId FROM TeacherSubjects WHERE userId = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subjectIds.add(rs.getInt("subjectId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectIds;
    }

}