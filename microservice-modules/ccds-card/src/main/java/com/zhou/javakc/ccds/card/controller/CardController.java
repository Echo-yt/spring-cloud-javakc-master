package com.zhou.javakc.ccds.card.controller;

import com.zhou.javakc.ccds.card.service.CardService;
import com.zhou.javakc.component.data.entity.ccds.Card;
import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:46
 */

@Api(value = "银行卡系统-银行卡表")
@Controller
@RequestMapping("ccdsCard")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("card")
    @ResponseBody
    public Page<Card> query(Card card) {
        return cardService.findAll(card);
    }

//    @GetMapping("level/Name")
//    public List queryName(Integer state)
//    {
//        return levelService.findByCardLevelState(state);
//    }

    @PostMapping("card")
    @ResponseBody
    public Card save(@RequestBody Card entity) {
        entity.setRdate(new Timestamp(System.currentTimeMillis()));
        entity.setUdate(new Timestamp(System.currentTimeMillis()));
        return cardService.save(entity);
    }

    @DeleteMapping("card/{id}")
    @ResponseBody
    public void delete(@PathVariable String id) {
        cardService.delete(id);
    }

    @GetMapping("card/load")
    @ResponseBody
    public Card load(String id) {
        return cardService.get(id);
    }

    @GetMapping("card/chartBrand")
    @ResponseBody
    public List<Map<String, Object>> queryCardBrand() {
        return cardService.queryCardBrand();
    }

    @GetMapping("card/chartName")
    @ResponseBody
    public List<Map<String, Object>> queryCardName() {
        return cardService.queryCardName();
    }

//    @GetMapping("card/saveAll")
//    public void saveAll(HttpServletRequest request) throws Exception {
//        String path = request.getParameter("path");
//        String name = request.getParameter("name");
//
//    }
}