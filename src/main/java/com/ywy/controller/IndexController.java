package com.ywy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ywy.entity.ExcelData;


@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "views/index";
    }

    @RequestMapping("/pathData")
    public String pathData(String excelPath,String photoPath,String burgName){
    	//Excel人名
		List<ExcelData> execlData = execlData(excelPath,burgName);
		//照片人名
		List<String> photoData = photoData(photoPath);
		//对比excel人名并重命名
		EqAndUpdate(execlData,photoData,photoPath);
		
		System.out.println(photoPath+"文件名称修改完成------");
        return "views/success";
    }

  //对比excel人名并重命名
  	private static void EqAndUpdate(List<ExcelData> execlData, List<String> photoData, String photoPath) {
  		
  		for(String s : photoData) {
  			for(ExcelData e : execlData) {
  				if(s.equals(e.getExeclName())) {
  					//获得原文件夹名称
  					File file = new File(photoPath+"/"+e.getExeclName());
  					//修改文件夹名称
  					file.renameTo(new File(photoPath+"/"+e.getExcelNum()+e.getExeclName()));
  				}
  			}
  		}
  	}

  	//获取照片的人名
  	private static List<String> photoData(String photoPath) {
  		List<String> list = new ArrayList<String>();
  		File file = new File(photoPath);
  		String[] fileNames = file.list();
  		
  		for (int i = 0; i < fileNames.length; i++) {
  			String fileName = fileNames[i].replaceAll(" +", "");
  			list.add(fileName);
  		}
  		if(list != null && list.size() > 0) {
  			return list;
  		}
  		return null;
  	}
  	
	//获取excel的序号和人名
	private List<ExcelData> execlData(String excelPath, String villageName) {
		try {
			List<ExcelData> list = new ArrayList<ExcelData>();

			InputStream is = new FileInputStream(excelPath);
			
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);
			//总行数
			int rows=sheet.getLastRowNum();
			HSSFRow row ;
			for(int icount=0;icount<rows;icount++){
				row = sheet.getRow(icount);
				//序号
				HSSFCell number = row.getCell(0);
				//人名
				HSSFCell personName = row.getCell(2);
				//村名
				HSSFCell burgName = row.getCell(5);
				
				if(villageName.equals(String.valueOf(burgName).replaceAll(" +", ""))) {
					ExcelData excelData = new ExcelData();
					excelData.setExcelNum(String.valueOf(number).split("\\.")[0]);
					excelData.setExeclName(String.valueOf(personName).replaceAll(" +", ""));
					list.add(excelData);
				}
			}
			is.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
