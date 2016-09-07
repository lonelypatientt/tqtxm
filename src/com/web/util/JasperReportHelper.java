package com.web.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperReportHelper<T> {
	
	/**
	 * 导出数据报表为excel文件
	 * @param jasperFilePath
	 * @param exportFileName
	 * @param map
	 * @param data
	 * @param resp
	 */
	public void export(String jasperFilePath, String exportFileName, Map<String, Object> map, List<T> data, HttpServletResponse resp){
		try {
			// 加载报表文件
			JasperReport report = (JasperReport) JRLoader.loadObject(new File(jasperFilePath));
			// 将JavaBeans数据集转换为JasperReports能识别的JRDataSource
			JRDataSource dataSource = new JRBeanCollectionDataSource(data);
			// 将数据和参数写入到报表中
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, dataSource);
			
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();

			// 导出报表文件为Excel。如要使用其他格式导出请使用相关的类
			// pdf---JRPdfExporter、rtf---JRRtfExporter、odt---JROdtExporter、html---JRHtmlExporter
			// xml---JRXmlExporter、csv---JRCsvExporter、text---JRTextExporter等
			JExcelApiExporter xlsExporter = new JExcelApiExporter();
			/*
			 * 导出xls文件后可能会出现多次表头的现象，解决办法是：
			 * 在iReport中选中当前报表查看属性，在More下面找到Ignore pagination项，勾上，忽略分页，一切就OK了
			 */
			xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			xlsExporter.setParameter(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);//是否允许自动修正Excel每个栏位的大小
			xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 是否移除行与行之间的空行
			xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);//每一页是否用一个Sheet false表示不分页 所有sheet合并到同一sheet中
			xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 页面的背景是否为白的
			xlsExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			/**page之间的margin**/ 
			xlsExporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS,Boolean.TRUE);  
            /**解决勾选stretch with overflow的折行问题  列的内容超出列宽度时是否折叠列（但要加载此列全部内容）**/  
			xlsExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE); 
			xlsExporter.exportReport();

			byte[] bytes = oStream.toByteArray();
			if (bytes != null && bytes.length > 0) {
				resp.reset();
				resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
				resp.setContentLength(bytes.length);
				
				// 转换文件名字符编码
				exportFileName = new String(exportFileName.getBytes("UTF-8"), "ISO-8859-1");
				resp.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
				ServletOutputStream ouputStream = resp.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
