import java.sql.*;

public class CrudOperations {
    static CreateConnection createConn;

    public static void main(String[] args) {
        createConn = new CreateConnection();
        performCreate("12.x","Test","Insercion de prueba");
        performRead();
        System.out.println("-- Final State --");
        performRead();
    }

    private static void performCreate(String recipeNumber, String title, String description) {
        String sql = "INSERT INTO RECIPES VALUES(" +
                "null, ?,?,?)";

        /**
         * Cambiamos los Statement objects por PreparedStament objects
         * Statement stmt = conn.createStatement();) {
         */
        try (Connection conn = createConn.getConnection();) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, recipeNumber);
                pstmt.setString(2, title);
                pstmt.setString(3, description);
                pstmt.executeUpdate();
                int result = pstmt.executeUpdate(sql);
                // Returns row-count or 0 if not successful
                if (result == 1) {
                    System.out.println("-- Record created --");
                } else {
                    System.err.println("!! Record NOT Created !!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void performRead() {
        String qry = "select recipe_number, recipe_name, description from apressbooks.recipes";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(qry);
            while (rs.next()) {
                String recipe = rs.getString("RECIPE_NUMBER");
                String name = rs.getString("RECIPE_NAME");
                String desc = rs.getString("DESCRIPTION");
                System.out.println(recipe + "\t" + name + "\t" + desc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performUpdate() {
        String sql = "UPDATE RECIPES " +
                "SET RECIPE_NUMBER = '12-5' " +
                "WHERE RECIPE_NUMBER = '12-4'";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            int result = stmt.executeUpdate(sql);
            if (result == 1) {
                System.out.println("-- Record Updated --");
            } else {
                System.out.println("!! Record NOT Updated !!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performDelete(String recipeNumber) {
        //TO-DO Preguntar si conviene dejar asi el delete o si deberiamos usar un PreparedStament
        String sql = "DELETE FROM RECIPES WHERE RECIPE_NUMBER = '" + recipeNumber + "'";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            int result = stmt.executeUpdate(sql);
            if (result > 0) {
                System.out.println("-- Record Deleted --");
            } else {
                System.out.println("!! Record NOT Deleted!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
