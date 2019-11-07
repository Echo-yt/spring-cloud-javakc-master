package com.zhou.javakc.ccds.level.dao;

import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import com.zhou.javakc.component.data.jpa.base.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:50
 */
public interface LevelDao extends BaseDao<CardLevel, String> {

    @Query("select c.cardLevelId,c.cardLevelName from CardLevel c where c.levelState = ?1 ")
    public List findByLevelState(Integer state);

}
