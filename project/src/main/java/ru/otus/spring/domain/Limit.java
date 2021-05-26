package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.CurrencyDTO;
import ru.otus.spring.dto.LimitConditionDTO;
import ru.otus.spring.dto.LimitDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "LIM_LIMIT")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "STARTDATE")
    private Date startDate;

    @Column(name = "ENDDATE")
    private Date endDate;

    @Column(name = "VALUE")
    private BigDecimal breakConditionValue;

    @OneToMany(mappedBy = "limit", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<LimitCondition> limitConditionList;

    @JoinColumn(name = "CURRENCYID")
    @ManyToOne(targetEntity = Currency.class )
    private Currency currency;


    public Limit(String name, String comment, Date startDate, Date endDate, BigDecimal breakConditionValue, Currency currency) {
        this.name = name;
        this.comment = comment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.breakConditionValue = breakConditionValue;
        this.currency = currency;
    }


    public LimitDTO toDTO () {

        CurrencyDTO currencyDTO = currency.toDTO();

        LimitDTO limitDTO = new LimitDTO();

        limitDTO.setId(id);
        limitDTO.setName(name);
        limitDTO.setComment(comment);
        limitDTO.setStartDate(startDate);
        limitDTO.setEndDate(endDate);
        limitDTO.setCurrency(currencyDTO);
        limitDTO.setBreakConditionValue(breakConditionValue);

        List <LimitConditionDTO> limitConditionDTOList = new ArrayList<>();
        for (LimitCondition condition: limitConditionList){
            LimitConditionDTO limitConditionDTO = condition.toDTO();
            limitConditionDTOList.add(limitConditionDTO);
        }
        limitDTO.setLimitConditionList(limitConditionDTOList);

        return limitDTO;
    }


}
