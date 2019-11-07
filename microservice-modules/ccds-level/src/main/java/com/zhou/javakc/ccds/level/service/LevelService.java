package com.zhou.javakc.ccds.level.service;

import com.zhou.javakc.ccds.level.dao.LevelDao;
import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import com.zhou.javakc.component.data.jpa.base.BaseService;
import com.zhou.javakc.component.data.jpa.dynamic.criterion.Criteria;
import com.zhou.javakc.component.data.jpa.dynamic.restrictions.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:50
 */
@Service
@Transactional(readOnly = true)
public class LevelService extends BaseService<CardLevel, LevelDao> {

    @Cacheable(value = "level",key = "'ccdslevel'+#entity.offset")
    public Page<CardLevel> findAll(CardLevel entity)
    {
        Criteria<CardLevel> criteria = new Criteria<>();

        if(!StringUtils.isEmpty(entity.getCardLevelName()))
        {
            criteria.add(Restrictions.like("cardLevelName", entity.getCardLevelName()));
        }
        if(!StringUtils.isEmpty(entity.getLevelState()))
        {
            criteria.add(Restrictions.eq("levelState", entity.getLevelState()));
        }
        PageRequest pageable = PageRequest.of(entity.getOffset(),entity.getLimit());
        return dao.findAll(criteria,pageable);
    }

    public List findByLevelState(Integer state)
    {
        return dao.findByLevelState(state);
    }

    @CacheEvict(value = "level",key = "'ccdslevel'",allEntries = true)
    @Transactional(readOnly = false)
    public CardLevel save(CardLevel entity)
    {
        if(!StringUtils.isEmpty(entity.getCardLevelId()))
        {
            entity.setRdate(new Timestamp(System.currentTimeMillis()));
        }
        else{
            entity.setUdate(new Timestamp(System.currentTimeMillis()));
        }
        return dao.save(entity);
    }

    @CacheEvict(value = "level",key = "'ccdslevel'",allEntries = true)
    @Transactional(readOnly = false)
    public void delete(String id)
    {
        dao.deleteById(id);
    }

}