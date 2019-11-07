package com.zhou.javakc.ccds.center.service;

import com.zhou.javakc.ccds.center.dao.CenterDao;
import com.zhou.javakc.component.data.entity.ccds.Center;
import com.zhou.javakc.component.data.jpa.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/24 14:59
 */
@Service
public class CenterService extends BaseService<Center, CenterDao> {
    public Page<Center> findAll(Center entity)
    {
        /*Criteria<Card> criteria = new Criteria<>();

        if(!StringUtils.isEmpty(card.getCardName()))
        {
            criteria.add(Restrictions.eq("cardName", card.getCardName()));
        }*/

        PageRequest pageable = PageRequest.of(entity.getOffset(),entity.getLimit());
        return dao.findAll(pageable);
    }
}
