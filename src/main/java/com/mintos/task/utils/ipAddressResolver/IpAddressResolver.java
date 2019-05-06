package com.mintos.task.utils.ipAddressResolver;

import com.mintos.task.exception.LocalHostException;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class IpAddressResolver {

    private static final String LOCAL_HOST_EXCEPTION = "Can not resolve weather running locally!";
    private static final String LOCAL_HOST = "127.0.0.1";

    public String getIpAddressFromRequest(HttpServletRequest request) {

        String ipAddress = "";
        if (request != null) {
            ipAddress = getIpAddress(request);
            checkIsLocalHost(ipAddress);
        }

        return ipAddress;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        ipAddress = request.getHeader("X-FORWARDED-FOR");
        ipAddress = checkOnEmptyIpAddress(request, ipAddress);
        return ipAddress;
    }

    private String checkOnEmptyIpAddress(HttpServletRequest request, String ipAddress) {
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private void checkIsLocalHost(String ipAddress) {
        if(LOCAL_HOST.equals(ipAddress)){
            throw new LocalHostException(LOCAL_HOST_EXCEPTION);
        }
    }

}