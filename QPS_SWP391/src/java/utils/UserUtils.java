
package utils;

public class UserUtils {
    public static String getRoleName(int id) {
        switch(id) {
            case 1:
                return "Student";
            case 2:
                return "Admin";
            case 3:
                return "Teacher";
            default:
                return "Unknown";
        }
    }
}
