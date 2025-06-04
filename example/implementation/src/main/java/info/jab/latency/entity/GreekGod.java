package info.jab.latency.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity class representing a Greek God in the database.
 * Uses Spring Data JDBC annotations for object-relational mapping.
 */
@Table("greek_god")
public class GreekGod {

    @Id
    private Long id;

    @Column("name")
    private String name;

    /**
     * Default constructor required by Spring Data JDBC.
     */
    public GreekGod() {
    }

    /**
     * Constructor for creating a GreekGod with a name.
     * 
     * @param name the name of the Greek god
     */
    public GreekGod(String name) {
        this.name = name;
    }

    /**
     * Constructor for creating a GreekGod with id and name.
     * 
     * @param id the unique identifier
     * @param name the name of the Greek god
     */
    public GreekGod(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GreekGod{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GreekGod greekGod = (GreekGod) o;

        if (id != null ? !id.equals(greekGod.id) : greekGod.id != null) return false;
        return name != null ? name.equals(greekGod.name) : greekGod.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
} 