package com.yimin.utils;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 23:44
 *   @Description :
 *
 */
public class ReportCsvUtils {


    public static void reportList(
            HttpServletResponse response,
            String[] header,
            String[] properties,
            String fileName,
            List<?> sourceList
    ) throws Exception{
        if(header == null || properties == null || sourceList == null || header.length <= 0 || properties.length <= 0 || sourceList.size() <= 0){
            return;
        }
        if(StringUtils.isBlank(fileName)){
            fileName = "1.csv";
        }
        response.setContentType("application/csv");
        response.setCharacterEncoding("GBK");
        response.setHeader("Content-FileName", URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8"));
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        for(Object obj : sourceList){
            csvWriter.write(obj, properties);
        }
        csvWriter.close();
    }
}
