package com.zhou.javakc.ccds.card.service;

import com.zhou.javakc.ccds.card.dao.CardDao;
import com.zhou.javakc.component.data.entity.ccds.Card;
import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import com.zhou.javakc.component.data.jpa.base.BaseService;
import com.zhou.javakc.component.data.jpa.dynamic.criterion.Criteria;
import com.zhou.javakc.component.data.jpa.dynamic.restrictions.Restrictions;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:50
 */
@Service
public class CardService extends BaseService<Card, CardDao> {

    public Page<Card> findAll(Card card) {
        Criteria<Card> criteria = new Criteria<>();

        if (!StringUtils.isEmpty(card.getCardName())) {
            criteria.add(Restrictions.eq("cardName", card.getCardName()));
        }
        if (!StringUtils.isEmpty(card.getCardType())) {
            criteria.add(Restrictions.like("cardType", card.getCardType()));
        }
        if (!StringUtils.isEmpty(card.getCardBrand())) {
            criteria.add(Restrictions.like("cardBrand", card.getCardBrand()));
        }


        //注册日期
        System.out.println("----" + card.getSdate() + "--日期进来了--" + card.getEdate() + "----");
        if (!StringUtils.isEmpty(card.getSdate()) && !StringUtils.isEmpty(card.getEdate())) {
            System.out.println("------------------------------------------------------");
            criteria.add(Restrictions.between("rdate", card.getSdate(), card.getEdate()));
        }

/*
        //起始注册日期
        if(!StringUtils.isEmpty(card.getSdate()))
        {
            criteria.add(Restrictions.gte("rdate", card.getSdate()));
        }
        //终止注册日期
        if(!StringUtils.isEmpty(card.getEdate()))
        {
            criteria.add(Restrictions.lt("rdate", card.getEdate()));
        }
        //区间查询
        if(!StringUtils.isEmpty(card.getSdate()) && !StringUtils.isEmpty(card.getEdate()))
        {
            criteria.add(Restrictions.between("rdate", card.getSdate(), card.getEdate()));
        }
*/

        PageRequest pageable = PageRequest.of(card.getOffset(), card.getLimit());
        return dao.findAll(criteria, pageable);
    }

    public List<Map<String, Object>> queryCardBrand() {
        return dao.queryCardBrand();
    }

    public List<Map<String, Object>> queryCardName() {
        return dao.queryCardName();
    }

    @Transactional(readOnly=false)
    public Card save(Card entity)
    {
        if(StringUtils.isEmpty(entity.getCardId()))
        {
            entity.setRdate(new Timestamp(System.currentTimeMillis()));
        }
        else
        {
            entity.setUdate(new Timestamp(System.currentTimeMillis()));
        }
        return dao.save(entity);
    }
}