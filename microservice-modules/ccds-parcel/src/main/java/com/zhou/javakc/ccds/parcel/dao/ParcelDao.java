package com.zhou.javakc.ccds.parcel.dao;

import com.zhou.javakc.component.data.entity.ccds.Parcel;
import com.zhou.javakc.component.data.jpa.base.BaseDao;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/24 14:41
 */
public interface ParcelDao extends BaseDao<Parcel, String> {

    //查询邮包表中各卡级别的数量
    @Query("select count(c.cardLevel.cardLevelName) from Card c " +
            "where c.parcelNumber=?1 and c.cardLevel.cardLevelName=?2")
    public Integer LevelNamecount(String parcelNumber,String cardLevelName);
}