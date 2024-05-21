package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.dao.AdopterDAO;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.PetType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterService {

   @Autowired
   private AdopterDAO adopterDAO;

   public Adopter addAdopter(Adopter adopter) {
      return adopterDAO.insert(adopter);
   }

   public Adopter getAdopter(int id) {
      return adopterDAO.findById(id);
   }

   public List<Adopter> getAllAdopters() {
      return adopterDAO.findAll();
   }

   public boolean deleteAdopter(int id) {
      return adopterDAO.delete(id);
   }

   public boolean updateAdopter(Adopter adopter) {
      return adopterDAO.update(adopter);
   }

   public void setAdopterDAO(AdopterDAO adopterDAO) {
      this.adopterDAO = adopterDAO;
   }

   public List<Adopter> getAdoptersByPetType(PetType type) {
      List<Adopter> result = adopterDAO
            .findAll().stream()
            .filter(a -> a.getPet().getType() == type)
            .toList();
      return result;
   }

   public List<Adopter> getAdoptersBy(Map<String, String> queryStrings) {
      Map<String, Object> converted = convertToMapOfCorrectTypes(queryStrings);
      Predicate<Adopter> pred = null;
      for (var entry : converted.entrySet()) {
         var key = entry.getKey();
         var value = entry.getValue();
         switch (key) {
            case String s when s.equals("pet.type") -> {
               Predicate<Adopter> tmpPred = (a) -> a.getPet().getType() != null
                     && a.getPet().getType().equals(value);
               pred = pred == null ? tmpPred : pred.or(tmpPred);

            }
            case String s when s.equals("pet.name") -> {
               Predicate<Adopter> tmpPred = (a) -> a.getPet().getName() != null
                     && a.getPet().getName().equals(value);
               pred = pred == null ? tmpPred : pred.or(tmpPred);
            }
            case String s when s.equals("pet.breed") -> {
               Predicate<Adopter> tmpPred = (a) -> a.getPet().getBreed() != null
                     && a.getPet().getBreed().equals(value);
               pred = pred == null ? tmpPred : pred.or(tmpPred);
            }
            case String s -> {
               Predicate<Adopter> tmpPrede = (a) -> {
                  var getMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
                  try {
                     Method method = a.getClass().getMethod(getMethodName);
//                  BeanWrapper bw = new BeanWrapperImpl(a);
                     String propValue = (method.invoke(a)).toString();
                     var t = propValue.contains(value.toString());
                     return t;
                  } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                     throw new RuntimeException(e);
                  }
               };
               pred = pred == null ? tmpPrede : pred.or(tmpPrede);
            }

//            case String s -> {
//               Predicate<Adopter> adoptionDatePred = (a) -> {
//                  BeanWrapper bw = new BeanWrapperImpl(a);
//                  String propValue = ((String) bw.getPropertyValue(key)).toUpperCase();
//                  return propValue.contains(value.toString().toUpperCase());
//               };
//               pred = pred == null ? adoptionDatePred : pred.or(adoptionDatePred);
//            }

         }
      }

      List<Adopter> result = getAllAdopters().stream()
            .filter(pred)
            .toList();
      return result;
   }

   public Map<String, Object> convertToMapOfCorrectTypes(Map<String, String> queryStrings) {
      Map<String, Object> result = new HashMap<>();
      queryStrings.forEach((k, v) -> {
         switch (k) {
            case String s when
                  s.contains("adoptionDate") -> result.put(k, LocalDate.parse(v));

            case String s when
                  s.contains("pet.type") -> result.put(k, PetType.valueOf(v.toUpperCase()));

            default -> result.put(k, v);
         }
      });

      return result;
   }
}
