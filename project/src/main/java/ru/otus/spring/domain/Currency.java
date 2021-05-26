package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.CurrencyDTO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LIM_CURRENCY")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ISO_CODE", nullable = false, unique = true)
    private String isoCode;


    @Column(name = "ALPHA_CODE", nullable = false, unique = true)
    private String alphaCode;

    public CurrencyDTO toDTO () {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setIsoCode(isoCode);
        dto.setAlphaCode(alphaCode);

        return dto;
    }
}
