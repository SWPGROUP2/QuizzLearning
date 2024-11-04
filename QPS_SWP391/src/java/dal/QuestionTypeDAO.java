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
import models.QuestionType;

/**
 *
 * @author Admin
 */
public class QuestionTypeDAO extends MyDAO{
    public List<QuestionType> getAllQuestionTypes() {
        List<QuestionType> questionTypes = new ArrayList<>();
        String query = "SELECT questionTypeId, questionTypeName FROM QuestionType"; 

        try ( PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                QuestionType questionType = new QuestionType();
                questionType.setQuestionTypeId(rs.getInt("questionTypeId"));
                questionType.setQuestionTypeName(rs.getString("questionTypeName")); 
                questionTypes.add(questionType);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return questionTypes; 
    }
}
    

