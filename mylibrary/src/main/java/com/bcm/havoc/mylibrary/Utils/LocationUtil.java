package com.bcm.havoc.mylibrary.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

public class LocationUtil {

    @SuppressLint("MissingPermission")
    public static String[] getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork(context);
            }
        } else {    //从网络获取经纬度
            //Looper.prepare();
            try {



                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }catch (Exception E){}
            //Looper.loop();
        }
        String[] str={latitude+"",longitude+""};
        try {
            String s="";
            List<Address> addresses = new Geocoder(context).getFromLocation(latitude, longitude, 3);
            if (addresses.size() > 0) {
                Address a = addresses.get(0);
                s = a.getAddressLine(0);
            }

        }catch (Exception E){}
        return str;
    }
    //从网络获取经纬度
    @SuppressLint("MissingPermission")
    public static String[]  getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }catch (Exception E){
            System.out.println("");}
        String[] str={latitude+"",longitude+""};
        try {
            String s ="";
            List<Address> addresses = new Geocoder(context).getFromLocation(latitude, longitude, 3);
            if (addresses.size() > 0) {
                Address a = addresses.get(0);
                s = a.getAddressLine(0);
            }

        }catch (Exception E){}
        return str;
    }


    public static LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };

}
