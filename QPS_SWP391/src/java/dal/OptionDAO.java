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
import models.Option;

/**
 *
 * @author Admin
 */
public class OptionDAO extends MyDAO {

    public void addOption(Option option) {
        String sql = "INSERT INTO Options (questionId, optionText, isCorrect) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, option.getQuestionID());
            ps.setString(2, option.getOptionText());
            ps.setInt(3, option.getIsCorrect());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Option> getOptionsByQuestionId(int questionId) {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT * FROM Options WHERE questionID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Option option = new Option(rs.getInt("questionID"),
                        rs.getInt("optionId"),
                        rs.getString("optionText"),
                        rs.getInt("isCorrect"));
                options.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }
    public void updateOption(Option option) {
        String sql = "UPDATE Options SET optionText = ?, isCorrect = ? WHERE optionId = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, option.getOptionText());
            ps.setInt(2, option.getIsCorrect());
            ps.setInt(3, option.getOptionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
