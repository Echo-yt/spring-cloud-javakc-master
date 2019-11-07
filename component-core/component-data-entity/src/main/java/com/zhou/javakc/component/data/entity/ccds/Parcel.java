package com.zhou.javakc.component.data.entity.ccds;

import com.zhou.javakc.component.data.entity.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 15:26
 */
@Getter
@Setter
@Entity
@Table(name = "ccds_parcel")
public class Parcel  extends Base implements Serializable {
    /**
     * 邮包号
     */
    @Id
    @Column(name = "parcel_number")
//    @GeneratedValue(generator = "generator")
//    @GenericGenerator(name = "generator", strategy = "uuid")
    private String parcelNumber;
    /**
     * 推广单位代码
     */
    @Column(name = "exend_unit_code")
    private Integer exendUnitCode;
    /**
     * 寄件人
     */
//    @NotBlank(message="寄件人不能为空")
    @Column(name = "sender")
    private String sender;
    /**
     * 递送日期
     */
    @Column(name = "sendate")
    private Timestamp sendate;
    /**
     * 报表总件数
     */
//    @NotBlank(message="卡片名称不能为空")
    @Column(name = "report_totalno")
    private Integer reportTotalno;
    /**
     * 实收总件数
     */
//    @NotBlank(message="卡片名称不能为空")
    @Column(name = "reality_receive_totalno")
    private Integer realityReceiveTotalno;

    /**
     * 营销中心外键
     */
    @Column(name = "center_id")
    private String centerId;

    /**
     * 通宝金普件
     */
    @Column(name = "tongbaojinpujian")
    private Integer tongbaojinpujian;
    /**
     * 分中心金普件
     */
    @Column(name = "fenzhongxinjinpujian")
    private Integer fenzhongxinjinpujian;
    /**
     * 普通加急
     */
    @Column(name = "commonurgent")
    private Integer commonurgent;
    /**
     * 员工件金普件
     */
    @Column(name = "yuangongjinpujian")
    private Integer yuangongjinpujian;
    /**
     * 散件金普件
     */
    @Column(name = "sanjianjinpujian")
    private Integer sanjianjinpujian;
    /**
     * 商务卡
     */
    @Column(name = "bussinesscard")
    private Integer bussinesscard;
    /**
     * 标准白金
     */
    @Column(name = "biaozhunbaijin")
    private Integer biaozhunbaijin;
    /**
     * 豪白
     */
    @Column(name = "haobai")
    private Integer haobai;
    /**
     * 钻石白金理财
     */
    @Column(name = "zuanshibaijinlicai")
    private Integer zuanshibaijinlicai;
    /**
     * 通宝分期
     */
    @Column(name = "tongbaofenqi")
    private Integer tongbaofenqi;
    /**
     * 通宝豪钻
     */
    @Column(name = "tongbaohaozuan")
    private Integer tongbaohaozuan;
    /**
     * 新E贷
     */
    @Column(name = "xinedai")
    private Integer xinedai;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parcel_number")
    private List<Card> cards;
}
