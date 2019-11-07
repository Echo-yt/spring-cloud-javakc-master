package com.zhou.javakc.ccds.parcel.controller;

import com.zhou.javakc.ccds.parcel.service.ParcelService;
import com.zhou.javakc.component.data.entity.ccds.Parcel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/24 14:42
 */
@Api(value = "银行卡系统-邮包表")
@Controller
@RequestMapping("ccdsParcel")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;
    String name=null;
    String path;

    private File files;

    @GetMapping("parcel")
    @ResponseBody
    public Page<Parcel> query(Parcel entity) {
        return parcelService.findAll(entity);
    }

    @ResponseBody
    @PostMapping("parcel")
    public Parcel save(@RequestBody Parcel entity) {
        entity.setRdate(new Timestamp(System.currentTimeMillis()));
        entity.setUdate(new Timestamp(System.currentTimeMillis()));
        return parcelService.save(entity);
    }

    @ResponseBody
    @DeleteMapping("parcel/{id}")
    public void delete(@PathVariable String id) {
        parcelService.delete(id);
    }

    @ResponseBody
    @GetMapping("parcel/load")
    public Parcel load(String id) {
        return parcelService.get(id);
    }

    @PostMapping("parcel/upload")
    public void upload(@RequestParam("file") MultipartFile file)
    {
        System.out.println("I'm coming~~~~");
        name = UUID.randomUUID().toString().replaceAll("-","");
        try {
            path = "E:\\HuiCai\\project\\Forth\\upload";
            path=path + File.separator + name;
            files = new File(path);
            file.transferTo(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("parcel/uploadSave")
    public void uploadSave(@RequestBody Parcel entity) throws Exception {
//        System.out.println("我进去啦");
        entity.setParcelNumber(name);
        parcelService.saveFile(entity,files,path);
//        System.out.println("结束，over");
//        int size=0;
//        //保存路径
//        String path = "E:\\HuiCai\\project\\Forth\\upload";
//
//        //用户上传真实名称
//        String fileName = files.getOriginalFilename();
//        //由系统自动生成唯一文件名称
//        String name = UUID.randomUUID().toString();
//        name=name.replaceAll("-","");
//
//        List<Card> list = new ArrayList<>();
//
//        path=path+"\\"+fileName;
//        System.out.println("文件名称："+path);
//        File uploadFile = new File(path);
//
//        files.transferTo(uploadFile);
//        try {
//            if (uploadFile.exists()) {
//                InputStream input = new FileInputStream(uploadFile);
//                Workbook workbook = null;
//                if (path.endsWith("xlsx")) {
//                    //version 07+
//                    workbook = new XSSFWorkbook(input);
//                } else {
//                    workbook = new HSSFWorkbook(input);
//                }
//                int sheets = workbook.getNumberOfSheets();
//
//                for (int i = 0; i < sheets; i++) {
//                    Sheet sheet = workbook.getSheetAt(0);
//                    //从sheet中获取总行数
//                    int rows = sheet.getPhysicalNumberOfRows();
//
//                    for (int j = 1; j < rows; j++) {
//
//                        Row row = sheet.getRow(j);
//                        //从row中获取总列数
//                        //int cells=row.getPhysicalNumberOfCells();
//                        Cell cell1 = row.getCell(0);
//                        Cell cell2 = row.getCell(1);
//                        Cell cell3 = row.getCell(2);
//                        Cell cell4 = row.getCell(3);
//                        Cell cell5 = row.getCell(4);
//                        Cell cell6 = row.getCell(5);
//                        Cell cell7 = row.getCell(6);
//                        Cell cell8 = row.getCell(7);
//                        Cell cell9 = row.getCell(8);
//                        Cell cell10 = row.getCell(9);
//                        Cell cell11 = row.getCell(10);
//                        Cell cell12 = row.getCell(11);
//                        Cell cell13 = row.getCell(12);
//
//                        Card entity = new Card();
//                        entity.setCardName(cell1.getStringCellValue());
//                        entity.setCardType(cell2.getStringCellValue());
//                        entity.setCardBrand(cell3.getStringCellValue());
//                        entity.setCardLevelState((int) cell4.getNumericCellValue());
//                        entity.setCardBin(cell5.getStringCellValue());
//                        entity.setCardCode(cell6.getStringCellValue());
//                        entity.setCardProductCode(cell7.getStringCellValue());
//                        entity.setCardSprcialCraft((int) cell8.getNumericCellValue());
//                        entity.setCraftDecript(cell9.getStringCellValue());
//                        entity.setCardAnnualFee(cell10.getStringCellValue());
//                        entity.setCardAll((int) cell11.getNumericCellValue());
//                        entity.setAllDetail(cell12.getStringCellValue());
//
//                        CardLevel level=new CardLevel();
//                        level.setCardLevelId(cell13.getStringCellValue());
//                        entity.setCardLevel(level);
//
//                        System.out.println(cell1.getStringCellValue()
//                                + " - " + cell12.getStringCellValue()
//                                + " - " + cell13.getStringCellValue());
//
//                        list.add(entity);
//                        size++;
//                    }
//                }
//            }
//            else {
//                System.out.println("文件不存在");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Parcel parcel=new Parcel();
//        parcel.setParcelNumber(name);
//        System.out.println(details);
//        parcel.setExendUnitCode(details.getExendUnitCode());
//        parcel.setSender(details.getSender());
//        System.out.println("size:"+size);
//        parcel.setReportTotalno(size);
//        parcel.setRealityReceiveTotalno(size);
//        parcel.setCards(list);
//
//        parcelService.save(parcel);
    }

    @GetMapping("parcel/export")
    public void export(HttpServletResponse response) throws IOException {
        parcelService.export(response);
    }

    @GetMapping("toExport/{parcelNumber}")
    public void toExport(HttpServletResponse response,@PathVariable String parcelNumber) throws IOException {
        parcelService.toExport(response,parcelNumber);
    }

}