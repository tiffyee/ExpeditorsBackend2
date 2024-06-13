package ttl.larku.beandiscovery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author whynot
 */
@JacksonXmlRootElement(localName = "bean-node")
@JsonRootName("bean-node")
public class BeanNode {

    @JsonProperty("bean-name")
    private String beanName;

    @JsonProperty("class")
    private Class<?> clazz;

//    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonUnwrapped(enabled = false)
    @JsonProperty("related-beans")
    private List<BeanNode> related = new ArrayList<>();

    public BeanNode(String beanName, Class<?> clazz) {
        this.beanName = beanName;
        this.clazz = clazz;
    }

    public void addRelated(BeanNode node) {
        this.related.add(node);
    }

    public void removeRelated(BeanNode node) {
        this.related.remove(node);
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<BeanNode> getRelated() {
        return List.copyOf(related);
    }

    @Override
    public String toString() {
        return "BeanNode{" +
                "beanName='" + beanName + '\'' +
                "class='" + clazz + '\'' +
                ", related=" + related +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanNode beanNode = (BeanNode) o;
        return Objects.equals(beanName, beanNode.beanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName);
    }
}
