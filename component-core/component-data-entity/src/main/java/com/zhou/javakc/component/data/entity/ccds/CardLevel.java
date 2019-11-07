package com.zhou.javakc.component.data.entity.ccds;

import com.zhou.javakc.component.data.entity.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 15:48
 */
@Getter
@Setter
@Entity
@Table(name = "card_level_detail")
public class CardLevel extends Base implements Serializable {
    /**
     * 级别编号
     */
    @Id
    @Column(name = "card_level_id")
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String cardLevelId;

    /**
     * 级别名
     */
    @Column(name = "card_level_name")
    private String cardLevelName;

    /**
     * 级别种类
     */
    @Column(name = "level_state")
    private Integer levelState;

}
