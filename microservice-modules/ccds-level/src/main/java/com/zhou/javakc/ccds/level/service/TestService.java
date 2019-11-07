package com.zhou.javakc.ccds.level.service;

import com.zhou.javakc.ccds.level.dao.TestDao;
import com.zhou.javakc.component.data.entity.ccds.Test;
import com.zhou.javakc.component.data.jpa.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:50
 */
@Service
public class TestService extends BaseService<Test, TestDao> {

    public void saveAll(List<Test> list)
    {
        System.out.println("service"+list.size());
        dao.saveAll(list);
    }
}
