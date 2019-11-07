package com.zhou.javakc.ccds.card.dao;

import com.zhou.javakc.component.data.entity.ccds.Card;
import com.zhou.javakc.component.data.jpa.base.BaseDao;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:50
 */
public interface CardDao extends BaseDao<Card, String> {

    @Query("select new map(count(c.cardBrand) as value,c.cardBrand as name) from Card c group by c.cardBrand")
    public List<Map<String,Object>> queryCardBrand();

    @Query("select new map(count(c.cardName) as value,c.cardName as name) from Card c group by c.cardName")
    public List<Map<String,Object>> queryCardName();



}
