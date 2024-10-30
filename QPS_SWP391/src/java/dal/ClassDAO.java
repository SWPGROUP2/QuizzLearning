/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import models.Classes;

/**
 *
 * @author Admin
 */
public class ClassDAO extends MyDAO{
    public List<Classes> getAllClasses() {
    String sql = "SELECT * FROM Class";
    List<Classes> classList = new ArrayList<>();

    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            int classId = rs.getInt("ClassID");
            String className = rs.getString("ClassName");
            int userId = rs.getInt("UserID");

            classList.add(new Classes(classId,className,userId));
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    return classList;
}

    
}
