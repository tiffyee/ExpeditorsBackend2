package adoption.dao.jdbc;

//@SpringBootTest
//
//public class JDBCAdopterDAOTest {
//    @Autowired
//    private JDBCAdopterDAO adopterDAO;
//
//
//    @Test
//    public void testFindAll() {
//        List<Adopter> adopters = adopterDAO.findAll();
//
//        System.out.println("adopters: " + adopters);
//        assertEquals(4, adopters.size());
//    }
//
//    @Test
//    public void testFindOneWithGoodId() {
//        Adopter adopter = adopterDAO.findById(1);
//
//        System.out.println("adopter: " + adopter);
//        assertNotNull(adopter);
//    }
//
//    @Test
//    public void testFindOneWithBadId() {
//        Adopter adopter = adopterDAO.findById(1000);
//
//        System.out.println("adopter: " + adopter);
//        assertNull(adopter);
//    }
//
//    @Test
//    @Transactional
//    public void testAddNewAdopter() {
//        Adopter newAdopter = new Adopter("New Guy", "999-999-9999", LocalDate.parse("1999-08-02"), Pet.builder(Pet.PetType.TURTLE).name("Toby").build());
//        Adopter adopter = adopterDAO.insert(newAdopter);
//
//        System.out.println("adopter: " + adopter);
//        assertTrue(adopter.getId() > 0);
//    }
//
////   @Test
////   @Transactional
////   public void testDeleteExitingAdopter() {
////      Adopter toDelete = adopterDAO.findById(1);
////      assertNotNull(toDelete);
////      boolean result = adopterDAO.delete(toDelete);
////
////      assertTrue(result);
////
////      Adopter shouldNotExist = adopterDAO.findById(1);
////      assertNull(shouldNotExist);
////   }
//
//    @Test
//    @Transactional
//    public void testUpdateExistingAdopterShouldSucceed() {
//        String newName = "Yahhooo";
//        Adopter toUpdate = adopterDAO.findById(1);
//        assertNotNull(toUpdate);
//        toUpdate.setName(newName);
//
//        boolean result = adopterDAO.update(toUpdate);
//
//        assertTrue(result);
//
//        Adopter updatedAdopter = adopterDAO.findById(1);
//        System.out.println("Updated: " + updatedAdopter);
//        assertEquals(newName, updatedAdopter.getName());
//    }
//
//    @Test
//    @Transactional
//    public void testUpdateNonExistingAdopterShouldFail() {
//        String newName = "Yahhooo";
//        Adopter toUpdate = adopterDAO.findById(1);
//        assertNotNull(toUpdate);
//        toUpdate.setName(newName);
//        toUpdate.setId(1000);
//
//        boolean result = adopterDAO.update(toUpdate);
//
//        assertFalse(result);
//    }
//
//}
