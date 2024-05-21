package adoption.jconfig;

import org.springframework.stereotype.Component;

@Component
public class AdoptionTestDataConfig {

//    public Pet pet1() {
//        Pet pet = Pet.builder(Pet.PetType.CAT)
//                .petId(1)
//                .name("Bobo")
//                .breed("Tabby")
//                .build();
//        return pet;
//    }
//
//    public Pet pet2() {
//        Pet pet = Pet.builder(Pet.PetType.DOG)
//                .petId(2)
//                .name("Jude")
//                .breed("Golden Retriever")
//                .build();
//        return pet;
//    }
//
//    public Pet pet3() {
//        Pet pet = Pet.builder(Pet.PetType.DOG)
//                .petId(3)
//                .name("Moose")
//                .breed("Bernadoodle")
//                .build();
//        return pet;
//    }
//
//    public Pet pet4() {
//        Pet pet = Pet.builder(Pet.PetType.TURTLE)
//                .petId(4)
//                .name("Frank")
//                .breed("Red-Eared Slider")
//                .build();
//        return pet;
//    }
//
//    public Adopter adopter1() {
//        Adopter adopter = new Adopter();
//        adopter.setId(1);
//        adopter.setName("John Doe");
//        adopter.setPhoneNumber("333-333-3333");
//        adopter.setAdoptionDate(LocalDate.parse("2013-12-12"));
//        adopter.addPet(pet1());
//        adopter.addPet(pet4());
//        return adopter;
//    }
//
//    public Adopter adopter2() {
//        Adopter adopter = new Adopter();
//        adopter.setId(2);
//        adopter.setName("Jane Smith");
//        adopter.setPhoneNumber("123-456-7890");
//        adopter.setAdoptionDate(LocalDate.parse("2002-10-15"));
//        adopter.addPet(pet2());
//        return adopter;
//    }
//
//    public Adopter adopter3() {
//        Adopter adopter = new Adopter();
//        adopter.setId(3);
//        adopter.setName("Tiffany Yee");
//        adopter.setPhoneNumber("555-555-5555");
//        adopter.setAdoptionDate(LocalDate.parse("2016-07-15"));
//        adopter.addPet(pet3());
//        return adopter;
//    }
//
//
//
//
//
//    public ScheduledClass class1() {
//        ScheduledClass sc = new ScheduledClass();
//        sc.setId(1);
//        sc.setStartDate(LocalDate.parse("2021-10-10"));
//        sc.setEndDate(LocalDate.parse("2022-02-20"));
//        sc.setCourse(course1());
//
//        return sc;
//    }
//
//    public ScheduledClass class2() {
//        ScheduledClass sc = new ScheduledClass();
//        sc.setId(2);
//        sc.setStartDate(LocalDate.parse("2022-10-10"));
//        sc.setEndDate(LocalDate.parse("2023-08-10"));
//        sc.setCourse(course2());
//
//        return sc;
//    }
//
//    public ScheduledClass class3() {
//        ScheduledClass sc = new ScheduledClass();
//        sc.setId(3);
//        sc.setStartDate(LocalDate.parse("2022-10-10"));
//        sc.setEndDate(LocalDate.parse("2023-10-10"));
//        sc.setCourse(course3());
//
//        return sc;
//    }
//
//    public BaseDAO<Adopter> adopterDAOWithInitData() {
//        InMemoryAdopterDAO dao = new InMemoryAdopterDAO();
//        initAdopterDAO(dao);
//        return dao;
//    }
//
////    public BaseDAO<Adopter> adopterJPADAOWithInitData() {
////        JPAAdopterDAO dao = new JPAAdopterDAO();
////        initAdopterDAO(dao);
////        return dao;
////    }
//
//    public void initAdopterDAO(BaseDAO<Adopter> dao) {
//        dao.findAll().forEach(dao::delete);
//        dao.insert(adopter1());
//        dao.insert(adopter2());
//        dao.insert(adopter3());
//
//        BaseDAO<ScheduledClass> classDAO = classDAOWithInitData();
//        Adopter s = dao.findById(adopter1().getId());
//        s.addClass(classDAO.findById(1));
//        s.addClass(classDAO.findById(2));
//    }
//
//    public Map<Integer, Adopter> initAdopters() {
//        Map<Integer, Adopter> adopters = new HashMap<>();
//        adopters.put(adopter1().getId(), adopter1());
//        adopters.put(adopter2().getId(), adopter2());
//        adopters.put(adopter3().getId(), adopter3());
//
//        return adopters;
//    }
//
//
//    public BaseDAO<Pet> courseDAOWithInitData() {
//        InMemoryPetDAO dao = new InMemoryPetDAO();
//        initPetDAO(dao);
//        return dao;
//    }
//
//    public void initPetDAO(BaseDAO<Pet> dao) {
//        dao.findAll().forEach(dao::delete);
//        dao.insert(pet1());
//        dao.insert(pet2());
//        dao.insert(pet3());
//        dao.insert(pet4());
//
//    }
//
//    public Map<Integer, Course> initCourses() {
//        Map<Integer, Course> courses = new HashMap<>();
//
//        courses.put(course1().getId(), course1());
//        courses.put(course2().getId(), course2());
//        courses.put(course3().getId(), course3());
//        return courses;
//    }
//
//    public BaseDAO<ScheduledClass> classDAOWithInitData() {
//        InMemoryClassDAO dao = new InMemoryClassDAO();
//        initClassDAO(dao);
//        return dao;
//    }
//
//    public void initClassDAO(BaseDAO<ScheduledClass> dao) {
//        dao.findAll().forEach(dao::delete);
//
//        dao.insert(class1());
//        dao.insert(class2());
//        dao.insert(class3());
//    }
//
//    public Map<Integer, ScheduledClass> initClasses() {
//        Map<Integer, ScheduledClass> classes = new HashMap<>();
//
//        classes.put(class1().getId(), class1());
//        classes.put(class2().getId(), class2());
//        classes.put(class3().getId(), class3());
//        return classes;
//    }
//
//
//    private Date convertToDate(int year, int month, int day) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(year, month, day);
//
//        return cal.getTime();
//    }

}
