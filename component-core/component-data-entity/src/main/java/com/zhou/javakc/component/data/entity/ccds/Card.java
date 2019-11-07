package com.zhou.javakc.component.data.entity.ccds;

import com.zhou.javakc.component.data.entity.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 11:21
 */

@Getter
@Setter
@Entity
@Table(name = "ccds_card")
@ToString
public class Card extends Base implements Serializable {

    /**
     * 卡片编号
     */
    @Id
    @Column(name = "card_id")
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String cardId;

    /**
     * 卡片名称
     */
    @Size(min=3, max=16, message = "卡片名称长度要求在3-16位之间")
    @Column(name = "card_name")
    private String cardName;

    /**
     * 卡片类别
     */
    @Column(name = "card_type")
    private String cardType;

    /**
     * 卡片品牌
     */
    @Column(name = "card_brand")
    private String cardBrand;

    /**
     * 卡片级别
     */
    @Column(name = "card_level_state")
    private Integer cardLevelState;

    /**
     * BIN码
     */
    @Column(name = "card_bin")
    private String cardBin;

    /**
     * 产品代码
     */
    @Column(name = "card_product_code")
    private String cardProductCode;

    /**
     * 卡版代码
     */
    @Column(name = "card_code")
    private String cardCode;

    /**
     * 特殊工艺：0无 1有
     */
    @Column(name = "card_special_craft")
    private Integer cardSprcialCraft;

    /**
     * 特殊工艺描述
     */
    @Column(name = "craft_decript")
    private String craftDecript;

    /**
     * 年费代码
     */
    @Column(name = "card_annual_fee")
    private String cardAnnualFee;

    /**
     * 所有元素
     */
    @Column(name = "card_all")
    private Integer cardAll;

    /**
     * 所有元素描述
     */
    @Column(name = "all_detail")
    private String allDetail;

    /**
     * 卡号格式：0默认 1 其他
     */
    @Column(name = "card_format")
    private Integer cardFormat;

    /**
     * 卡号格式描述
     */
    @Column(name = "format_detail")
    private String formatDetail;

    /**
     * 日期格式：0单日期 1双日期 2有-双日期
     */
    @Column(name = "card_data_format")
    private Integer cardDateFormat;

    /**
     * 持卡人姓名:0:拼音或英文字母 1：中文
     */
    @Column(name = "card_user_name")
    private Integer cardUserName;

    /**
     * 第四行字符 0：无 1：有（说明:如有，请说明位数）
     */
    @Column(name = "card_character")
    private Integer cardCharacter;

    /**
     * 第四行字符 说明
     */
    @Column(name = "chara_detail")
    private String charaDetail;

    /**
     * 彩照个人化 0：正面整幅并覆保护膜（DIY卡）1：正面局部并覆保护膜 2：反面局部并覆保护膜
     */
    @Column(name = "card_photo")
    private Integer cardPhoto;

    /**
     * 卡覆膜 0：需要 1：不需要
     */
    @Column(name = "card_filim")
    private Integer cardFirm;

    /**
     * 凹字格式0:4+3  1:3(VISA)  2:15+3
     */
    @Column(name = "card_concave")
    private Integer cardConcave;

    /**
     * 写磁情况 0:第一隧道 1：第二隧道  2：第三隧道
     */
    @Column(name = "card_magnetic")
    private Integer cardMagnetic;

    /**
     * 级别id
     */
    @ManyToOne
    @JoinColumn(name = "card_level_id")
    private CardLevel cardLevel;

    /**
     * 外键：邮包号(邮包表的主键)
     */
    @Column(name = "parcel_number")
    private String parcelNumber;

    /**
     * 外键：用户id(用户表主键)
     */
    @Column(name = "user_id")
    private String userId;

}