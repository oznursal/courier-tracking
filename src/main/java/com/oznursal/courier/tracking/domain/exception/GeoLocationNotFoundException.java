package com.oznursal.courier.tracking.domain.exception;

public class GeoLocationNotFoundException extends BaseException{
    public GeoLocationNotFoundException(String msg){
        super(msg);
    }
}
