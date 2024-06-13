package ttl.mie.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.search.JpaSearchSpecSupport;
import ttl.mie.search.JpaTrackSearchSpec;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestTrackRepoPaging {

   @Autowired
   private TrackRepo trackRepo;

   @Test
   public void testBasicPagingSupportForTrackRepo() {
      int currPage = 0;
      int size = 20;
      int totalElements = 0;

      //Set up sorting criteria
      Sort sort = Sort.by("trackId").descending();

      Specification<TrackEntity> searchSpec = JpaTrackSearchSpec.ALL;
      //Use the paging variation of the findAll method.
      Page<TrackEntity> page = null;
      do {
//         page = trackRepo.findAll(searchSpec, PageRequest.of(currPage++, size, sort));
         page = trackRepo.findAll(PageRequest.of(currPage++, size));
         totalElements += page.getNumberOfElements();
         out.println("Page Number: " + page.getNumber() + ", numElements: " + page.getNumberOfElements());

         page.forEach(t -> out.println(t.getTrackId() + ", " + t.getAlbum() + ", " + t.getGroup()));
      } while (page.hasNext());

//      assertEquals(30, totalElements);
//      assertEquals(1, page.getNumber());

   }

   @Test
   public void testPagingSupportForTrackRepoWithSpecification() {
      int currPage = 0;
      int size = 15;
      int totalElements = 0;

      //Set up sorting criteria
      Sort sort = Sort.by("trackId").descending();

      Specification<TrackEntity> searchSpec = new JpaTrackSearchSpec(JpaSearchSpecSupport.SearchType.ContainsString,
            "title", "o");
      //Use the paging variation of the findAll method.
      Pageable pageable = PageRequest.of(currPage++, size, sort);

      Page<TrackEntity> page = null;
      do {
         page = trackRepo.findAll(searchSpec, PageRequest.of(currPage++, size, sort));
//         page = trackRepo.findAll(PageRequest.of(currPage++, size));
         totalElements += page.getNumberOfElements();
         out.println("Page Number: " + page.getNumber() + ", numElements: " + page.getNumberOfElements());

         page.forEach(t -> out.println(t.getTrackId() + ", " + t.getAlbum() + ", " + t.getGroup()));
      } while (page.hasNext());

//      assertEquals(17, totalElements);
//      assertEquals(1, page.getNumber());

   }
}
