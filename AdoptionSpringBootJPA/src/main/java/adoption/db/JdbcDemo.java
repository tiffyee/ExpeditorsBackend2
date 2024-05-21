package adoption.db;

import adoption.domain.Adopter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDemo {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/adoptapp";
        String user = "larku";
        String pw = "larku";

        try (Connection connection = DriverManager.getConnection(url, user, pw)){
            addAdopter(connection);
            dumpAdopters(connection);
            addPet(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addAdopter(Connection connection){
        String sql = "insert into adopter (name, phoneNumber) values (?, ?)";

        List<Integer> newKeys = new ArrayList<>();
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,"Bob");
            ps.setString(2, "111-111-1111");

           rowsAffected += ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys();) {
                keys.next();
                var newId = keys.getInt(1);
                newKeys.add(newId);
            }
            System.out.println("rowsAffected: " + rowsAffected + ",newKeys: " + newKeys);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPet(Connection connection){
        String sql = "insert into pet (id, name, type_id, breed) values (?, ?)";

        List<Integer> newKeys = new ArrayList<>();
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,5);
            ps.setString(2,"Fluffy");
            ps.setInt(3, 1);
            ps.setString(4, "Labradoodle");


            rowsAffected += ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys();) {
                keys.next();
                var newId = keys.getInt(1);
                newKeys.add(newId);
            }
            System.out.println("rowsAffected: " + rowsAffected + ",newKeys: " + newKeys);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dumpAdopters(Connection connection){
        String sql = "select * from adopter";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            List<Adopter> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phonenumber = resultSet.getString("phonenumber");

                var newObj = new Adopter(name, phonenumber);
                newObj.setId(id);
                result.add(newObj);
            }

            System.out.println("num obj: " + result.size());
            result.forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
