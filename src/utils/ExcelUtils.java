package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.constant.Regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
public class ExcelUtils {
    private static List<String> mailReceivers = null;

    private static Iterator<String> mailIterator = null;

    private static ReentrantLock lock = new ReentrantLock();


    /**
     * 下一个收件人
     *
     * @return
     */
    public static String nextReceiversMail() {
        if (mailIterator == null) throw new NullPointerException("收件人列表未初始化");
        String receiver = null;
        lock.lock();
        try {
            if (mailIterator.hasNext()) {
                ProjectUtil.sendCountPlus();
                receiver = mailIterator.next();
                ProjectUtil.setCurrentReserver(receiver);
                return "289048093@qq.com";
            }
        } finally {
            lock.unlock();
        }
        return receiver;
    }

    public static List<String> getMailReceivers() {
        return mailReceivers;
    }

    /**
     * 加载excel文件
     *
     * @return email帐号列表
     * @throws IOException
     */
    public static List<String> loadExcel() throws IOException {
        List<String> list = new ArrayList<String>();
        String fileToBeRead = ExcelUtils.class.getClassLoader().getResource(PropertiesUtils.getString("emailsFile")).getFile();
        Workbook workbook;

        if (fileToBeRead.indexOf(".xlsx") > -1) {
            workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
        } else {
            workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
        }

        Sheet sheet = workbook.getSheetAt(0); // 创建对工作表的引用
        Iterator<Row> it = sheet.rowIterator();
        Row tmpRow;
        Cell cell;
        String value;
        int illegalEmailCount = 0;
        while (it.hasNext()) {
            tmpRow = it.next();
            if (tmpRow != null && (cell = tmpRow.getCell(0)) != null) {
                value = cell.getStringCellValue();
                if (!StringUtils.isBlank(value)) {
                    value = value.trim();
                    if (value.matches(Regex.EMAIL_REGEX.getValue()))
                        list.add(value);
                    else {
                        illegalEmailCount++;
                        LogUtils.failure("邮件地址错误：" + value);
                    }
                }
            }
        }
        mailReceivers = list;
        mailIterator = mailReceivers.iterator();
        LogUtils.result("=======================================");
        LogUtils.result("邮件地址错误数量：" + illegalEmailCount);
        LogUtils.result("邮件地址正确数量：" + list.size());
        LogUtils.result("=======================================");
        return list;
    }


    public static void main(String[] args) throws IOException {
        String name = "kuncungexin.xls";
        String fileToBeRead = ExcelUtils.class.getClassLoader().getResource(name).getFile();
        Workbook workbook;

//            workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
            workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("test");
        row.createCell(6).setCellValue("row1 cell6 test");
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,5));
                                          File f = new File("test.xls");
        System.out.println(f.getAbsolutePath());
        row = sheet.createRow(2);
        row.createCell(0).setCellValue("test row2");
        row.createCell(6).setCellValue("row2 cell6 test");
        FileOutputStream fileOut = new FileOutputStream(f);
        workbook.write(fileOut);
        fileOut.close();


    }
}
