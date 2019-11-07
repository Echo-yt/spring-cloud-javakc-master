package com.zhou.javakc.ccds.center.controller;

import com.zhou.javakc.ccds.center.service.CenterService;
import com.zhou.javakc.component.data.entity.ccds.Center;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/24 14:59
 */
@Api(value = "银行卡系统-营销中心")
@RestController
@RequestMapping("ccdsCenter")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping("center")
    public Page<Center> query(Center entity)
    {
        return centerService.findAll(entity);
    }

    @PostMapping("center")
    public Center save(@RequestBody Center entity)
    {
        return centerService.save(entity);
    }

    @DeleteMapping("center/{id}")
    public void delete(@PathVariable String id)
    {
        centerService.delete(id);
    }

    @GetMapping("center/load")
    public Center load(String id)
    {
        return centerService.get(id);
    }
}
