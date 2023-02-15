package org.come.tool;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 读取表格工具类
 * @author 叶豪芳
 * @date : 2017年11月29日 上午10:50:39
 */
public class ReadExelTool {

	private static String[][] result;


	public synchronized static String[][] getResult( String path ){
		try {
			// 获得文件真实路径
			String tablePath = ReadExelTool.class.getResource("/").getPath() + path;
//			String tablePath ="config/"+path;
			// 读取表格
			Workbook workbook = Workbook.getWorkbook(new File(tablePath));
			Sheet sheet = workbook.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			result = new String[row][col];
			Cell cell;
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					try {
						cell = sheet.getCell(j, i);
						result[i][j] = cell.getContents();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		} catch (BiffException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	  /**
     * 获取对应路径HGC
     * 
     * @time 2020年1月7日 下午5:11:30<br>
     * @param path
     * @return
     */
    public synchronized static String[][] getResultRelative(String path) {
        try {
            // String tablePath ="config/"+path;
            // 读取表格
            Workbook workbook = Workbook.getWorkbook(new File(path));
            Sheet sheet = workbook.getSheet(0);
            int col = sheet.getColumns();
            int row = sheet.getRows();
            result = new String[row][col];
            Cell cell;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    try {
                        cell = sheet.getCell(j, i);
                        result[i][j] = cell.getContents();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        } catch (BiffException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }
}
