package com.cnf.utils;

public class RequestConstants {

    public static final String OCC_INSPECTION_ADDRESS = "http://10.0.0.146:8082/occInspection";
    public static final String CNF_AUTHORIZATION_ADDRESS = "http://10.0.0.146:8081/login";

    public static final String USER_LOGIN_PATH = CNF_AUTHORIZATION_ADDRESS + "/user";
    public static final String AUTH_PERIOD_PATH = CNF_AUTHORIZATION_ADDRESS + "/authPeriod";

    public static final String USER_AUTH_TYPE = "/userAuth";
    public static final String PERIOD_AUTH_TYPE = "/periodAuth";

    public static final String OCC_INSPECTION_INFRA_ADDRESS = OCC_INSPECTION_ADDRESS + USER_AUTH_TYPE + "/Infrastructure";
    public static final String OCC_INSPECTION_DISPATCH_ALL_ADDRESS = OCC_INSPECTION_ADDRESS + PERIOD_AUTH_TYPE + "/dispatch/all";
    public static final String OCC_INSPECTION_DISPATCH_UN_SYNCHRONIZE_ADDRESS = OCC_INSPECTION_ADDRESS + PERIOD_AUTH_TYPE + "/dispatch/unSynchronize";
    public static final String OCC_INSPECTION_DISPATCH_SYNCHRONIZED_ADDRESS = OCC_INSPECTION_ADDRESS + PERIOD_AUTH_TYPE + "/dispatch/synchronized";

    public static final String OCC_INSPECTION_UPLOAD = OCC_INSPECTION_ADDRESS + PERIOD_AUTH_TYPE + "/synchronize";

}
