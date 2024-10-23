/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import models.Option;

/**
 *
 * @author Admin
 */
public class OptionDAO extends MyDAO {
    public void addOption(Option option) {
        String sql = "INSERT INTO Options (questionId, optionText, isCorrect) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, option.getQuestionID());
            ps.setString(2, option.getOptionText());
            ps.setInt(3, option.getIsCorrect());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
