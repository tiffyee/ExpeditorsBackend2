package ttl.mie.dao.inmemory;

import java.util.function.Predicate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author whynot
 */
public class InMemorySearchSpec<T> {

   public enum Operation {
      And,
      Or
   }

   //Very basic comparison operators.
   //Put these symbols before the property in the map
   //e.g. e.title=Autumn in Vermont compares title with equality
   //e.g. c.album=Remember compares album name with contains
   //Note, the non string properties will only work if invoked
   //from a Java Test.  In particular, we are NOT doing any conversions
   // on the values coming in over the web.

   //e.  equal
   //!.  not equal
   //l.  less than
   //L.  Less than or equal
   //g.  Greater than
   //G.  Greater than or equal
   //c.  contains
   //C.  contains ignore case

   public enum SearchType {
      Equal(false, "e") {
         @Override
         public <T> Predicate<T> makeIt(String propName, Object value) {
            Predicate<T> pred = (x) -> {
               BeanWrapper bw = new BeanWrapperImpl(x);
               Object propValue = bw.getPropertyValue(propName);
               return propValue.equals(value);
            };

            return pred;
         }
      },
      NotEqual(false, "!") {
         @Override
         public <T> Predicate<T> makeIt(String propName, Object value) {
            Predicate<T> pred = (x) -> {
               BeanWrapper bw = new BeanWrapperImpl(x);
               Object propValue = bw.getPropertyValue(propName);
               return !propValue.equals(value);
            };

            return pred;
         }
      },
      Greater(true, "g") {
            @SuppressWarnings({"unchecked", "rawtypes"})
            @Override
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  Comparable propValue = (Comparable)bw.getPropertyValue(propName);
                  return propValue.compareTo((Comparable)value) > 0;
               };

               return pred;
            }
      },
      GreaterEqual(true, "G") {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  Comparable propValue = (Comparable)bw.getPropertyValue(propName);
                  return propValue.compareTo((Comparable)value) >= 0;
               };

               return pred;
            }
      },
      Less(true, "l") {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  Comparable propValue = (Comparable)bw.getPropertyValue(propName);
                  return propValue.compareTo((Comparable)value) < 0;
               };

               return pred;
            }
      },
      LessEqual(true, "L") {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  Comparable propValue = (Comparable)bw.getPropertyValue(propName);
                  return propValue.compareTo((Comparable)value) <= 0;
               };

               return pred;
            }
      },
      ContainsString(false, "c") {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  String propValue = (String)bw.getPropertyValue(propName);
                  var result = propValue.contains(value.toString());
                  return result;
               };

               return pred;
            }
      },
      ContainsStringIC(false, "C") {
            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public <T> Predicate<T> makeIt(String propName, Object value) {
               Predicate<T> pred = (x) -> {
                  BeanWrapper bw = new BeanWrapperImpl(x);
                  String propValue = ((String)bw.getPropertyValue(propName)).toUpperCase();
                  return propValue.contains(value.toString().toUpperCase());
               };

               return pred;
            }
      },
      Custom(false, "x");

      public final boolean needsComparable;
      public final String symbol;

      SearchType(boolean needsComparable, String symbol) {
         this.needsComparable = needsComparable;
         this.symbol = symbol;
      }

      public static SearchType getBySymbol(String symbol) {
         for(SearchType st : SearchType.values()) {
            if(st.symbol.equals(symbol)) {
               return st;
            }
         }
         return null;
      }

      public <T> Predicate<T> makeIt(String propName, Object value) {
         return (t) -> false;
      }
   }

   public final SearchType searchType;
   public final Object propValue;
   public final String propName;
   public Predicate<T> predicate;


   public InMemorySearchSpec(SearchType searchType,
                             String propName, Object propValue, Predicate<T> predicate) {
      this.searchType = searchType;
      this.propName = propName;
      this.propValue = propValue;
      this.predicate = predicate;
   }

   public InMemorySearchSpec(SearchType searchType, String propName, Object propValue) {
      this(searchType, propName, propValue, null);
      predicate = makePredicate();
   }

   public Predicate<T> makePredicate() {
      if (searchType.needsComparable && !(propValue instanceof Comparable)) {
         throw new RuntimeException(searchType + " can only be applied to Comparables");
      }

      Predicate<T> pred = searchType.makeIt(propName, propValue);
      return pred;
   }

   @Override
   public String toString() {
      return "SearchSpec{" +
            "searchType=" + searchType +
            ", propName='" + propName + '\'' +
            ", value='" + propValue + '\'' +
            '}';
   }
}
