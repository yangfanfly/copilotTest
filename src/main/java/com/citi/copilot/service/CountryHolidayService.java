package com.citi.copilot.service;

import com.citi.copilot.dto.CountryHoliday;

import java.util.List;

public interface CountryHolidayService {

    void addCountryHoliday(List<CountryHoliday> countryHoliday);

    void updateCountryHoliday(List<CountryHoliday> countryHoliday);

    void removeCountryHoliday(List<CountryHoliday> countryHoliday);

    List<CountryHoliday> getNextYearHolidayByCountrCode(String countryCode);

    CountryHoliday getNextHolidayByCountryCode(String countryCode);

    List<CountryHoliday> isCountryHoliday(String date);

}
