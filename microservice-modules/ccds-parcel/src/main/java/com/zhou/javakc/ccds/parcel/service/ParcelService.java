package com.zhou.javakc.ccds.parcel.service;

import com.zhou.javakc.ccds.parcel.dao.ParcelDao;
import com.zhou.javakc.component.data.entity.ccds.Card;
import com.zhou.javakc.component.data.entity.ccds.CardLevel;
import com.zhou.javakc.component.data.entity.ccds.Parcel;
import com.zhou.javakc.component.data.entity.system.User;
import com.zhou.javakc.component.data.jpa.base.BaseService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/24 14:42
 */
@Service
public class ParcelService extends BaseService<Parcel, ParcelDao> {

    public Page<Parcel> findAll(Parcel entity) {

        PageRequest pageable = PageRequest.of(entity.getOffset(), entity.getLimit());

        Page<Parcel> page = dao.findAll(pageable);
        List<Parcel> list = page.getContent();
        list.forEach(a -> {
            System.out.println(a.getParcelNumber());
            a.setFenzhongxinjinpujian(dao.LevelNamecount(a.getParcelNumber(), "分中心金普件"));
            a.setCommonurgent(dao.LevelNamecount(a.getParcelNumber(), "普通加急"));
            System.out.println("普通加急:" + dao.LevelNamecount(a.getParcelNumber(), "普通加急"));
            a.setYuangongjinpujian(dao.LevelNamecount(a.getParcelNumber(), "员工件金普件"));
            a.setSanjianjinpujian(dao.LevelNamecount(a.getParcelNumber(), "散件金普件"));
            a.setBussinesscard(dao.LevelNamecount(a.getParcelNumber(), "商务卡"));
            a.setBiaozhunbaijin(dao.LevelNamecount(a.getParcelNumber(), "标准白金"));
            a.setHaobai(dao.LevelNamecount(a.getParcelNumber(), "豪白"));
            a.setZuanshibaijinlicai(dao.LevelNamecount(a.getParcelNumber(), "钻石白金理财"));
            a.setTongbaofenqi(dao.LevelNamecount(a.getParcelNumber(), "通宝分期"));
            a.setTongbaohaozuan(dao.LevelNamecount(a.getParcelNumber(), "通宝豪钻"));
            a.setXinedai(dao.LevelNamecount(a.getParcelNumber(), "新E贷"));
            a.setTongbaojinpujian(dao.LevelNamecount(a.getParcelNumber(), "通宝金普件"));

        });
        return page;
    }

    public Integer LevelNamecount(String parcelNumber, String cardLevelName) {
        return dao.LevelNamecount(parcelNumber, cardLevelName);
    }

