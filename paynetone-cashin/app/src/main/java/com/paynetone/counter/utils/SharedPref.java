package com.paynetone.counter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.core.base.log.Logger;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.TabMainModel;
import com.paynetone.counter.model.response.AddressPayNetIDResponse;
import com.paynetone.counter.model.response.PayNetHasChildrenResponse;
import com.paynetone.counter.network.NetWorkController;

import java.util.ArrayList;

public class SharedPref {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean autoCommit = true;
    private static volatile SharedPref instance;

    public static SharedPref getInstance(Context context) {
        if (instance == null)
            instance = new SharedPref(context);
        return instance;
    }

    public SharedPref(Context activity) {
        this.autoCommit = true;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity.getSharedPreferences(Constants.KEY_SHARE_PREFERENCES, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, String name) {
        this.autoCommit = true;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity
                .getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, boolean autoCommit) {
        this.autoCommit = autoCommit;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity
                .getSharedPreferences(Constants.KEY_SHARE_PREFERENCES, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        if (autoCommit) {
            commit();
        }

    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue){
        return pref.getBoolean(key,defaultValue);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public void putInt(String key, int value){
        editor.putInt(key,value);
        if (autoCommit) commit();
    }
    public void putBoolean(String key, Boolean value){
        editor.putBoolean(key,value);
        if (autoCommit) commit();
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public void commit() {
        editor.commit();
    }

    public void clear() {
        try {
            editor.remove(Constants.KEY_ANDROID_PAYMENT_MODE);
            editor.remove(Constants.KEY_PAYNET);
            editor.remove(Constants.KEY_SHARE_PREFERENCES);
            editor.remove(Constants.KEY_EMPLOYEE);
            editor.remove(PrefConst.PREF_IS_LOGIN);
            editor.remove(PrefConst.PREF_IS_TAB_MAIN);
            editor.remove(PrefConst.PREF_IS_EXIST_PIN_CODE);
            editor.commit();
        } catch (Exception ex) {
            Logger.w(ex);
        }
    }

    public void saveEmployee(EmployeeModel employeeModel) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.KEY_EMPLOYEE, NetWorkController.getGson().toJson(employeeModel));
        editor.apply();
    }

    public void savePaynet(PaynetModel paynetModel) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.KEY_PAYNET, NetWorkController.getGson().toJson(paynetModel));
        editor.apply();
    }
    public EmployeeModel getEmployeeModel() {
        String userData = pref.getString(Constants.KEY_EMPLOYEE, "");
        if (!userData.equals("")) {
            return NetWorkController.getGson().fromJson(userData, EmployeeModel.class);
        } else {
            return null;
        }
    }

    public PaynetModel getPaynet() {
        String userData = pref.getString(Constants.KEY_PAYNET, "");
        if (!userData.equals("")) {
            return NetWorkController.getGson().fromJson(userData, PaynetModel.class);
        } else {
            return null;
        }
    }
    public AddressPayNetIDResponse getAddressPayNet(){
        String json = pref.getString(Constants.KEY_ADDRESS_PAYNET_ID, "");
        try{
            if (!json.equals("")) {
                return NetWorkController.getGson().fromJson(json, AddressPayNetIDResponse.class);
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public PayNetHasChildrenResponse getPayNetHasChildren(){
        String json = pref.getString(Constants.KEY_PAYNET_HAS_CHILDREN, "");
        try{
            if (!json.equals("")) {
                return NetWorkController.getGson().fromJson(json, PayNetHasChildrenResponse.class);
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    public ArrayList<TabMainModel> getTabMain(){
        try{
            String jsonTabMain = pref.getString(PrefConst.PREF_IS_TAB_MAIN,"");
            if (!jsonTabMain.equals("")){
                return NetWorkController.getGson().fromJson(jsonTabMain, new TypeToken<ArrayList<TabMainModel>>(){}.getType());
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;

    }
    public Boolean isPersonal(){
        try {
            if (getPaynet().getBusinessType()== Constants.BUSINESS_TYPE_PERSONAL){ //  cá nhân
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isMerchantAdmin(){
        try {
            if (getEmployeeModel().getRoleID() == Constants.MERCHANT_ADMIN && Integer.parseInt(getPaynet().getPnoLevel())==Constants.PNOLEVEL_MERCHANT){ // merchant admin
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isAccountStore(){
        try {
            if (Integer.parseInt(getPaynet().getPnoLevel())==Constants.PNOLEVEL_STORE){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    public Boolean isAccountant(){
        try {
            if (getEmployeeModel().getRoleID() == Constants.ROLE_ACCOUNTANT) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isAdmin(){
        try {
            if (getEmployeeModel().getRoleID() == Constants.ROLE_ADMIN) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isManagerStore(){
        try {
            if (getEmployeeModel().getRoleID() == Constants.ROLE_MANAGER_STORE) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isBranchStore(){
        try {
            if ( (getEmployeeModel().getRoleID() == Constants.ROLE_MANAGER_STORE &&
                    Integer.parseInt(getPaynet().getPnoLevel()) == Constants.ROLE_MANAGER_BRANCH) ||
                    getEmployeeModel().getRoleID() == Constants.ROLE_MANAGER_BRANCH) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isAccountStall(){
        try {
            if (Integer.parseInt(getPaynet().getPnoLevel()) == Constants.PNOLEVEL_STALL) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isAccountBranch(){
        try {
            if (Integer.parseInt(getPaynet().getPnoLevel()) == Constants.PNOLEVEL_BRANCH) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean isExistPINCode(){
        try{
            String pinCode = pref.getString(PrefConst.PREF_IS_EXIST_PIN_CODE,"");
            if (!getEmployeeModel().getPin().isEmpty() && getEmployeeModel().getPin()!=null) return true;
            if (!pinCode.isEmpty()) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
