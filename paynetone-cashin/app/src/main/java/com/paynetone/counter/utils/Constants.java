package com.paynetone.counter.utils;

public class Constants {
    public static final String KEY_SHARE_PREFERENCES = "KEY_SHARE_PREFERENCES";
    public static final String KEY_EMPLOYEE = "KEY_EMPLOYEE";
    public static final String KEY_PAYNET = "KEY_PAYNET";
    public static final String KEY_ANDROID_PAYMENT_MODE = "ANDROID_PAYMENT_MODE";
    public static final String KEY_ADDRESS_PAYNET_ID = "KEY_ADDRESS_PAYNET_ID";
    public static final String KEY_PAYNET_HAS_CHILDREN = "KEY_PAYNET_HAS_CHILDREN";
    public static final String KEY_MOBILE_NUMBER = "KEY_MOBILE_NUMBER";

    public static final String ANDROID_PAYMENT_MODE_HIDE = "HIDE";
    public static final String ANDROID_PAYMENT_MODE_SHOW = "SHOW";

    public static final String CHANNEL = "ANDROID";

    public static final String CMD_EMP_LOGIN = "EMP_LOGIN";
    public static final String CMD_ORDER_ADD_NEW = "ORDER_ADD_NEW";
    public static final String CMD_ORDER_GET_BY_CODE = "ORDER_GET_BY_CODE";
    public static final String CMD_ORDER_SEARCH = "ORDER_SEARCH";
    public static final String CMD_MERCHANT_GET_BALANCE = "MERCHANT_GET_BALANCE";
    public static final String CMD_PAYNET_GET_BY_ID = "PAYNET_GET_BY_ID";
    public static final String CMD_TRANS_WITHDRAW = "TRANS_WITHDRAW";
    public static final String CMD_DIC_GET_BANK = "DIC_GET_BANK";
    public static final String DIC_GET_WALLET = "DIC_GET_WALLET";
    public static final String CMD_TRANS_GET_OUTWARD = "TRANS_GET_OUTWARD";
    public static final String CMD_TRANS_WITHDRAW_SEARCH = "TRANS_WITHDRAW_SEARCH";
    public static final String CMD_EMP_ADD_NEW = "EMP_ADD_NEW";
    public static final String CMD_EMP_GET_OTP = "EMP_GET_OTP";
    public static final String CMD_DIC_GET_PARAMS = "DIC_GET_PARAMS";
    public static final String CMD_DIC_BUSINESS_SERVICES = "DIC_BUSINESS_SERVICES";
    public static final String CMD_DIC_GET_PROVINCE = "DIC_GET_PROVINCE";
    public static final String CMD_DIC_GET_DISTRICT = "DIC_GET_DISTRICT";
    public static final String CMD_DIC_GET_WARD = "DIC_GET_WARD";
    public static final String CMD_MERCHANT_ADD_NEW = "MERCHANT_ADD_NEW";
    public static final String CMD_MERCHANT_EDIT = "MERCHANT_EDIT";
    public static final String CMD_MERCHANT_GET_BY_MOBILE_NUMBER = "MERCHANT_GET_BY_MOBILE_NUMBER";
    public static final String EMP_UPDATE_PASSWORD ="EMP_UPDATE_PASSWORD";
    public static final String EMP_OTP_VERIFY = "EMP_OTP_VERIFY";
    public static final String DIC_GET_VI_TOPUP_ADDRESS = "DIC_GET_VI_TOPUP_ADDRESS";
    public static final String TRANS_SEARCH = "TRANS_SEARCH";
    public static final String DIC_GET_PROVIDERS = "DIC_GET_PROVIDERS";
    public static final String PAYNET_GET_BY_PARENT = "PAYNET_GET_BY_PARENT";
    public static final String PAYNET_GET_BALANCE_BY_ID  = "PAYNET_GET_BALANCE_BY_ID";
    public static final String DIC_GET_POS  = "DIC_GET_POS";
    public static final String EMP_UPDATE_PASSWORD_BY_OTP = "EMP_UPDATE_PASSWORD_BY_OTP";
    public static final String EMP_PIN_ADD = "EMP_PIN_ADD";
    public static final String EMP_PIN_VERIFY = "EMP_PIN_VERIFY";
    public static final String EMP_PIN_HAS_OR_NOT = "EMP_PIN_HAS_OR_NOT";
    public static final String REPORT_MOBILE_DASHBOARD = "REPORT_MOBILE_DASHBOARD";
    public static final String DIC_GET_VBOOKING_ADDRESS  = "DIC_GET_VBOOKING_ADDRESS";
    public static final String DIC_GET_PARTNER_ADDRESS  = "DIC_GET_PARTNER_ADDRESS";
    public static final String DIC_GET_ADDRESS_BY_PAYNETID   = "DIC_GET_ADDRESS_BY_PAYNETID";
    public static final String DIC_GET_APP_BANNER = "DIC_GET_APP_BANNER";
    public static final String PAYNET_CHECK_IF_HAS_CHILDREN = "PAYNET_CHECK_IF_HAS_CHILDREN";
    public static final String REFERRAL_MERCHANT_SEARCH = "REFERRAL_MERCHANT_SEARCH";
    public static final String REFERRAL_TRANS_SEARCH = "REFERRAL_TRANS_SEARCH";

