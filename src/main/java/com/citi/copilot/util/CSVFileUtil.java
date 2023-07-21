package com.citi.copilot.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFileUtil {
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath,
            String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);

            writeRow(head, csvWtriter);

            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {

        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static ArrayList<ArrayList<String>> CSV2Array(String path) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            ArrayList<ArrayList<String>> alldata = new ArrayList<ArrayList<String>>();
            String line;
            String[] onerow;
            while ((line = in.readLine()) != null) {
                onerow = line.split(",");
                List<String> onerowlist = Arrays.asList(onerow);
                ArrayList<String> onerowaArrayList = new ArrayList<String>(onerowlist);
                alldata.add(onerowaArrayList);
            }
            in.close();
            return alldata;
        } catch (Exception e) {
            return null;
        }

    }

    // main method
    public static void main(String[] args) {
        List<Object> exportData = new ArrayList<Object>();
        exportData.add("Country Code");
        exportData.add("Country Desc");
        exportData.add("Holiday Date");
        exportData.add("Holiday Name");

        List<List<Object>> datalist = new ArrayList<List<Object>>();
        List<Object> data = new ArrayList<Object>();
        data.add("US");
        data.add("United States");
        data.add("\t2023-12-25\t");
        data.add("Christmas Day");

        datalist.add(data);

        String path = "src/main/resources/csv";

        String fileName = "countryHoliday";

        File file = createCSVFile(exportData, datalist, path, fileName);

        String fileName2 = file.getName();

        System.out.println("fileName：" + fileName2);

    }

}
