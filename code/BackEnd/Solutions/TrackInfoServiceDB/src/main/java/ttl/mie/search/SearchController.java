package ttl.mie.search;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.pricing.PricingProvider;
import ttl.mie.pricing.PricingUtils;

@RestController
@RequestMapping("/tracks")
public class SearchController {

   @Autowired
   private PricingUtils pricingUtils;

   @Autowired
   private JPASearchSpecService<TrackEntity, Integer, TrackRepo> searchService;

   @Autowired
   private TrackRepo targetRepository;

   //CHANGE Required.  Set this to the name of the
   //object your templating engine is going to look for.
   private String resultPropertName = "tracks";

   //CHANGE Required.  Set this to the name of the
   //next view you want to show.
   private String nextViewName = "showTracks";

   @GetMapping("/search")
   public ModelAndView getAllTracks(@RequestParam Map<String, Object> queryStrings) {
//      List<TrackEntity> result = null;
//      Object tmp = queryStrings.get("pageSize");
//      int pageSize = tmp != null ? Integer.parseInt(tmp.toString()) : -1;
//      if (pageSize == -1) {
//         var sz = (Integer) session.getAttribute("pageSize");
//         if (sz != null) {
//            pageSize = sz;
//         } else {
//            pageSize = 10;
//         }
//      }
//      session.setAttribute("pageSize", pageSize);

      //If we have a "searchParams" query parameter,
      //break it out and put it into the searchContext as
      //individual parameters.
      var searchParams = queryStrings.get("searchParams");
      if(searchParams != null)  {
         String spStr = (String) searchParams;
         if(!spStr.isBlank()) {
            var arr = searchParams.toString().split("&");
            for (String param : arr) {
               var innerArr = param.split("=");
               queryStrings.put(innerArr[0], innerArr[1]);
            }
         }
      }
//      queryStrings.put("pageSize", Integer.toString(pageSize));

//      result = searchService.getTracksByRequestParams(queryStrings, targetRepository);
      ResultWithPageData<TrackEntity> pageResult = searchService.doSearch(queryStrings, targetRepository);

      Object tmp = queryStrings.get("page");
      int page = tmp != null ? Integer.parseInt(tmp.toString()) : 0;

      tmp = queryStrings.get("pageSize");
      int pageSize = tmp != null ? Integer.parseInt(tmp.toString()) : 0;

      tmp = queryStrings.get("totalPages");
      int totalPages = tmp != null ? Integer.parseInt(tmp.toString()) : 1;

      tmp = queryStrings.get("totalElements");
      long totalElements = tmp != null ? Long.parseLong(tmp.toString()) : pageResult.result().size();

      postProcess(pageResult.result());

      var props = Map.of(
            "page", page,
            "pageSize", pageSize,
            "totalPages", totalPages,
            "totalElements", totalElements,
            "searchParams", searchParams != null ? searchParams : "",
            "searchResult", pageResult.result(),    //This one is for the searchHeader

            resultPropertName, pageResult.result());

      ModelAndView mav = new ModelAndView(nextViewName, props);
      return mav;
   }

   public List<TrackEntity> postProcess(List<TrackEntity> result) {
      result.forEach(track ->  {
         pricingUtils.addPriceToTrack(track);
      });

      return result;
   }
}
