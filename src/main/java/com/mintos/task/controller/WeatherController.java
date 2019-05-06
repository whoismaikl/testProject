package com.mintos.task.controller;


import com.mintos.task.exception.EmptyIPAddressException;
import com.mintos.task.responceDto.ResponseDto;
import com.mintos.task.responceDto.ResponseErrorDto;
import com.mintos.task.service.WeatherService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;


@RestController
@RequestMapping("/v1")
public class WeatherController {


    private static final String WEATHER_SERVICE_UNAVAILABLE = "Weather Service unavailable";
    @Inject
    WeatherService weatherService;


    @RequestMapping(value = "/weather",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity weather(@Context HttpServletRequest request) {

        try {

            ResponseDto responseDto = new ResponseDto();

            responseDto.setCurrentTemperature(weatherService.getCurrentWeather(request));
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);



        } catch (EmptyIPAddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto(e.getMessage()));
          } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseErrorDto(WEATHER_SERVICE_UNAVAILABLE));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorDto(e.getMessage()));
        }

    }

}