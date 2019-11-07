package com.zhou.javakc.ccds.level.controller;


import com.zhou.javakc.ccds.level.service.LevelService;
import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:46
 */

//@Api(value = "银行卡系统-级别表")
@RestController
@RefreshScope
@RequestMapping("ccdsLevel")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping("level")
    public Page<CardLevel> query(CardLevel cardLevel)
    {
        return levelService.findAll(cardLevel);
    }

    @GetMapping("level/Name")
    public List queryName(Integer state)
    {
        return levelService.findByLevelState(state);
    }

    @PostMapping("level")
    public CardLevel save(@RequestBody CardLevel entity)
    {
        return levelService.save(entity);
    }

    @DeleteMapping("level/{id}")
    public void delete(@PathVariable String id)
    {
        levelService.delete(id);
    }

    @GetMapping("level/load")
    public CardLevel load(String id)
    {
        return levelService.get(id);
    }

    @PostMapping("level/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        String name = UUID.randomUUID().toString();
//        try {
//            String path = ResourceUtils.getURL("classpath:").getPath();
//            File uploadFile = new File(path + File.separator + name);
//            file.transferTo(uploadFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return name;

        System.out.println("---------------------------------");
    }

}