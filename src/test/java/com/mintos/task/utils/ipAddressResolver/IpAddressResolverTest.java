package com.mintos.task.utils.ipAddressResolver;

import com.mintos.task.exception.LocalHostException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class IpAddressResolverTest {


    private static final String LOCAL_HOST = "127.0.0.1";
    private static final String SOME_IP = "206.190.36.45";

    @InjectMocks
    private IpAddressResolver ipAddressResolver;

    @Mock
    private HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

    @Test(expected = LocalHostException.class)
    public void shouldThrowExceptionRunningLocally() {

        when(mockedRequest.getHeader("X-FORWARDED-FOR")).thenReturn(LOCAL_HOST);
        ipAddressResolver.getIpAddressFromRequest(mockedRequest);

    }

    @Test
    public void checkIsCorrectIpReturned() {

        when(mockedRequest.getHeader("X-FORWARDED-FOR")).thenReturn(SOME_IP);
        String ipAddress = ipAddressResolver.getIpAddressFromRequest(mockedRequest);
        assertEquals(ipAddress, SOME_IP);

    }

    @Test
    public void getRemoteAddressOnEmptyIP() {

        when(mockedRequest.getHeader("X-FORWARDED-FOR")).thenReturn(null);
        when(mockedRequest.getRemoteAddr()).thenReturn(SOME_IP);
        String ipAddress = ipAddressResolver.getIpAddressFromRequest(mockedRequest);
        assertEquals(ipAddress, SOME_IP);

    }

}