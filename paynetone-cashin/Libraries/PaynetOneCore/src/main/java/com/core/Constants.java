package com.core;

/**
 * Common constants
 * Created by neo on 7/18/2016.
 */
public class Constants {

  public static final String IS_NOT_FIRST_TIME_INSTALL = "first_session";
  public static final String LOGIN_AUTO = "login_auto";
  public static final String LOGIN_MANUAL = "login_manual";
  public static final String REGISTER = "register";
  public static final String TAG_LOG = "LOG_TRACKING";
  public static final String MESSAGE_TRACKING_LOG = "Firebase tracking: ";
  public static final String EXTRA_DATA = "extra_data";
  public static final String PACKAGE_NAME = "package_name";
  public static final String LOGIN_MANUAL_SUCCESS = "login_manual_success";
  public static final String LOGIN_AUTO_SUCCESS = "login_auto_success";
  public static final String REGISTER_FAIL = "register_fail";
  public static final String SERVICE_HOT = "service_hot";
  public static final String SERVICE_NORMAL = "service_normal";
  public static final String TOP_UP = "top_up";
  public static final String TOP_UP_CARD = "top_up_card";
  public static final String TOP_UP_BANK_PLUS = "top_up_bank_plus";
  public static final String TOP_UP_PAYVN = "top_up_payvn";
  public static final String TOP_UP_CARD_FAIL = "top_up_card_fail";
  public static final String PAYMENT = "payment";
  public static final String PAYMENT_CARD = "payment_card";
  public static final String PAYMENT_BANK_PLUS = "payment_bank_plus";
  public static final String PAYMENT_PAYVN = "payment_payvn";
  public static final String BILLING_HISTORY = "billing_history";
  public static final String BILLING_HISTORY_TAB_TRA_CUOC = "billing_history_tab_tra_cuoc";
  public static final String BILLING_HISTORY_TRA_CUOC = "billing_history_tra_cuoc";
  public static final String SESSION_END = "session_end";
  public static final String AVERAGE_SESSION_TIME = "average_session_time";
  public static final String LOGIN_SCREEN = "login_screen";
  public static final String HOME_SCREEN = "home_screen";
  public static final String TOP_UP_SCREEN = "top_up_screen";
  public static final String SERVICE_SCREEN = "service_screen";
  public static final String BILLING_HISTORY_SCREEN = "billing_history_screen";
  public static final String CSKH_SCREEN = "cskh_screen";
  public static final String LOGIN = "login";
  public static final String REGISTER_PROMOTION = "register_promotion";
  public static final String PROMOTION_CODE = "promotion_code";
  public static final String PROMOTION_NAME = "promotion_name";
  public static final int TYPE_CALL_SMS = 0;
  public static final int TYPE_DATA = 1;
  public static final int TYPE_DATA_EXTRA = 3;
  public static final String REGISTER_PROMOTION_DATA="register_promotion_data";
  public static final String PACK_NAME = "pack_name";
  public static final String PACK_CODE = "pack_code";
  public static final int RESTART_MODEM = 1;
  public static final int OFF_WIFI = 3;
  public static final int ON_WIFI = 2;
  public static final int CHANGE_PASS = 4;
  public static final String MODEM = "modem_object";
  public static final int MULTI_ACCOUNT_ADD_METHOD_INFO_OTP = 1;
  public static final int MULTI_ACCOUNT_ADD_METHOD_INFO_CODE = 2;
  public static final int MULTI_ACCOUNT_ADD_METHOD_INFO_CUSTOMER = 3;
  public static final String FLAG_M_WIFI = "FLAG_M_WIFI";

  public static final int TYPE_PROVINCE = 1;
  public static final int TYPE_DISTRICT = 2;
  public static final int TYPE_COMMUNE = 3;
  public static final int CREATE = 0;
  public static final int DANG_XY_LY = 1;
  public static final String DA_XONG = "other";
  public static final int CHO_DAT_LICH_HEN = 2;

  public static final String TINH = "Tỉnh/Thành phố";
  public static final String HUYEN = "Quận/Huyện";
  public static final String XA = "Phường/Xã";
  public static final int CODE_REQUEST_PROVINCE = 001;
  public static final int CODE_REQUEST_DISTRICT = 002;
  public static final int CODE_REQUEST_PRECINCT = 003;
  public static final String TINH_ID = "Tinh ID";
  public static final String HUYEN_ID = "Huyen ID";
  public static final String CURRENT_TINH = "Current Tinh";
  public static final String CURRENT_HUYEN = "Current Huyen";
  public static final String CURRENT_XA = "Current Xa";
  public static final String ADDRESS = "Address";
  public static final String MESSAGE = "create_message";

  public static final String COMPLAINT_ITEM = "complaint item";
  public static final String KEY_ID_COMPLAINT = "KEY_ID_COMPLAINT";

  public static final String DATA_ANALYTICS_PRIMARY_KEY = "DATA_ANALYTICS_PRIMARY_KEY";
  public static final String GET_DATA_ANALYTICS_ERROR_DETAIL_PRIMARY_KEY = "account";
  public static final String LST_ERROR_DETAIL = "LST_ERROR_DETAIL";
  public static final String DATA_DESCRIPTION_ERROR = "data_description_error";
  public static final String DATA_DESCRIPTION_ERROR_COUNT = "DATA_DESCRIPTION_ERROR_COUNT";
  public static final String DATA_DESCRIPTION_ERROR_CODE = "DATA_DESCRIPTION_ERROR_CODE";
  public static final String SEARCH_ERROR_SERVICE = "SEARCH_ERROR_SERVICE";
  public static final String DATA_CHOOSE_ACCOUNT_ERROR_CODE = "DATA_CHOOSE_ACCOUNT_ERROR_CODE";

    public static String LayKetQuaPhanTichLoiCD = "layketquaphantichloicd";
  public static String isComplaintFirstTime = "is_first_time_tao_phieu";

  public static final int CHECK_SERVICE_CHECK = 0;
  public static final int CHECK_SERVICE_SEARCH = 1;
  public static final int CHECK_SERVICE_CREATE_TICKET = 2;
    private Constants() {

  }
  public static final String EMPTY_STRING = "";
}
