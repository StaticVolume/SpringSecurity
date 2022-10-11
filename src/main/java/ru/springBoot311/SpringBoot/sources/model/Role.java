package ru.springBoot311.SpringBoot.sources.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @NotEmpty
    @Size(min = 2, max = 128, message = "role name must be greater than 2 and less than 128")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User person;

    public Role () {}

    public Role(Long id, String name, User person) {
        this.id = id;
        this.name = name;
        this.person = person;
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
    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", person=" + person +
                '}';
    }
}
