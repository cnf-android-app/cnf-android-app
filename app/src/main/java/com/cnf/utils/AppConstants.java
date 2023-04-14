package com.cnf.utils;

public class AppConstants {

    private AppConstants(){};

    public static final String SHARE_PREFERENCE_USER_OCC_SESSION = "SHARE_PREFERENCE_USER_OCC_SESSION";
    public static final String SP_KEY_IS_ONLINE = "SP_KEY_IS_ONLINE";
    public static final String SP_KEY_USER_LOGIN_TOKEN = "SP_KEY_USER_LOGIN_TOKEN";
    public static final String SP_KEY_IS_INITIALIZED = "SP_KEY_IS_INITIALIZED";

    public static final String TOAST_INVALID_USERNAME_OR_PASSWORD_MSG = "Invalid Username or Password!";
    public static final String TOAST_INVALID_LOGIN_AUTHORIZATION_MSG = "Invalid Login Authorization!";
    public static final String TOAST_INVALID_CLIENT_MSG = "Mobile App Error Issue!";
    public static final String TOAST_INVALID_SERVER_MSG = "Server Error Issue!";
    public static final String TOAST_UNKNOWN_MSG = "UnKnown Issue!";











    public static final String SP_KEY_USER_ID = "SP_KEY_USER_ID";
    public static final String SP_KEY_MUNICIPALITY_CODE = "SP_KEY_MUNICIPALITY_CODE";
    public static final String SP_KEY_AUTH_PERIOD_ID = "SP_KEY_AUTH_PERIOD_ID";


    public static final String TOAST_NETWORK_CONNECTION_ERROR_MSG = "Network Connection Error!";
    public static final String TOAST_UNKNOWN_NONE_RESPONSE_MSG = "Unknown None Response!";
    public static final String TOAST_LOCAL_DATABASE_ERROR = "Local Database Error!";

    public static final String ERROR_INFRASTRUCTURE_INITIALIZATION_MSG = "Code Infrastructure Initialization Error!";

    public static final String ERROR_VALID_MUNICIPALITY_PERIOD_FETCH_MSG = "Fetch Valid MUNICIPALITY PERIOD Error!";
    public static final String ERROR_VALID_MUNICIPALITY_PERIOD_INSERT_MSG = "Insert Valid MUNICIPALITY PERIOD Error!";




    //TODO CHECK
    public static final String INTENT_EXTRA_INSPECTION_ID_KEY = "INTENT_EXTRA_INSPECTION_ID_KEY";
    public static final String INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY = "INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY";




    public static final String INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY = "INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY";

    public static final String INTENT_EXTRA_INSPECTED_SPACE_ID_NAME = "inspectedSpaceId";
    public static final String INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME = "inspectedSpaceElementGuidId";
    public static final String INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY = "inspectedSpaceElementId";
    public static final String INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY = "INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY";
    public static final String INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY = "INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY";

    public static final String INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY = "inspectedSpaceElementPhotoName";
    public static final String INTENT_EXTRA_INSPECTION_LOADING_SUCCESS = "loadingSuccess";
    public static final String INTENT_EXTRA_INSPECTION_OFFLINE= "is_offline";

    public static final String INSPECTION_DATABASE_NAME = "inspection_database";
    public static final String INTENSITY_SCHEMA_VIOLATION_SEVERITY = "violationseverity";

    public final static String FIREBASE_NOTIFICATION_REF_KEY = "CNF_NOTIFICATION";

    public final static String LOG_INSPECTION_NOTIFICATION = "LOG_INSPECTION_NOTIFICATION";

    public final static String FRAGMENT_INSPECTION = "FRAGMENT_INSPECTION";
    public final static String FRAGMENT_NOTIFICATION = "FRAGMENT_NOTIFICATION";
    public final static String FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE = "FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE";
    public final static String FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CATEGORY = "FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CAGEGORY";
    public final static String FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE = "FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE";

    public final static String FRAGMENT_INSPECTION_OCC_LOCATION_DESCRIPTION = "FRAGMENT_INSPECTION_OCC_LOCATION_DESCRIPTION"; // inspectionSelectOccLocationDescriptionFragment
    public final static String FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE_DETAILS = "FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE_DETAILS"; //inspectionSpaceTypeDetailsFragment
}
