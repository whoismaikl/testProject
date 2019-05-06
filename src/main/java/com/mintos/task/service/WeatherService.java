package com.mintos.task.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mintos.task.model.LocationObject;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Service
public class WeatherService {

    private static final String MAIN_MAP = "main";
    @Inject
    LocationService locationService;

    private static final String UNITS_METRIC = "&units=metric";
    private static final String LATITUDE = "lat=";
    private static final String LONGITUDE = "&lon=";
    private static final String OWM_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?";


    private static final String APP_ID_STRING = "&APPID=";
    private static final String API_KEY = "1f15d46002762c989c15e2c9d2aef92b";


    private static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        return map;
    }

    public String getCurrentWeather(HttpServletRequest request) throws IOException {

        LocationObject locationObject = locationService.getLocationObject(request);
        String latitude = locationObject.getLatitude();
        String longitude = locationObject.getLongitude();


        String urlString = OWM_WEATHER_API_URL + LATITUDE + latitude + LONGITUDE + longitude
                + APP_ID_STRING + API_KEY + UNITS_METRIC;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        StringBuffer result = readDataFromURL(conn);
        System.out.println(result);

        Map<String, Object> responseMap = jsonToMap(result.toString());
        Map<String, Object> mainMap = jsonToMap(responseMap.get(MAIN_MAP).toString());
        // Map<String, Object> windMap = jsonToMap(responseMap.get("wind").toString());


        return mainMap.get("temp").toString();

    }

    private StringBuffer readDataFromURL(URLConnection conn) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;

        StringBuffer result = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result;
    }


    private static void printWeatherData(Map<String, Object> mainMap, Map<String, Object> windMap) {
        System.out.println("Current Temp: " + mainMap.get("temp"));
        System.out.println("Current Humidiyy: " + mainMap.get("humidity"));
        System.out.println("Wind Speeds: " + windMap.get("speed"));
        System.out.println("Wind Angle : " + windMap.get("deg"));
    }
}
