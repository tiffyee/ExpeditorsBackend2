package ttl.mie.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.pricing.PricingProvider;
import ttl.mie.search.JPASearchSpecService;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
@Transactional
public class TrackRepoService {

   private final JPASearchSpecService<TrackEntity, Integer, TrackRepo> searchService;
   private TrackRepo trackDAO;
   private DecimalFormat decimalFormat = new DecimalFormat("0.00");

   private final PricingProvider pricingProvider;


   public TrackRepoService(TrackRepo trackDAO,
                           PricingProvider pricingProvider,
                           JPASearchSpecService<TrackEntity, Integer, TrackRepo> searchService) {
      this.trackDAO = trackDAO;
      this.pricingProvider = pricingProvider;
      this.searchService = searchService;
   }

   public TrackEntity addTrack(TrackEntity track) {
      return trackDAO.save(track);
   }

   public boolean deleteTrack(int id) {
      if (!trackDAO.existsById(id)) {
         trackDAO.deleteById(id);
         return true;
      }
      return false;
   }

   public boolean updateTrack(TrackEntity track) {
      if (!trackDAO.existsById(track.getTrackId())) {
         trackDAO.save(track);
         return true;
      }
      return false;
   }

   public Optional<TrackEntity> getById(int id) {
      var track = trackDAO.findTrackWithArtistsById(id)
            .map(this::addPriceToTrack);

      return track;
   }

   public List<TrackEntity> getAllTracks() {
      List<TrackEntity> tracks =
            trackDAO.findTracksWithArtists().stream()
                  .map(this::addPriceToTrack)
                  .toList();

      return tracks;
   }

   private TrackEntity addPriceToTrack(TrackEntity track) {
      BigDecimal price = pricingProvider.getPriceByTrackId(track.getTrackId());
      track.setPrice(price.toString());
      return track;
   }

   /**
    * This one is the first point of entry.  We will filter out anything
    * that is not a SearchSpecification, e.g. sorting parameters.
    *
    * @param searchContext
    * @return
    */
   public List<TrackEntity> getTracksByRequestParams(Map<String, Object> searchContext) {

      List<TrackEntity> result = searchService.getTracksByRequestParams(searchContext, trackDAO);

//      Map<Boolean, List<Map.Entry<String, Object>>> partition = searchContext.entrySet().stream()
//            .collect(Collectors.partitioningBy(entry -> entry.getKey().matches(".\\..*")));
//
//      Map<String, String> nonSearchParams = partition.get(false)
//            .stream().collect(Collectors.toMap(Map.Entry::getKey, me -> me.getValue().toString()));
      //look for page property
//      Pageable pageable = null;
//      if (searchContext.containsKey("page")) {
//         int page = Integer.parseInt(searchContext.get("page").toString());
//         int pageSize = searchContext.containsKey("pageSize") ?
//               Integer.parseInt(searchContext.get("pageSize").toString()) : 10;
//
//         pageable = PageRequest.of(page, pageSize);
//      }
//
//      var haveSearchParams = searchContext.entrySet().stream()
//            .filter(entry -> entry.getKey().matches(".\\..*"))
//            .findFirst();
//
////      Map<String, Object> searchParams = partition.get(true)
////            .stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//      List<TrackEntity> result = null;
//      if (haveSearchParams.isPresent()) {
//
//         result = searchService.getTracksBySearchSpec(searchContext, pageable, trackDAO);
//
//      } else if (pageable != null) {
//         Page<TrackEntity> page = trackDAO.findAll(pageable);
//         searchContext.put("totalElements", page.getTotalElements());
//         searchContext.put("totalPages", page.getTotalPages());
//         result = page.getContent();
//      } else {
//         result = trackDAO.findAll();
//      }
      result.forEach(this::addPriceToTrack);
      return result;
   }

//   /**
//    * Handle JpaSearchSpec parameters.  Look at JpaTrackSearchSpec.  For usage,
//    * look at the test in the 'dao' package.
//    *
//    * @param rawQueryStrings
//    * @return
//    */
//   public List<TrackEntity> getTracksBySearchSpec(Map<String, Object> rawQueryStrings) {
//      return getTracksBySearchSpec(rawQueryStrings, null);
//   }
//
//   public List<TrackEntity> getTracksBySearchSpec(Map<String, Object> searchContext,
//                                                  Pageable pageAble) {
//      Map<String, Object> queryStrings = convertToMapOfCorrectTypes(searchContext);
//
//      List<JpaTrackSearchSpec> searchSpecs = new ArrayList<>();
//      queryStrings.forEach((k, value) -> {
//         if(k.matches(".\\..*")) {
//            //Pattern we are looking for is op.property
//            //op is first character
//            var opSymbol = k.substring(0, 1);
//            var searchType = JpaSearchSpecSupport.SearchType.getBySymbol(opSymbol);
//            //Property name to filter by starts at position 2
//            var propName = k.substring(2);
//
//            var ss = new JpaTrackSearchSpec(searchType,
//                  propName, value);
//            searchSpecs.add(ss);
//         }
//      });
//
//      List<TrackEntity> result = getTracksBySearchSpec(searchContext, searchSpecs, pageAble);
//      return result;
//   }
//
//   public List<TrackEntity> getTracksBySearchSpec(Map<String, Object> searchContext,
//                                                  List<JpaTrackSearchSpec> searchSpecs,
//                                                  Pageable pageAble) {
//      if (searchSpecs.isEmpty()) return List.of();
//
//      Specification<TrackEntity> specification = searchSpecs.get(0);
//      for (int i = 1; i < searchSpecs.size(); i++) {
//         specification = specification.or(searchSpecs.get(i));
//      }
//
//      List<TrackEntity> result = null;
//      if (pageAble != null) {
//         var page = trackDAO.findAll(specification, pageAble);
//         searchContext.put("totalElements", page.getTotalElements());
//         searchContext.put("totalPages", page.getTotalPages());
//         result = page.getContent();
//      } else {
//         result = trackDAO.findAll(specification);
//      }
//
//      return result;
//   }
//
//   //Hard coded for now
//   public Map<String, Object> convertToMapOfCorrectTypes(Map<String, Object> searchContext) {
////      Map<String, Object> result = new HashMap<>();
//      searchContext.forEach((k, v) -> {
//         switch (k) {
//            case String s when
//                  s.contains("length") || s.contains("duration") -> searchContext.put(k, Duration.parse(v.toString()));
//
//            case String s when
//                  s.contains("format") -> searchContext.put(k, Format.valueOf(v.toString().toUpperCase()));
//
//            default -> {
//            }
//         }
//      });
//
//      return searchContext;
//   }

