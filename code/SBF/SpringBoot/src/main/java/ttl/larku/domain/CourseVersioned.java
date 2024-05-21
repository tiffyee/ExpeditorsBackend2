package ttl.larku.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

@Entity
public class CourseVersioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private int version = 1;

    private String title;
    private String code;
    private float credits = 2.5f;

    @Transient
    private String rating;

    @Transient
    @JsonIgnore
    private float[] creditList = {1, 1.5f, 2, 2.5f, 3, 3.5f, 4};

    public CourseVersioned() {
    }

    public CourseVersioned(String code, String title, float credits) {
        super();
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public CourseVersioned(String code, String title) {
        this(code, title, 2.5f);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public float[] getCreditList() {
        return creditList;
    }

    public void setCreditList(float[] creditList) {
        this.creditList = creditList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseVersioned other = (CourseVersioned) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "CourseVersioned{" +
              "id=" + id +
              ", version=" + version +
              ", title='" + title + '\'' +
              ", code='" + code + '\'' +
              ", credits=" + credits +
              ", rating='" + rating + '\'' +
              '}';
    }

}
