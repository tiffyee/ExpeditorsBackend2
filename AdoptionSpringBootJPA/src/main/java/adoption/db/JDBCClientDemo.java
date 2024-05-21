package adoption.db;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class JDBCClientDemo {

    public static void main(String[] args) {
        JDBCClientDemo jtd = new JDBCClientDemo();
        String url = "jdbc:postgresql://localhost:5433/adoptapp";
        String user = "larku";
        String pw = "larku";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

        JdbcClient jdbcClient = JdbcClient.create(dataSource);

//        jtd.getSimpleWithOneColumn(jdbcClient);
//      jtd.getAllCourses(jdbcClient);
//      jtd.addAdoptersWithPets(jdbcClient);
      jtd.getWholeObjectWithRelationShipWithResultSetExtractor(jdbcClient);
    }

    public void getSimpleWithOneColumn(JdbcClient jdbcClient) {
        String sql = "select name from adopter where id = :id";

        String name = jdbcClient.sql(sql)
                .param("id", 2)
                .query(String.class)
                .single();

        System.out.println("name: " + name);
    }

    public void getWholeObject(JdbcClient jdbdClient) {
        String sql = "select * from Course where id = ?";

        Adopter adopter = jdbdClient.sql(sql)
                .param(3)
                .query(Adopter.class)
                .single();

        System.out.println("adopter: " + adopter);
    }

    public void getAllAdopters(JdbcClient jdbdClient) {
        String sql = "select * from adopter";

        List<Adopter> adopters = jdbdClient.sql(sql)
                .query(Adopter.class)
                .list();

        adopters.forEach(System.out::println);
    }

    public void addAdoptersWithPets(JdbcClient jdbcClient) {
        String insertAdopterSql = "insert into adopter (name, phonenumber) values(?, ?)";

        String insertPetSql = "insert into pet (name, type, breed) values( ?, ?, ?)";

        String insertAdoptedPetSql = "insert into adopted_pet (adopter_id, pet_id, adoptiondate) values( ?, ?, ?)";

        List<Integer> newAdopterKeys = new ArrayList<>();

        Pet pet1 = Pet.builder(Pet.PetType.CAT)
                .petId(1)
                .name("Bobo")
                .breed("Tabby")
                .build();

        Pet pet2 = Pet.builder(Pet.PetType.DOG)
                .petId(2)
                .name("Jude")
                .breed("Golden Retriever")
                .build();

        Pet pet3 = Pet.builder(Pet.PetType.DOG)
                .petId(3)
                .name("Moose")
                .breed("Bernadoodle")
                .build();

        Pet pet4 = Pet.builder(Pet.PetType.TURTLE)
                .petId(4)
                .name("Frank")
                .breed("Red-Eared Slider")
                .build();


        Adopter adopter1 = new Adopter();
        adopter1.setId(1);
        adopter1.setName("John Doe");
        adopter1.setPhoneNumber("333-333-3333");
        adopter1.setAdoptionDate(LocalDate.parse("2013-12-12"));
        adopter1.addPet(pet1);
        adopter1.addPet(pet4);

        Adopter adopter2 = new Adopter();
        adopter2.setId(2);
        adopter2.setName("Jane Smith");
        adopter2.setPhoneNumber("123-456-7890");
        adopter2.setAdoptionDate(LocalDate.parse("2002-10-15"));
        adopter2.addPet(pet2);

        Adopter adopter3 = new Adopter();
        adopter3.setId(3);
        adopter3.setName("Tiffany Yee");
        adopter3.setPhoneNumber("555-555-5555");
        adopter3.setAdoptionDate(LocalDate.parse("2016-07-15"));
        adopter3.addPet(pet3);

        List<Adopter> adopters = List.of(
                adopter1,
                adopter2,
                adopter3
        );

        List<Object[]> adopterParams = adopters.stream()
                .map(s -> new Object[]{s.getName() + "-JDBC", s.getPhoneNumber()})
                .toList();


        List<Object[]> petParams = adopters.stream()
                .flatMap(s -> s.getPets().stream()
                        .map(p -> new Object[]{p.getName(), p.getType().toString(), p.getBreed()}))
                .toList();

        List<Object[]> adoptedPetParams = adopters.stream()
                .flatMap(s -> s.getPets().stream()
                        .map(p -> new Object[]{s.getId(),p.getPetId(), s.getAdoptionDate()}))
                .toList();
        System.out.println("adopterParams: ");
        adopterParams.forEach(System.out::println);
        System.out.println("petParams: ");
        petParams.forEach(System.out::println);
        System.out.println("adoptedPetParams: ");
        adoptedPetParams.forEach(System.out::println);

        System.out.println("Running sql: " + insertAdopterSql);
        var keyHolder = new GeneratedKeyHolder();
        for (Object[] params : adopterParams) {
            jdbcClient.sql(insertAdopterSql)
                    .params(params)
                    .update(keyHolder);

//         out.println("Keys for new student: " + keyHolder.getKeys());
            newAdopterKeys.add((int)keyHolder.getKeys().get("id"));
        }


        System.out.println("Running sql: " + insertPetSql);
        for (Object[] params : petParams) {
            jdbcClient.sql(insertPetSql)
                    .params(params)
                    .update(keyHolder);
        }

        System.out.println("Running sql: " + insertAdoptedPetSql);
        for (Object[] params : adoptedPetParams) {
            jdbcClient.sql(insertAdoptedPetSql)
                    .params(params)
                    .update(keyHolder);
        }

        System.out.println("adopters added : " + newAdopterKeys.size());
        newAdopterKeys.forEach(System.out::println);
    }

    public void getWholeObjectWithRelationShipWithResultSetExtractor(JdbcClient jdbcClient) {
        String sql = """
                select *, a.name as adopterName, p.name as petName from adopted_pet ap
                    left join adopter a on ap.adopter_id = a.id
                    left join pet p on ap.pet_id = p.id
                    order by a.id
                """;

        List<Adopter> result = new ArrayList<>();
        ResultSetExtractor<List<Adopter>> resultSetExtractor = (resultSet) -> {
            int lastAdopterId = -1;
            Adopter currAdopter = null;

            while (resultSet.next()) {
                int id = resultSet.getInt("adopter_id");
                if (id != lastAdopterId) {
                    lastAdopterId = id;

                    String adopterName = resultSet.getString("name");
                    String phoneNumber = resultSet.getString("phonenumber");
                    LocalDate dob = resultSet.getObject("adoptiondate", LocalDate.class);

                    currAdopter = new Adopter(adopterName, phoneNumber);
                    currAdopter.setId(id);
                    result.add(currAdopter);
                }
                String type = resultSet.getString("type");
                if (type != null) {
                    Pet pet = Pet.builder(Pet.PetType.valueOf(type))
                            .petId(resultSet.getInt("pet_id"))
                            .name(resultSet.getString("petname"))
                            .breed(resultSet.getString("breed"))
                            .build();

                    currAdopter.addPet(pet);
                }
            }
            return result;
        };

        List<Adopter> adopters = jdbcClient.sql(sql)
                .query(resultSetExtractor);


        adopters.forEach(out::println);
    }

}