    @Transactional(readOnly = false)
    public Parcel saveFile(Parcel parcel, File file,String path) throws Exception {

        List<Card> list = new ArrayList<>();
        int size = 0;

        try {
            if (file.exists()) {
                InputStream input = new FileInputStream(file);
                Workbook workbook = null;
                if (path.endsWith("xlsx")) {
                    workbook = new XSSFWorkbook(input);
                } else {
                    workbook = new HSSFWorkbook(input);
                }
                int sheets = workbook.getNumberOfSheets();
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
                        Cell cell6 = row.getCell(5);
                        Cell cell7 = row.getCell(6);
                        Cell cell8 = row.getCell(7);
                        Cell cell9 = row.getCell(8);
                        Cell cell10 = row.getCell(9);
                        Cell cell11 = row.getCell(10);
                        Cell cell12 = row.getCell(11);
                        Cell cell13 = row.getCell(12);

                        Card entity = new Card();
                        entity.setCardName(cell1.getStringCellValue());
                        entity.setCardType(cell2.getStringCellValue());
                        entity.setCardBrand(cell3.getStringCellValue());
                        entity.setCardLevelState((int) cell4.getNumericCellValue());
                        entity.setCardBin(cell5.getStringCellValue());
                        entity.setCardCode(cell6.getStringCellValue());
                        entity.setCardProductCode(cell7.getStringCellValue());
                        entity.setCardSprcialCraft((int) cell8.getNumericCellValue());
                        entity.setCraftDecript(cell9.getStringCellValue());
                        entity.setCardAnnualFee(cell10.getStringCellValue());
                        entity.setCardAll((int) cell11.getNumericCellValue());
                        entity.setAllDetail(cell12.getStringCellValue());

                        CardLevel level = new CardLevel();
                        level.setCardLevelId(cell13.getStringCellValue());
                        entity.setCardLevel(level);

                        list.add(entity);
                        size++;
                    }
                }
                //删除临时文件
                file.delete();
                parcel.setRdate(new Timestamp(System.currentTimeMillis()));

                parcel.setReportTotalno(size);
                parcel.setRealityReceiveTotalno(size);
                parcel.setCards(list);

            } else {
                System.out.println("文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dao.save(parcel);
    }

    @Transactional(readOnly=false)
    public Parcel save(Parcel entity)
    {
        if(StringUtils.isEmpty(entity.getParcelNumber()))
        {
            entity.setRdate(new Timestamp(System.currentTimeMillis()));
        }
        else
        {
            entity.setUdate(new Timestamp(System.currentTimeMillis()));
        }
        return dao.save(entity);
    }

    public void export(HttpServletResponse response) throws IOException {
        //1.查询数据库(需要导出的数据)
        List<Parcel> list = dao.findAll();
        //2.把查询结果集写入到excel中
        HSSFWorkbook workbook = new HSSFWorkbook();

        //workbook字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); //字体高度
        font.setBold(true); //加粗
        font.setFontName("黑体"); //字体

        //workbook样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);

        HSSFSheet sheet = workbook.createSheet("用户列表1");

        //2.1创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setRowStyle(cellStyle);
        titleRow.createCell(0).setCellValue("推广单位代码");
        titleRow.createCell(1).setCellValue("寄件人");
        titleRow.createCell(2).setCellValue("报表总件数");
        titleRow.createCell(3).setCellValue("实收总件数");
        titleRow.createCell(4).setCellValue("注册日期");
        titleRow.createCell(5).setCellValue("修改日期");
        titleRow.createCell(6).setCellValue("递送日期");
        titleRow.createCell(7).setCellValue("通宝金普件");
        titleRow.createCell(8).setCellValue("分中心金普件");
        titleRow.createCell(9).setCellValue("普通急件");
        titleRow.createCell(10).setCellValue("员工件金普件");
        titleRow.createCell(11).setCellValue("散件金普件");
        titleRow.createCell(12).setCellValue("商务卡");
        titleRow.createCell(13).setCellValue("标准白金");
        titleRow.createCell(14).setCellValue("豪白");
        titleRow.createCell(15).setCellValue("钻石白金理财");
        titleRow.createCell(16).setCellValue("通宝分期");
        titleRow.createCell(17).setCellValue("通宝豪钻");
        titleRow.createCell(18).setCellValue("新E贷");

        //2.2创建数据行
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0;i<list.size();i++) {
            Parcel entity = list.get(i);
            HSSFRow dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(entity.getExendUnitCode());
            dataRow.createCell(1).setCellValue(entity.getSender());
            dataRow.createCell(2).setCellValue(entity.getReportTotalno());
            dataRow.createCell(3).setCellValue(entity.getRealityReceiveTotalno());
            String rdate = StringUtils.isEmpty(entity.getRdate())?"":format.format(entity.getRdate());
            dataRow.createCell(4).setCellValue(rdate);
            String udate = StringUtils.isEmpty(entity.getUdate())?"":format.format(entity.getUdate());
            dataRow.createCell(5).setCellValue(udate);
            String sendate = StringUtils.isEmpty(entity.getSendate())?"":format.format(entity.getSendate());
            dataRow.createCell(6).setCellValue(sendate);
            int tongbaojinpujian = entity.getTongbaojinpujian() == null ?0:entity.getTongbaojinpujian();
            dataRow.createCell(7).setCellValue(tongbaojinpujian);
            int fenzhongxinjinpujian = entity.getFenzhongxinjinpujian() == null ?0:entity.getFenzhongxinjinpujian();
            dataRow.createCell(8).setCellValue(fenzhongxinjinpujian);
            int commonurgent = entity.getCommonurgent() == null ?0:entity.getCommonurgent();
            dataRow.createCell(9).setCellValue(commonurgent);
            int yuangongjinpujian = entity.getYuangongjinpujian() == null ?0:entity.getYuangongjinpujian();
            dataRow.createCell(10).setCellValue(yuangongjinpujian);
            int sanjianjinpujian = entity.getSanjianjinpujian() == null ?0:entity.getSanjianjinpujian();
            dataRow.createCell(11).setCellValue(sanjianjinpujian);
            int bussinesscard = entity.getBussinesscard() == null ?0:entity.getBussinesscard();
            dataRow.createCell(12).setCellValue(bussinesscard);
            int biaozhunbaijin = entity.getBiaozhunbaijin() == null ?0:entity.getBiaozhunbaijin();
            dataRow.createCell(13).setCellValue(biaozhunbaijin);
            int haobai = entity.getHaobai() == null ?0:entity.getHaobai();
            dataRow.createCell(14).setCellValue(haobai);
            int zuanshibaijinlicai = entity.getZuanshibaijinlicai() == null ?0:entity.getZuanshibaijinlicai();
            dataRow.createCell(15).setCellValue(zuanshibaijinlicai);
            int tongbaofenqi = entity.getTongbaofenqi() == null ?0:entity.getTongbaofenqi();
            dataRow.createCell(16).setCellValue(tongbaofenqi);
            int tongbaohaozuan = entity.getTongbaohaozuan() == null ?0:entity.getTongbaohaozuan();
            dataRow.createCell(17).setCellValue(tongbaohaozuan);
            int xinedai = entity.getXinedai() == null ?0:entity.getXinedai();
            dataRow.createCell(18).setCellValue(xinedai);

//            ByteArrayInputStream in = new ByteArrayInputStream(entity.getUserPhoto());
//
//            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//            BufferedImage bufferImg = ImageIO.read(in);
//            ImageIO.write(bufferImg, "jpg", byteArrayOut);
//            HSSFClientAnchor anchor = new HSSFClientAnchor(480, 30, 700, 250, (short) 9, i+1, (short) 9, i+1);
//
//            patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        }

        //设置workbook列宽自动调整
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);
        sheet.autoSizeColumn(18);

        //3.把excel写入到响应流中
        response.reset();
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+System.currentTimeMillis()+".xls");

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    public void toExport(HttpServletResponse response,String parcelNumber) throws IOException {

        //1.查询数据库(需要导出的数据)
        Parcel parcel=dao.getOne(parcelNumber);
        //2.把查询结果集写入到excel中
        HSSFWorkbook workbook = new HSSFWorkbook();

        //workbook字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); //字体高度
        font.setBold(true); //加粗
        font.setFontName("黑体"); //字体

        //workbook样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);

        HSSFSheet sheet = workbook.createSheet("用户列表1");

        //2.1创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("卡片名称");
        titleRow.createCell(1).setCellValue("卡片类别");
        titleRow.createCell(2).setCellValue("卡片品牌");
        titleRow.createCell(3).setCellValue("卡片级别");
        titleRow.createCell(4).setCellValue("BIN码");
        titleRow.createCell(5).setCellValue("产品代码");
        titleRow.createCell(6).setCellValue("卡版代码");
        titleRow.createCell(7).setCellValue("特殊工艺：0无 1有");
        titleRow.createCell(8).setCellValue("技术描述");
        titleRow.createCell(9).setCellValue("年费代码");
        titleRow.createCell(10).setCellValue("正面所有元素");
        titleRow.createCell(11).setCellValue("正面所有元素 描述");
//        titleRow.createCell(12).setCellValue("卡片级别");

        //2.2创建数据行
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0;i<parcel.getCards().size();i++) {
            Card entity = parcel.getCards().get(i);
            HSSFRow dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(entity.getCardName());
            dataRow.createCell(1).setCellValue(entity.getCardType());
            dataRow.createCell(2).setCellValue(entity.getCardBrand());
            dataRow.createCell(3).setCellValue(entity.getCardLevelState());
            dataRow.createCell(4).setCellValue(entity.getCardBin());
            dataRow.createCell(5).setCellValue(entity.getCardCode());
            dataRow.createCell(6).setCellValue(entity.getCardProductCode());
//            dataRow.createCell(7).setCellValue(entity.getCardSprcialCraft());
            dataRow.createCell(8).setCellValue(entity.getCraftDecript());
            dataRow.createCell(9).setCellValue(entity.getCardAnnualFee());
            dataRow.createCell(10).setCellValue(entity.getCardAll());
            dataRow.createCell(11).setCellValue(entity.getAllDetail());

        }

        //设置workbook列宽自动调整
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);

        //3.把excel写入到响应流中
        response.reset();
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+System.currentTimeMillis()+".xls");

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }
}