   public List<TrackEntity> getTracksBy(Map<String, String> queryStrings) {
      TrackEntity.Builder builder = new TrackEntity.Builder();
      queryStrings.forEach((k, v) -> {
         if (k.equals("title")) {
            builder.title(v);
         } else if (k.equals("artist")) {
            builder.artist(v);
         } else if (k.equals("album")) {
            builder.album(v);
         }
      });
      TrackEntity example = builder.build();

      List<TrackEntity> result = getTracksBy(example);
      return result;
   }

   public List<TrackEntity> getTracksBy(TrackEntity probe) {
      //Here we can set up rules on how to do the matching.
      //We are selecting anything that matches on any property (matchingAny),
      // matching the "artist" property with contains,
      // matching the "title" property with contains,
      // matching the "album" property with contains,
      // not looking at the "id" property,
      // ignoring case for all matches,
      //and not matching null values.
      ExampleMatcher matcher = ExampleMatcher.matchingAny()
            .withMatcher("title", contains())
            .withMatcher("album", contains())
            .withIgnorePaths("id", "trackId")
            .withIgnoreCase()
            .withIgnoreNullValues();

      //Now we make our Example from the probe and the matcher
      Example<TrackEntity> example = Example.of(probe, matcher);

      List<TrackEntity> result = trackDAO.findAll(example).stream()
            .map(this::addPriceToTrack)
            .toList();
      return result;
   }

//
//
//
   /**
    * Partial update of tracks.  Here we do it "by hand", i.e
    * use the Reflection API directly.  We try to match
    * property names in the input map to corresponding "set"Blah
    * methods in the object.
    *
    * @param id
    * @param props
    */
//   public void updateTrackPartial(int id, Map<String, Object> props) {
//      trackDAO.findById(id)
//            .ifPresent(t -> {
//               Class<?> clazz = Track.class;
//               Map<String, Method> methods = Arrays
//                     .asList(clazz.getMethods()).stream()
//                     .filter(m -> m.getName().startsWith("set"))
//                     .collect(Collectors.toMap(m -> m.getName(), m -> m));
//
//               props.forEach((name, value) -> {
//                  if (!name.equals("id")) {
//                     String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
//                     Method m = methods.get(setMethodName);
//                     if (m != null) {
//                        Class<?>[] paramTypes = m.getParameterTypes();
//                        if (paramTypes.length == 1) {
//                           Object param = value;
//                           try {
//                              Class<?> pClass = paramTypes[0];
//                              //We only handle String properties for now.
//                              if (pClass.equals(String.class)) {
//                                 param = String.valueOf(value);
//                              }
//                              m.invoke(t, param);
//                           } catch (Exception e) {
//                              e.printStackTrace();
//                           }
//                        }
//                     }
//                  }
//               });
//            });
//   }

   /**
    * Here we use the handy Spring BeanWrapper class to make our lives
    * that little bit simpler.
    *
    * @param id    The id of the resource to update.
    * @param props The properties to update.
    */
   public void updateTrackPartialBeanWrapper(int id, Map<String, Object> props) {
      trackDAO.findById(id)
            .ifPresent(track -> {

//      if (track != null) {
               BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(track);
               props.forEach((name, value) -> {
                  if (!name.equals("id")) {
                     if (bw.isWritableProperty(name)) {
                        Class<?> pClass = bw.getPropertyType(name);
                        bw.setPropertyValue(name, bw.convertIfNecessary(value, pClass));
                     }
                  }
               });
            });
   }
}

