package net.kingsilk.qh.shop.service.service;


import com.google.common.collect.Lists;
import com.mongodb.gridfs.GridFSDBFile;
import net.kingsilk.qh.shop.core.ItemStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.msg.EventPublisher;
import net.kingsilk.qh.shop.msg.api.search.ItemsImport.sync.ImportSyncEvent;
import net.kingsilk.qh.shop.msg.api.search.esItem.sync.SyncEvent;
import net.kingsilk.qh.shop.repo.CategoryRepo;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import net.kingsilk.qh.shop.service.util.ExcelUtil;
import org.apache.commons.logging.Log;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

/**
 * 仅使用于 商品的导入模板
 */
@Service
public class ExcelReadService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ItemPropService itemPropService;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private SkuStoreRepo skuStoreRepo;


    int totalRows; //sheet中总行数
    int totalCells; //每一行总单元格数

    public static Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }

    /**
     * read the Excel .xlsx,.xls
     * 读取
     */
    public void readExcel(ImportSyncEvent event) {
        try {
            //读取
            List<ArrayList<String>> listOfAll = readExcelValue(event);
            //保存
            if (listOfAll != null && listOfAll.size() > 0) {
                itemSave(listOfAll, event);
            } else {
            }  //todo 把文件名打印到日志
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String dbFileName = event.getDaFileName();
//
//        if ( dbFileName == null || ExcelUtil.EMPTY == dbFileName.trim()) {
//            return;
//        } else {
//            String postfix = ExcelUtil.getPostfix(dbFileName);
//            if (ExcelUtil.EMPTY != postfix) {
//                if (ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equalsIgnoreCase(postfix)) {
//                    readXls(event);
//                } else if (ExcelUtil.OFFICE_EXCEL_2010_POSTFIX.equalsIgnoreCase(postfix)) {
//                    readXlsx(event);
//                } else {
//                    return ;
//                }
//            }
//        }
    }

    /**
     * read the Excel 2003-2007 .xls
     *
     * @return
     * @throws IOException
     */
    private List<ArrayList<String>> readExcelValue(ImportSyncEvent event) throws Exception {

        String dbFileName = event.getDaFileName();

        List<ArrayList<String>> listOfAll = new ArrayList<ArrayList<String>>();

        GridFSDBFile dbFile = gridFsTemplate.findOne(query(whereFilename().is(dbFileName)));
        if (dbFile == null) {
            return null; //todo
        }
        // IO流读取文件
        Workbook wb = null;
        try {
            // 创建文档
            String filename = dbFile.getFilename();
            String prefix = filename.substring(0, filename.indexOf("."));
            String suffix = filename.substring(filename.indexOf("."), filename.length());
            File tempFile = File.createTempFile(prefix, suffix);
            dbFile.writeTo(tempFile);
            InputStream in = new FileInputStream(tempFile);
            wb = WorkbookFactory.create(in);
//            wb = new XSSFWorkbook(in);
            in.close();
            tempFile.deleteOnExit();
            //读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                Sheet Sheet = wb.getSheetAt(numSheet);
                if (Sheet == null) {
                    continue;
                }

                //行数
                totalRows = Sheet.getLastRowNum();   //todo
                //遍历 获取 总共的列数（第2行列名的数据）
                ArrayList<String> rowList = new ArrayList<>();
                for (totalCells = 0; totalCells < Integer.MAX_VALUE; totalCells++) {
                    Row row2 = Sheet.getRow(1);
                    if (row2.getCell(totalCells) != null) {  //空指针
                        String value = Sheet.getRow(1).getCell(totalCells).getStringCellValue();
                        if (value == null || value == "") {
                            break;
                        }
                        rowList.add(value);
                    } else {
                        break;
                    }
                }
                listOfAll.add(rowList);
                //读取Row,从第三行开始
                for (int rowNum = 2; rowNum <= totalRows; rowNum++) {                       //第三行的真正数据
                    Row row = Sheet.getRow(rowNum);
                    if (row != null) {
                        ArrayList<String> rowArrayList = new ArrayList<String>();
//                                                    totalCells = xssfRow.getLastCellNum();
                        //读取列，从第一列开始
                        for (int c = 0; c < totalCells; c++) {
                            Cell cell = row.getCell(c);
                            boolean nulableCol = (c == 2 || c == totalCells - 4 || c == totalCells - 3
                                    || c == totalCells - 2 || c == totalCells - 1); //值可空列
                            boolean isEmpty = ExcelUtil.isCellEmpty(cell);
                            if (nulableCol && isEmpty) {//可空＆值空
                                rowArrayList.add(ExcelUtil.EMPTY);
                            } else if ((! nulableCol) && isEmpty && rowNum != 2) {//非空列＆空值＆不为第３行
                                //rowNum=2(第三列不数据不会为空，因为是合并的单元格)
                                rowArrayList.add(listOfAll.get(rowNum - 2).get(c));
                            } else {
                                rowArrayList.add(ExcelUtil.getValue(cell).trim());
                            }
                        }
                        listOfAll.add(rowArrayList);
                    }
                }
            }
            wb.close();
            return listOfAll;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wb.close();
        }
        return null;
    }


    //保存商品相关信息
    private void itemSave(List<ArrayList<String>> arrayLists, ImportSyncEvent event) {

        String brandAppId = event.getBrandAppId();
        String shopId = event.getShopId();
        List<String> excelHeadPropsList;

        try {
            if (arrayLists == null || arrayLists.size() == 0) {
//                uniResp.setStatus(ErrStatus.PARAMNUll);
//                uniResp.setData("读取不到excel数据");
//                return uniResp;
                return;
            }

            excelHeadPropsList = arrayLists.get(0);
            if (excelHeadPropsList == null) {
//                uniResp.setData("表格第二行的数据为空");
//                uniResp.setStatus(ErrStatus.UNKNOWN);
//                return uniResp;
                return;
            }
            Assert.notNull(arrayLists.get(0).get(1), "第一个产品读取不到");
//            String sameItem = arrayLists.get(0).get(1);

            ArrayList<String> itemIdList = new ArrayList<>();
            //前一个商品
            String prevItemTitle = null;
            Item prevItem = null;

            for (int i = 1; i < arrayLists.size(); i++) {//第2个元素是真正商品数据
                ArrayList<String> row = arrayLists.get(i);
                Item item = null;
                if (prevItemTitle != row.get(1) || prevItemTitle == null) {
                    //检查是否已存在该商品
                    ArrayList<Item> titleItems = Lists.newArrayList(itemRepo.findAll(
                            allOf(
                                    QItem.item.brandAppId.eq(brandAppId),
                                    QItem.item.shopId.eq(shopId),
                                    QItem.item.title.eq(row.get(1)),
                                    QItem.item.deleted.ne(true)
                            )
                    ));
                    if (titleItems == null || titleItems.size() < 1) {
                        item = new Item();
                    } else if (titleItems.size() == 1) {
                        item = titleItems.get(0);
                    } else {
                        item = titleItems.get(0);//该商品已经存在多个重名了
//                        uniResp.setException(titleItems.get(0).getTitle() + " : 该商品已经存在多个(重名了)");
                    }
                    item.setBrandAppId(brandAppId);
                    item.setShopId(shopId);
                    item.setStatus(ItemStatusEnum.EDITING);

                    //关联 商品分类
                    ArrayList<Category> cates = Lists.newArrayList(categoryRepo.findAll(
                            allOf(
                                    QCategory.category.name.eq(row.get(0)),
                                    QCategory.category.brandAppId.eq(brandAppId),
                                    QCategory.category.shopId.eq(shopId),
                                    QCategory.category.deleted.ne(true)
//                                    QCategory.category.enable.eq(true)  //TODO 确定导入商品时 商品分类是否可用
                            )
                    ));
                    if (cates == null || cates.size() < 1) { //不存在这个商品分类 则创建一个
                        Category category = new Category();
                        category.setEnable(true);
                        category.setName(row.get(0));
                        category.setBrandAppId(brandAppId);
                        category.setShopId(shopId);
                        category.setDesp("商品批量导入时新增，请及时编辑更新");
                        categoryRepo.save(category);
                    } else {
                        //将该商品分类激活
                        cates.forEach(it -> {
                            if (!it.isEnable()) {
                                it.setEnable(true);
                                categoryRepo.save(it);
                            }
                        });
                    }

                    Set<String> categories = new HashSet<>();
                    categories.add(row.get(0));
                    item.setCategorys(categories);

                    item.setTitle(row.get(1));
                    itemRepo.save(item);
                    prevItem = item;
//                    //新的商品，先清理sku
//                    Iterable<Sku> deleteSkus = skuRepo.findAll(
//                            allOf(
//                                    QSku.sku.itemId.eq(item.getId())
//                            )
//                    );
//                    for (Sku deleteSku : deleteSkus) { //todo
//                        deleteSku.setDeleted(true);
//                        skuRepo.save(deleteSku);
//                    }
                } else if (prevItemTitle == row.get(1)) {
                    item = prevItem;
                }
                //存入sku
                Sku sku = new Sku();
                for (int col = 3; col < totalCells - 4; col++) {//商品的属性所在的列
                    Sku.Spec skuSpec = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(col)
                            , row.get(col));
                    sku.getSpecs().add(skuSpec);
                }
//                if (excelHeadPropsList.size() == 9) {
//
//                    Sku.Spec skuSpec1 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(3)
//                            , row.get(3));
//                    Sku.Spec skuSpec2 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(4)
//                            , row.get(4));
//                    sku.getSpecs().add(skuSpec1);
//                    sku.getSpecs().add(skuSpec2);
//
//                } else if (excelHeadPropsList.size() == 10) {
//                    Sku.Spec skuSpec1 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(3)
//                            , row.get(3));
//                    Sku.Spec skuSpec2 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(4)
//                            , row.get(4));
//                    Sku.Spec skuSpec3 = itemPropService.skuSpecPackage(item, row.get(2), excelHeadPropsList.get(5)
//                            , row.get(5));
//                    sku.getSpecs().add(skuSpec1);
//                    sku.getSpecs().add(skuSpec2);
//                    sku.getSpecs().add(skuSpec3);
//                }

                sku.setEnable(true);
                sku.setCode(row.get(2));
                sku.setItemId(item.getId());
                sku.setTitle(item.getTitle());

                if (!StringUtils.isEmpty(row.get(row.size() - 3))) {
                    sku.setLabelPrice(Integer.parseInt(row.get(row.size() - 3)) * 100);
                }
                if (!StringUtils.isEmpty(row.get(row.size() - 2))) {
                    sku.setBuyPrice(Integer.parseInt(row.get(row.size() - 2)) * 100);
                }
                if (!StringUtils.isEmpty(row.get(row.size() - 1))) {
                    sku.setSalePrice(Integer.parseInt(row.get(row.size() - 1)) * 100);
                }
                skuRepo.save(sku);

                //库存量
                SkuStore skuStore = new SkuStore();
                if (!StringUtils.isEmpty(row.get(row.size() - 4))) {
                    skuStore.setNum(Integer.parseInt(row.get(row.size() - 4)));
                } else {
                    skuStore.setNum(0);
                }
                skuStore.setSkuId(sku.getId());
                skuStore.setShopId(shopId);
                skuStore.setBrandAppId(brandAppId);
//                    skuStore.setStoreId();  //TODO 仓库
                skuStoreRepo.save(skuStore);

                itemIdList.add(item.getId());
            }
            itemIdList.forEach(
                    id -> {
                        SyncEvent syncEvent = new SyncEvent();
                        syncEvent.setItemId(id);
                        eventPublisher.publish(syncEvent);
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//    /**
//     * read the Excel 2010 .xlsx
//     *
//     * @return
//     * @throws IOException
//     *
//     * 读取
//     */
//    @SuppressWarnings("deprecation")
//    private void readXlsx(ImportSyncEvent event) {
//        String dbFileName = event.getDaFileName();
//
//        List<ArrayList<String>> listOfAll = new ArrayList<ArrayList<String>>();
//
//        GridFSDBFile dbFile = gridFsTemplate.findOne(query(whereFilename().is(dbFileName)));
//        if (dbFile == null){
//            return ; //todo
//        }
//        // IO流读取文件
//        HSSFWorkbook wb = null;
//        try {
//            // 创建文档
//            wb = new HSSFWorkbook(dbFile.getInputStream());
//            //读取sheet(页)
//            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
//                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
//                if (hssfSheet == null) {
//                    continue;
//                }
//
//                //行数
//                totalRows = hssfSheet.getLastRowNum();   //todo
//                //遍历 获取 总共的列数（第2行列名的数据）
//                ArrayList<String> rowList = null;
//                for (totalCells = 0; totalCells < Integer.MAX_VALUE; totalCells++) {
//                    String value = hssfSheet.getRow(1).getCell(totalCells).getStringCellValue();
//                    if (value == null || value == "") {
//                        break;
//                    }
//                    rowList.add(value);
//                }
//                listOfAll.add(rowList);
//                //读取Row,从第三行开始
//                for (int rowNum = 2; rowNum < totalRows; rowNum++) {                       //第三行的真正数据
//                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                    if (hssfRow != null) {
//                        rowList = new ArrayList<String>();
//                        //                        totalCells = xssfRow.getLastCellNum();
//                        //读取列，从第一列开始
//                        for (int c = 0; c < totalCells; c++) {
//                            HSSFCell cell = hssfRow.getCell(c);
//                            //第3列和倒数第1、2、3、4列下的值 可以为空，详见模板。
//                            boolean nullableCol = (c==3||c==totalCells-4||c==totalCells-3
//                                    ||c==totalCells-2||c==totalCells-1);
//                            boolean isEmpty = ExcelUtil.isHCellEmpty(cell);
//
//                            if (nullableCol && isEmpty){
//                                rowList.add(ExcelUtil.EMPTY);
//                            }else if (!nullableCol && isEmpty && rowNum != 2){//rowNum=2(第三列不数据不会为空，因为是合并的单元格)
//                                rowList.add(listOfAll.get(rowNum-1).get(c));
//                            }else {
//                                rowList.add(ExcelUtil.getHValue(cell).trim());
//                            }
//                        }
//                        listOfAll.add(rowList);
//                    }
//                }
//            }
//            itemSave ( listOfAll,event);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


//    List<ArrayList<String>> readXls(MultipartFile file) {
//        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
//        // IO流读取文件
//        InputStream input = null;
//        HSSFWorkbook wb = null;
//        ArrayList<String> rowList = null;
//        try {
//            input = file.getInputStream();
//            // 创建文档
//            wb = new HSSFWorkbook(input);
//            //读取sheet(页)
//            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
//                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
//                if (hssfSheet == null) {
//                    continue;
//                }
//                totalRows = hssfSheet.getLastRowNum();
//                //读取Row,从第二行开始
//                for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
//                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                    if (hssfRow != null) {
//                        rowList = new ArrayList<String>();
//                        totalCells = hssfRow.getLastCellNum();
//                        //读取列，从第一列开始
//                        for (short c = 0; c <= totalCells + 1; c++) {
//                            HSSFCell cell = hssfRow.getCell(c);
//                            if (cell == null) {
//                                rowList.add(ExcelUtil.EMPTY);
//                                continue;
//                            }
//                            rowList.add(ExcelUtil.getHValue(cell).trim());
//                        }
//                        list.add(rowList);
//                    }
//                }
//            }
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }


//    /**
//     * 读取商品表格第二行，关于商品属性和商品名称
//     */
//    public List<String> readItemProp(InputStream InputStream){
//        List<String> propsList = new ArrayList<>();
//
//        // IO流读取文件
//        XSSFWorkbook wb = null;
//        try {
//            wb = new XSSFWorkbook(InputStream);
//            XSSFSheet sheet0 = wb.getSheetAt(0);
//            if (wb != null){
//                XSSFRow rowProps = sheet0.getRow(1);//第二行
//
//                if (rowProps != null){
//                    for (int i = 0;i < rowProps.getLastCellNum();i++){
//                        if (rowProps.getCell(i) != null){
//                            propsList.add(rowProps.getCell(i).toString());
//                        }else {
//                            propsList.add(null);
//                        }
//                    }
//                    return propsList;
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                InputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }


//}
