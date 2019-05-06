package com.mintos.task.service;

import com.mintos.task.model.LocationObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    private static final String LATITUDE = "59";
    private static final String LONGITUDE = "25";

    @InjectMocks
    WeatherService weatherService;

    @Mock
    private HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

    @Mock
    LocationService locationService;

    @Test
    public void getCurrentWeather() throws IOException {

        when(locationService.getLocationObject(mockedRequest)).thenReturn(createLocationObject());
        String temperature = weatherService.getCurrentWeather(mockedRequest);
        assertNotNull(temperature);

    }


    private LocationObject createLocationObject() {
        LocationObject locationObject = new LocationObject();
        locationObject.setLatitude(LATITUDE);
        locationObject.setLongitude(LONGITUDE);

        return locationObject;
    }

}