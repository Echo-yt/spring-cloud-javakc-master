package com.zhou.javakc.component.data.entity.ccds;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhou.javakc.component.data.entity.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/22 17:00
 */

@Getter
@Setter
@Entity
@Table(name = "ccds_test")
public class Test extends Base implements Serializable {

    /**
     * 卡片编号
     */
    @Id
    @Column(name = "test_id")
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String testId;

    /**
     * 用户性别
     */
    @Column(name = "test_name")
    private String testName;

    /**
     * 用户性别
     */
    @Column(name = "test_age")
    private Integer testAge;

    /**
     * 用户性别
     */
    @Column(name = "test_sex")
    private Integer testSex;

    /**
     * 用户性别
     */
    @Column(name = "test_address")
    private String testAddress;

    /**
     * 用户性别
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "test_birth")
    private Date testBirth;
}
