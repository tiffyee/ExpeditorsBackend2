package ttl.mie.dao.repository.track;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Component;
import ttl.mie.domain.track.dto.TrackWithArtistsNames;
import ttl.mie.domain.track.entity.TrackEntity;

/**
 * Implementation of our custom JPARepo interface.
 * NOTE: The naming seems to be important.  The name of the
 *  implementing class has to be [Interface name]Impl.
 * @author whynot
 */

@Component
public class CustomTrackRepoImpl implements CustomTrackRepo {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    /**
     * Use the Criteria API to create our query.  Code is
     * is ugly, but safer than building a query up by appending Strings.
     *
     * @param example
     * @return
     */
    @Override
    public List<TrackEntity> getByExampleWithCriteria(TrackEntity example) {

        //Get the builder
        CriteriaBuilder builder = em.getCriteriaBuilder();
        //Create a query that will return Tracks
        CriteriaQuery<TrackEntity> cq = builder.createQuery(TrackEntity.class);

        //Tracks is also going to be the (only) root entity we will
        //be searching from.  This need not always be the same as the
        //type returned from the query.  This is the 'From' clause.
        Root<TrackEntity> queryRoot = cq.from(TrackEntity.class);
        //We are going to also get the artists
        queryRoot.fetch("artists", JoinType.LEFT); //.fetch("course", JoinType.LEFT);

        //This is the 'select' part of the query.
        // We are going to be selecting Tracks.
        cq.select(queryRoot).distinct(true);

        //Build up a List of javax.persistence.criteria.Predicate objects,
        //based on what is not null in the example Student.
        List<Predicate> preds = new ArrayList<>();
        if (example.getTitle() != null) {
            //we are doing a 'like' comparison, with the lower case entity title to lower case example title.
            //But purposely asking for "id" instead of title.  This will throw exception at query creation at run time.
            //MetaModel variation above will catch this error at compile time.
            preds.add(builder.like(builder.lower(queryRoot.get("id")), "%" + example.getTitle().toLowerCase() + "%"));
        }
        //Here is how you can go into a collection valued attribute
        //e.g. artists.name
        if (!example.getArtists().isEmpty()) {
            var first = example.getArtists().stream().findFirst().orElseThrow();
            preds.add(builder.like(builder.lower(queryRoot.get("artists").get("name")), "%" + first.getName().toLowerCase() + "%"));
        }
        if (example.getAlbum() != null) {
            preds.add(builder.like(builder.lower(queryRoot.get("album")), "%" + example.getAlbum().toLowerCase() + "%"));
        }

        //Now 'or' them together.
        Predicate finalPred = builder.or(preds.toArray(new Predicate[0]));

        //And set them as the where clause of our query.
        cq.where(finalPred);

        List<TrackEntity> result = em.createQuery(cq).getResultList();

        return result;
    }

    @Override
    public List<TrackWithArtistsNames> findTracksWithArtistNames() {
        String jpqlStr = """
                   select t.title, a.name
                   from TrackEntity t left join t.artists a
                """;
        List<TrackWithArtistsNames> tResult = doQuery(jpqlStr);
        return tResult;
    }

    private List<TrackWithArtistsNames> doQuery(String jpqlStr, String ...params) {
        var transformer = new TrackEntityToDTOTransformer();
        TypedQuery<TrackWithArtistsNames> query =
                em.createQuery(jpqlStr, TrackWithArtistsNames.class)
                        .unwrap(org.hibernate.query.Query.class)
                        .setTupleTransformer(transformer)
                        .setResultListTransformer(transformer);

        for(int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<TrackWithArtistsNames> result = query.getResultList();

//        System.out.println("Result: " + result);

        return result;
    }

    class TrackEntityToDTOTransformer implements TupleTransformer<TrackWithArtistsNames>,
            ResultListTransformer<TrackWithArtistsNames> {

        Map<String, TrackWithArtistsNames> cWithP = new HashMap<>();

        @Override
        public TrackWithArtistsNames transformTuple(Object[] tuple, String[] aliases) {

            String title = (String) tuple[0];
            String artist = (String) tuple[1];
            TrackWithArtistsNames twan = cWithP.computeIfAbsent(title, i -> {
                var tmp = new TrackWithArtistsNames(title, new ArrayList<>());
                return tmp;
            });
            twan.artistNames().add(artist);

            return twan;
        }

        @Override
        public List<TrackWithArtistsNames> transformList(List<TrackWithArtistsNames> resultList) {
            return new ArrayList<>(cWithP.values());
        }
    }
}
