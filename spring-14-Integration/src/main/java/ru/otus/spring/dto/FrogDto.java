package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Frog;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrogDto {

    private long id;

    private String name;

   private String state;


    public FrogDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public FrogDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static FrogDto toDto(Frog frog) {
        return new FrogDto(frog.getId(), frog.getName(), frog.getState());
    }
}
