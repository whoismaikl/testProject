package com.mintos.task.service;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import com.mintos.task.model.LocationObject;
import com.mintos.task.utils.ipAddressResolver.IpAddressResolver;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Service
public class LocationService {

    @Inject
    IpAddressResolver ipAddressResolver;

    private static final String GEO_FILE_NAME = "GeoLiteCity.dat";
    private static final String USER_DIR = "user.dir";
    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";

    LocationObject getLocationObject(HttpServletRequest request) {

        String ipAddress = ipAddressResolver.getIpAddressFromRequest(request);
        File file = readFile();
        return getLocation(ipAddress, file);

    }

    private File readFile() {

        return new File(
                System.getProperty(USER_DIR) + File.separator +
                        SRC + File.separator +
                        MAIN + File.separator +
                        RESOURCES + File.separator + GEO_FILE_NAME);
    }


    private LocationObject getLocation(String ipAddress, File file) {

        LocationObject locationObject = null;

        try {
            locationObject = new LocationObject();

            LookupService lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
            Location locationServices = lookup.getLocation(ipAddress);

            locationObject.setCountryCode(locationServices.countryCode);
            locationObject.setCountryName(locationServices.countryName);
            locationObject.setRegion(locationServices.region);
            locationObject.setRegionName(regionName.regionNameByCode(
                    locationServices.countryCode, locationServices.region));
            locationObject.setCity(locationServices.city);
            locationObject.setPostalCode(locationServices.postalCode);
            locationObject.setLatitude(String.valueOf(locationServices.latitude));
            locationObject.setLongitude(String.valueOf(locationServices.longitude));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return locationObject;

    }

}