package com.mintos.task.service;

import com.mintos.task.model.LocationObject;
import com.mintos.task.utils.ipAddressResolver.IpAddressResolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    private static final String SOME_IP = "206.190.36.45";

    @InjectMocks
    private LocationService locationService;

    @Mock
    private HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

    @Mock
    IpAddressResolver ipAddressResolver;

    @Test
    public void getLocationObject() {
        when(ipAddressResolver.getIpAddressFromRequest(mockedRequest)).thenReturn(SOME_IP);
        LocationObject locationObject = locationService.getLocationObject(mockedRequest);

        assertThat(locationObject.getLatitude()).isNotEmpty();
        assertThat(locationObject.getLatitude()).isNotEmpty();

    }

}