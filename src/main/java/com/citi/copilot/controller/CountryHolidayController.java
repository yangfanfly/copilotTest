package com.citi.copilot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.citi.copilot.dto.CountryHoliday;
import com.citi.copilot.service.CountryHolidayService;

@RestController
@RequestMapping("/copilot")
public class CountryHolidayController {

    @Autowired
    private CountryHolidayService countryHolidayService;

    // mehod: add country holiday post request

    @PostMapping("/addCountryHoliday")
    public ResponseEntity<String> addCountryHoliday(@RequestBody List<CountryHoliday> countryHoliday) {
        try {
            countryHolidayService.addCountryHoliday(countryHoliday);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    // mehod: update country holiday post request
    @PostMapping("/updateCountryHoliday")
    public ResponseEntity<String> updateCountryHoliday(@RequestBody List<CountryHoliday> countryHoliday) {
        try {
            countryHolidayService.updateCountryHoliday(countryHoliday);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    // mehod: remove country holiday post request
    @PostMapping("/removeCountryHoliday")
    public ResponseEntity<String> removeCountryHoliday(@RequestBody List<CountryHoliday> countryHoliday) {
        try {
            countryHolidayService.removeCountryHoliday(countryHoliday);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    // mehod: get next year holiday by country code post request
    @PostMapping("/getNextYearHolidayByCountrCode")
    public ResponseEntity<List<CountryHoliday>> getNextYearHolidayByCountrCode(@RequestBody String countryCode) {
        List<CountryHoliday> countryHolidayList = null;
        try {
            countryHolidayList = countryHolidayService.getNextYearHolidayByCountrCode(countryCode);
        } catch (Exception e) {
            return new ResponseEntity<>(countryHolidayList, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(countryHolidayList, HttpStatus.CREATED);
    }

    // mehod: get next holiday by country code post request
    @PostMapping("/getNextHolidayByCountryCode")
    public ResponseEntity<CountryHoliday> getNextHolidayByCountryCode(@RequestBody String countryCode) {
        CountryHoliday countryHoliday = null;
        try {
            countryHoliday = countryHolidayService.getNextHolidayByCountryCode(countryCode);
        } catch (Exception e) {
            return new ResponseEntity<>(countryHoliday, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(countryHoliday, HttpStatus.CREATED);
    }

    // mehod: is country holiday post request

    @GetMapping("/isCountryHoliday")
    public ResponseEntity<List<CountryHoliday>> isCountryHoliday(@RequestParam("date") String date) {
        List<CountryHoliday> countryHolidayList = null;
        try {
            countryHolidayList = countryHolidayService.isCountryHoliday(date);
        } catch (Exception e) {
            return new ResponseEntity<>(countryHolidayList, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(countryHolidayList, HttpStatus.CREATED);
    }

}
