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
 * @date 2019/10/19 15:36
 */
@Getter
@Setter
@Entity
@Table(name = "ccds_center")
public class Center  extends Base implements Serializable {
    /**
     * 营销中心主键
     */
    @Id
    @Column(name = "center_id")
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String centerId;

    /**
     * 营销中心名称
     */
    @Column(name = "center_name")
    private String centerName;

    /**
     * 营销中心添加人
     */
    @Column(name = "center_adduserid")
    private String centerAdduserId;

    /**
     * 营销中心修改人
     */
    @Column(name = "center_updateuserid")
    private String centerUpdateuserid;

    /**
     * 营销中心地区编号
     */
    @Column(name = "center_num")
    private String centerNum;

}
