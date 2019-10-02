package com.google.firebase.samples.apps.mlkit.others;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String IS_FIRST_LAUNCH="IS_FIRST_LAUNCH";
    private String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static String NAME = "NAME";
    private String MOBILE = "MOBILE";
    private String IS_USER = "IS_USER";
    private Context mContext;
    private String PREF_NAME = "USER_INFO";
    private String STUDENT_ID = "STUDENT_ID";
    private String DIVISION = "DIVISION";
    private String TEACHER_ID = "TEACHER_ID";
    private String IS_STUDENT = "IS_STUDENT";


    public SharedPref(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //for student info
    public SharedPref(Context context,String student_id, String name, String mobile,String division,boolean isStudent) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(STUDENT_ID,student_id);
        editor.putString(NAME,name);
        editor.putString(MOBILE,mobile);
        editor.putString(DIVISION,division);
        editor.putBoolean(IS_STUDENT,isStudent);
       // editor.putBoolean(IS_USER,is_user);
        editor.apply();
    }
    //For Teacher info
    public SharedPref(Context context,int teacher_id, String name, String mobile,boolean isStudent) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(TEACHER_ID,teacher_id);
        editor.putString(NAME,name);
        editor.putString(MOBILE,mobile);
        editor.putBoolean(IS_STUDENT,isStudent);

        // editor.putBoolean(IS_USER,is_user);
        editor.apply();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getPREF_NAME() {
        return PREF_NAME;
    }
 /*       public void setIsFirstLaunch(boolean is_first_launch) {
        editor.putBoolean(IS_FIRST_LAUNCH,is_first_launch);
        editor.commit();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH,true);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN,isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    public void logout() {
        editor.remove(IS_LOGGED_IN);
        editor.remove(NAME);
        editor.remove(MOBILE);
        editor.remove(IS_USER);
        editor.commit();
    }*/

    public String getNAME() {
        return sharedPreferences.getString("NAME","");
    }

    public String getMOBILE() {
        return sharedPreferences.getString("MOBILE","");
    }

    public String getIS_USER() {
        return sharedPreferences.getString("IS_USER","");
    }

    public String getSTUDENT_ID() {
        return sharedPreferences.getString("STUDENT_ID","");
    }

    public String getDIVISION() {
        return sharedPreferences.getString("DIVISION","");
    }

    public String getTEACHER_ID() {
        return sharedPreferences.getString("TEACHER_ID","");
    }

    public String getIS_STUDENT() {
        return sharedPreferences.getString("IS_STUDENT","");
    }
}
