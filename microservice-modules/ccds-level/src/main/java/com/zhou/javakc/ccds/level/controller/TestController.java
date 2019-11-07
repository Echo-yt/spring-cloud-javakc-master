package com.zhou.javakc.ccds.level.controller;


import com.zhou.javakc.ccds.level.service.TestService;
import com.zhou.javakc.component.data.entity.ccds.Test;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:46
 */

//@Api(value = "银行卡系统-级别表")
@RestController
@RequestMapping("ccdsLevel")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("level/saveAll")
    public void saveAll() throws Exception {
        System.out.println("即将上传");
        String path = "C:\\Users\\ASUS\\Desktop\\b.xlsx";
        File file = new File(path);
        if (file.exists()) {
            InputStream input = new FileInputStream(file);
            //1.根据用户上传的excel文档，动态创建响应的解析对象
            Workbook workbook = null;
            if (path.endsWith("xlsx")) {
                //version 07+
                workbook = new XSSFWorkbook(input);
                //Name: /xl/workbook.xml -Content Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml
                //System.out.println("xlsx");
            } else {
                //version 97-03
                workbook = new HSSFWorkbook(input);
                //org.apache.poi.hssf.usermodel.HSSFWorkbook@4c203ea1
            }
            //System.out.println(workbook);
            //2.准备从excel中进行数据提取
            //excel中sheet的总个数
            int sheets = workbook.getNumberOfSheets();

            List<Test> list = new ArrayList<>();

            for (int i = 0; i < sheets; i++) {
                Sheet sheet = workbook.getSheetAt(0);
                //从sheet中获取总行数
                int rows = sheet.getPhysicalNumberOfRows();

                for (int j = 1; j < rows; j++) {

                    Row row = sheet.getRow(j);
                    //从row中获取总列数
                    //int cells=row.getPhysicalNumberOfCells();
                    Cell cell1 = row.getCell(0);
                    Cell cell2 = row.getCell(1);
                    Cell cell3 = row.getCell(2);
                    Cell cell4 = row.getCell(3);
                    Cell cell5 = row.getCell(4);
                    System.out.println(cell1.getStringCellValue() + " " +
                            cell2.getNumericCellValue() + " " +
                            cell3.getNumericCellValue() + " " +
                            cell4.getStringCellValue() + " " +
                            cell5.getDateCellValue());
                    Test entity = new Test();
                    entity.setTestName(cell1.getStringCellValue());
                    entity.setTestAge((int)cell2.getNumericCellValue());
                    entity.setTestSex((int)cell3.getNumericCellValue());
                    entity.setTestAddress(cell4.getStringCellValue());
                    entity.setTestBirth(cell5.getDateCellValue());

                    list.add(entity);
                }

                System.out.println("length:"+list.size());
            }
            //保存list 批量添加
            System.out.println("保存list 批量添加");
//            testService.saveAll(list);
            testService.save(list);
        } else {
            System.out.println("文件不存在");
        }
    }
}
