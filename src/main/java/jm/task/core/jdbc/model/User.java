package jm.task.core.jdbc.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String lastName;

    private Byte age;

    public User() {
    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", age = " + age +
                ']';
    }

    @Override
    public int hashCode() {
        int result = 37;
        result = 37 * result + (int)(id - (id >>> 32));
        result = 37 * result + name.hashCode() + lastName.hashCode();
        result = 37 * result + (int)age;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        User other = (User) obj;
        return (id.equals(other.id) &&
                name.equals(other.name) &&
                lastName.equals(other.lastName) &&
                age.equals(other.age));
    }
}
