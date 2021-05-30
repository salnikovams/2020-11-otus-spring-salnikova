package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.LimitConditionDTO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LIM_LIMITCONDITION")
/**
 * Объект LimitCondition Условия отбора. Условия по которым отбирается лимит, под который попадет объект.
 * Condition_id идентификатор
 * Limit_id идентификатор лимита
 * ConditionKind вид условия (в общем случае тут может быть валюта, филиал, участник что угодно, для нас сейчас это просто один из пара Условие-Значение)
 * ConditionValue значение условия
 */
public class LimitCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CONDITIONKIND", nullable = false)
    private Integer conditionKind;

    @Column(name = "CONDITIONVALUE", nullable = false)
    private Long conditionValue;

    @JoinColumn(name = "LIMITID")
    @ManyToOne(targetEntity = Limit.class)
    private Limit limit;

    public LimitCondition(Integer conditionKind, Long conditionValue) {
        this.conditionKind = conditionKind;
        this.conditionValue = conditionValue;
    }

    @Override
    public String toString() {
        return "Condtion{" +
                "id=" + id +
                ", conditionKind='" + conditionKind + '\'' +
                ", conditionValue='" + conditionValue + '\'' +
                '}';
    }

    public LimitConditionDTO toDTO () {
        LimitConditionDTO dto = new LimitConditionDTO();
        dto.setId(id);
        dto.setConditionKind (conditionKind);
        dto.setConditionValue (conditionValue);
        dto.setLimitId(limit.getId());
        return dto;
    }
}
