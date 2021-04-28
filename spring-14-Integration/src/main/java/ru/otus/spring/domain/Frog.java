package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Frog")
public class Frog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "STATE")
    private String state;


    public Frog(long id, String name,  String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Frog(String name) {
        this.name = name;
    }
}