    public static final int SCREEN_WIDTH_DESIGN = 375;


    public static final String WEB_VIEW_URL = "WEB_VIEW_URL";

    public static final String STATUS_W = "W";
    public static final String STATUS_S = "S";
    public static final String STATUS_C = "C";

    public static final String PROVIDER_VIETTEL = "VIETTEL";
    public static final String PROVIDER_ZALO = "ZALO";
    public static final String PROVIDER_SHOPPE = "SHOPEE";
    public static final String PROVIDER_VN_PAY = "VNPAY";
    public static final String PROVIDER_MOCA = "GRAB";
    public static final String PROVIDER_VIETQR = "VIETQR";
    public static final String PROVIDER_GTGT = "GTGT";

    public static final String HOME_QR = "HOME_QR";
    public static final String HOME_HISTORY = "HOME_HISTORY";

    public static final String AMOUNT_OUTWARD = "AMOUNT_OUTWARD";
    public static final String AMOUNT_OUTWARD_GTGT = "AMOUNT_OUTWARD_GTGT";
    public static final String AMOUNT_OUT_WARD_BONUS = "AMOUNT_OUT_WARD_BONUS";
    public static final String AMOUNT_TYPE_BALANCE = "AMOUNT_TYPE_BALANCE";

    public static final String REGISTER_PASS_DATA = "REGISTER_PASS_DATA";
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final String MERCHANT_MODE_ADD = "MERCHANT_MODE_ADD";
    public static final String MERCHANT_MODE_EDIT = "MERCHANT_MODE_EDIT";
    public static final String MERCHANT_MODE_VIEW = "MERCHANT_MODE_VIEW";

    public static final int PAYMENT_TYPE_SHOPEE=5;
    public static final int PAYMENT_TYPE_VIETTEL=3;
    public static final int PAYMENT_TYPE_ZALO=4;
    public static final int PAYMENT_TYPE_VN_PAY=6;
    public static final int PAYMENT_TYPE_MOCA =7;
    public static final int PAYMENT_TYPE_VIETQR =8;

    public static final String FORMALITY_TYPE_ONLINE = "1";
    public static final String FORMALITY_TYPE_OFFLINE = "2";

    public static final String WAITING_APPROVAL = "A";
    public static final String APPROVED= "P";
    public static final String REFUSE = "R";


    public static final int PNOLEVEL_STALL = 4; // tài khoản quầy
    public static final int PNOLEVEL_STORE = 3; // tải khoản cửa hàng
    public static final int PNOLEVEL_BRANCH = 2; // chi nhánh
    public static final int PNOLEVEL_MERCHANT = 1; // tài khoản merchant

    public static final int   BUSINESS_TYPE_ENTERPRISE = 1; // doanh nghiep
    public static final int   BUSINESS_TYPE_HOUSEHOLD = 2; // hộ kinh doanh
    public static final int   BUSINESS_TYPE_PERSONAL = 3; //  cá nhân kinh doanh
    public static final int   BUSINESS_TYPE_VIETLOTT = 4; //cửa hàng xổ số vietlott
    public static final int   BUSINESS_TYPE_SYNTHETIC = 5; // cửa hàng xổ số tổng hợp

