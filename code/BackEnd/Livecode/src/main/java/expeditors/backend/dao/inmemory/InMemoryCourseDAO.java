package expeditors.backend.dao.inmemory;

import expeditors.backend.dao.BaseDAO;
import expeditors.backend.domain.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCourseDAO implements BaseDAO<Course> {

    private Map<Integer, Course> courses = new ConcurrentHashMap<Integer, Course>();
    private static AtomicInteger nextId = new AtomicInteger(1);

    public InMemoryCourseDAO() {
        int stop = 0;
    }

    public boolean update(Course updateObject) {
        return courses.replace(updateObject.getId(), updateObject) != null;
//        if (courses.containsKey(updateObject.getId())) {
//            courses.put(updateObject.getId(), updateObject);
//        }
    }

    public boolean delete(Course course) {
        return courses.remove(course.getId()) != null;
    }

    public Course insert(Course newObject) {
        //Create a new Id
        int newId = nextId.getAndIncrement();
        newObject.setId(newId);
        courses.put(newId, newObject);

        return newObject;
    }

    public Course findById(int id) {
        return courses.get(id);
    }

    public List<Course> findAll() {
        return new ArrayList<Course>(courses.values());
    }

    public void deleteStore() {
        courses = null;
    }

    public void createStore() {
        courses = new HashMap<Integer, Course>();
    }

    public Map<Integer, Course> getCourses() {
        return courses;
    }

    public void setCourses(Map<Integer, Course> courses) {
        this.courses = courses;
    }
}
