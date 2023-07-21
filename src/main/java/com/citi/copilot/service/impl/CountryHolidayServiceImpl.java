package com.citi.copilot.service.impl;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.citi.copilot.dto.CountryHoliday;
import com.citi.copilot.service.CountryHolidayService;
import com.citi.copilot.util.CSVFileUtil;

@Service
public class CountryHolidayServiceImpl implements CountryHolidayService {

    private String path = "src/main/resources/csv/";
    private String fileName = "countryHoliday";

    @Override
    public void addCountryHoliday(List<CountryHoliday> countryHoliday) {
        // get all the country holiday from csv
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        // Determine if it the country holiday is already exist
        for (CountryHoliday countryHoliday1 : countryHoliday) {
            boolean isExist = false;
            for (CountryHoliday countryHoliday2 : countryHolidayList) {
                if (countryHoliday1.getCountryCode().equals(countryHoliday2.getCountryCode())
                        && countryHoliday1.getHolidayDate().equals(countryHoliday2.getHolidayDate())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                countryHolidayList.add(countryHoliday1);
            }
        }
        saveDataToTheCSV(countryHolidayList);
    }

    @Override
    public void updateCountryHoliday(List<CountryHoliday> countryHoliday) {
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        for (CountryHoliday countryHoliday1 : countryHoliday) {
            for (CountryHoliday countryHoliday2 : countryHolidayList) {
                if (countryHoliday1.getCountryCode().equals(countryHoliday2.getCountryCode())
                        && countryHoliday1.getHolidayDate().equals(countryHoliday2.getHolidayDate())) {
                    countryHoliday2.setHolidayName(countryHoliday1.getHolidayName());
                    break;
                }
            }
        }
        saveDataToTheCSV(countryHolidayList);

    }

    @Override
    public void removeCountryHoliday(List<CountryHoliday> countryHoliday) {
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        // remove the country holiday from csv
        for (CountryHoliday countryHoliday1 : countryHoliday) {
            for (CountryHoliday countryHoliday2 : countryHolidayList) {
                if (countryHoliday1.getCountryCode().equals(countryHoliday2.getCountryCode())
                        && countryHoliday1.getHolidayDate().equals(countryHoliday2.getHolidayDate())) {
                    countryHolidayList.remove(countryHoliday2);
                    break;
                }
            }
        }
        saveDataToTheCSV(countryHolidayList);

    }

    @Override
    public List<CountryHoliday> getNextYearHolidayByCountrCode(String countryCode) {
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        List<CountryHoliday> nextYearCountryHolidayList = new ArrayList<>();
        // method get current year
        Date date = new Date(System.currentTimeMillis());
        int currentYear = date.getYear();
        // get next year
        int nextYear = currentYear + 1;
        // get the next year country holiday
        for (CountryHoliday countryHoliday : countryHolidayList) {
            if (countryHoliday.getCountryCode().equals(countryCode)
                    && countryHoliday.getHolidayDate().contains(String.valueOf(nextYear))) {
                nextYearCountryHolidayList.add(countryHoliday);
            }
        }

        return nextYearCountryHolidayList;

    }

    @Override
    public CountryHoliday getNextHolidayByCountryCode(String countryCode) {
        // get current date
        Date date = new Date(System.currentTimeMillis());
        // get holiday list
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        // get the holiday by country code
        List<CountryHoliday> countryHolidayListByCountryCode = new ArrayList<>();
        for (CountryHoliday countryHoliday : countryHolidayList) {
            if (countryHoliday.getCountryCode().equals(countryCode)) {
                countryHolidayListByCountryCode.add(countryHoliday);
            }
        }
        // order the holiday list by date
        countryHolidayListByCountryCode.sort(Comparator.comparing(CountryHoliday::getHolidayDate));

        // get the next holiday
        CountryHoliday c = null;
        for (CountryHoliday countryHoliday : countryHolidayListByCountryCode) {
            if (countryHoliday.getHolidayDate().compareTo(date.toString()) > 0) {
                c = countryHoliday;
                break;
            }
        }

        return c;

    }

    @Override
    public List<CountryHoliday> isCountryHoliday(String date) {
        // get holiday list
        List<CountryHoliday> countryHolidayList = getCountryHolidayFromCSV();
        // get the unique contry code list
        List<String> countryCodeList = new ArrayList<>();
        for (CountryHoliday countryHoliday : countryHolidayList) {
            if (!countryCodeList.contains(countryHoliday.getCountryCode())) {
                countryCodeList.add(countryHoliday.getCountryCode());
            }
        }

        // get the all the CountryHoliday by date
        List<CountryHoliday> countryHolidayListByDate = new ArrayList<>();
        for (CountryHoliday countryHoliday : countryHolidayList) {
            if (countryHoliday.getHolidayDate().equals(date)) {
                countryHolidayListByDate.add(countryHoliday);
            }
        }

        // get the list which which country holiday is today and set the parameter
        // isHoliday to true and not to false
        List<CountryHoliday> countryHolidayListByDateAndIsHoliday = new ArrayList<>();
        for (String countryCode : countryCodeList) {
            boolean isHoliday = false;
            for (CountryHoliday countryHoliday : countryHolidayListByDate) {
                if (countryHoliday.getCountryCode().equals(countryCode)) {
                    isHoliday = true;
                    break;
                }
            }
            CountryHoliday countryHoliday = new CountryHoliday();
            countryHoliday.setCountryCode(countryCode);
            countryHoliday.setHoliday(isHoliday);
            countryHolidayListByDateAndIsHoliday.add(countryHoliday);
        }

        return countryHolidayListByDateAndIsHoliday;

    }

    private List<CountryHoliday> getCountryHolidayFromCSV() {
        ArrayList<ArrayList<String>> list = CSVFileUtil.CSV2Array("src/main/resources/csv/countryHoliday.csv");
        List<CountryHoliday> countryHolidayList = new ArrayList<>();
        // mapping the list to CountryHoliday
        for (int i = 1; i < list.size(); i++) {
            ArrayList<String> row = list.get(i);
            CountryHoliday countryHoliday = new CountryHoliday();
            countryHoliday.setCountryCode(row.get(0).replace("\"",""));
            countryHoliday.setCountryDesc(row.get(1).replace("\"",""));
            countryHoliday.setHolidayDate(row.get(2).replace("\"\t","").replace("\t\"",""));
            countryHoliday.setHolidayName(row.get(3).replace("\"",""));
            countryHolidayList.add(countryHoliday);
        }
        return countryHolidayList;

    }

    private void saveDataToTheCSV(List<CountryHoliday> countryHolidayList) {
        File file = new File(path + fileName);
        file.delete();

        List<Object> exportData = new ArrayList<>();
        exportData.add("Country Code");
        exportData.add("Country Desc");
        exportData.add("Holiday Date");
        exportData.add("Holiday Name");

        List<List<Object>> datalist = new ArrayList<>();
        for (CountryHoliday countryHoliday : countryHolidayList) {
            List<Object> data = new ArrayList<Object>();
            data.add(countryHoliday.getCountryCode());
            data.add(countryHoliday.getCountryDesc());
            data.add("\t" + countryHoliday.getHolidayDate() + "\t");
            data.add(countryHoliday.getHolidayName());
            datalist.add(data);
        }

        CSVFileUtil.createCSVFile(exportData, datalist, path, fileName);

    }
}