    public static final String SERVICE_TYPE_QR = "1"; // thanh toán qr code
    public static final String SERVICE_TYPE_TRA_SAU = "2"; // mua ngay - trả sau
    public static final String QR_CODE_TINH = "1";
    public static final String QR_CODE_DONG = "2";

    public static final int THRESHOLD_CLICK_TIME = 500;

    public static final String TYPE_GET_OTP_REGISTER_ACCOUNT="N";
    public static final String TYPE_GET_OTP_FORGOT_PASSWORD="Y";

    public static final String EXTRA_OPT ="EXTRA_OTP";

    public static final int PAYMENT_VIETTEL_PAY = 1;
    public static final int PAYMENT_ZALO_PAY = 2;
    public static final int PAYMENT_SHOPPE_PAY = 3;
    public static final int PAYMENT_VN_PAY = 4;
    public static final int PAYMENT_MOCA = 5;
    public static final int PAYMENT_VIETQR = 6;

    public static final int WITHDRAW_CATEGORY_BANK = 1;  // rút tiền ngân hàng
    public static final int WITHDRAW_CATEGORY_VIETLOTT = 2;  // rút tiền vietlott
    public static final int WITHDRAW_CATEGORY_WALLET = 3; // rút tiền ví
    public static final int WITHDRAW_CATEGORY_HAN_MUC = 4; // rút tiền han muc

    public static final String NOTIFICATION_COUNT = "notification_count";
    public static final String NOTIFICATION_DATA_TRANSFER = "TF_NotificationData";

    // bank
    public static final int BANK_ID_BIDV = 1;
    public static final int BANK_ID_TECK = 2;
    public static final int BANK_ID_VIETCOM = 3;
    public static final int BANK_ID_VIETIN = 4;
    public static final int BANK_ID_AGRI = 5;
    public static final int BANK_MB=6;

    // wallet

    public static final int WALLET_VIETTEL = 1;
    public static final int WALLET_ZALO = 2;
    public static final int WALLET_SHOPEE = 3;
    public static final int WALLET_MOMO = 4;

    //
    public static final int MERCHANT_ADMIN = 1;

    public static final int ROLE_ADMIN = 1; // admin
    public static final int ROLE_ACCOUNTANT = 2; // kế toán
    public static final int ROLE_MANAGER_BRANCH = 3; // quản lý chi nhánh
    public static final int ROLE_MANAGER_STORE= 4; // quản lý cửa hàng
    public static final int ROLE_STAFF = 5; // nhân viên

    public static final int TYPE_BANK = 1; // ngân hàng
    public static final int TYPE_EWALLET = 2 ; // ví điện tử
    public static final int TYPE_GTGT = 3; // dịch vụ gia tăng
    public static final int TYPE_PAYMENT_QR = 4; // thanh toán qr

    public static final int TYPE_TAB_MAIN_QR = 1 ; // tab thanh toán qr
    public static final int TYPE_TAB_MAIN_SERVICE = 2; //tab dịch vụ gia tăng

    public static final String PROVIDER_ACTIVE = "Y";
    public static final String PROVIDER_NO_ACTIVE = "N";

    public static final String EXIST_PIN_CODE = "Y"; // đã tồn tại pincode
    public static final String NOT_EXIST_PIN_CODE = "N"; // chưa tạo pincode

    public static final String DASHBOARD_TO_DAY = "T";
    public static final String DASHBOARD_DAY = "D";
    public static final String DASHBOARD_WEEK = "W";
    public static final String DASHBOARD_MONTH = "M";
    public static final String DASHBOARD_QUARTER = "Q";

    public static final int ID_VBOOKING = 78; // mua vé máy bay
    public static final int ID_MOBILE_RECHARE = 77; // nạp thẻ điện thoại
    public static final int ID_TOPUP_DATA = 19;
    public static final int ID_CARD_GAME = 18;

    public static final int ID_VNPAY = 5;






}
