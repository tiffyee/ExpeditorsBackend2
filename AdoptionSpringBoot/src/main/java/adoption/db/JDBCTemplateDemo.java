package adoption.db;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDate;
import java.util.List;

public class JDBCTemplateDemo {

    public static void main(String[] args) {
        JDBCTemplateDemo jtd = new JDBCTemplateDemo();
        String url = "jdbc:postgresql://localhost:5433/adoptapp";
        String user = "larku";
        String pw = "larku";

        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

        JdbcTemplate template = new JdbcTemplate(dataSource);
//        jtd.getSimpleWithOneColumn(template);
//        jtd.getWholeObjectButOnlyAdopter(template);
//        jtd.getWholeObjectWithRelationShip(template);
        jtd.addAdopters(template);
    }

    public void getSimpleWithOneColumn(JdbcTemplate template){
        String sql = "select name from adopter where id = ?";

        String name = template.queryForObject(sql,String.class,1);
        System.out.println("name: " + name);
    }

    public void getWholeObjectButOnlyAdopter(JdbcTemplate template) {
//      String sql = "select * from adopter where id = ?";
        String sql = "select * from adopter";

        RowMapper<Adopter> rowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phonenumber = resultSet.getString("phonenumber");

            var newObj = new Adopter(name, phonenumber);
            newObj.setId(id);
            return newObj;
        };

        //Adopter adopter = template.queryForObject(sql, rowMapper, 1);
        List<Adopter> adopters = template.query(sql, rowMapper);

        //System.out.println("Adopter: " + adopter);
        adopters.forEach(System.out::println);

    }

    public void getWholeObjectWithRelationShip(JdbcTemplate template) {
//        String sql = "select *, a.name as adopterName, p.name as petName from adopted_pet ap " +
//                "left join adopter a on ap.adopter_id = a.id " +
//                "left join pet p on ap.pet_id = p.id " +
//                "left join pet_type pt on p.type_id = pt.type_id ";
//
        String sql = """
                select *, a.name as adopterName, p.name as petName from adopted_pet ap
                    left join adopter a on ap.adopter_id = a.id
                    left join pet p on ap.pet_id = p.id
                """;

        RowMapper<Adopter> rowMapper = (resultSet, rowNum) -> {
            //int id = resultSet.getInt("ap.id");
            String adopterName = resultSet.getString("adopterName");
            LocalDate adoptDate = resultSet.getObject("adoptiondate", LocalDate.class);
            String phoneNumber = resultSet.getString("phonenumber");
            String petName = resultSet.getString("petName");
            Pet.PetType petType = Pet.PetType.valueOf(resultSet.getString("type"));
            String breed = resultSet.getString("breed");

            Adopter adopter = new Adopter(adopterName, phoneNumber, adoptDate, Pet.builder(petType).name(petName).breed(breed).build());

            return adopter;
        };

        List<Adopter> adopters = template.query(sql, rowMapper);

        adopters.forEach(System.out::println);
    }

    public void addAdopters(JdbcTemplate template){
        String sql = "insert into adopter (name, phonenumber)" +
                "values(?,?)";
        int numRows = 0;
        numRows += template.update(sql, "Bill", "222-222-2222");
        numRows += template.update(sql, "Hannah", "777-777-7777");

        System.out.println("rows: " + numRows);

    }

}



