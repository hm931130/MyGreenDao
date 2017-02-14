package com.hm.greendao;

import android.app.Application;
import android.content.Context;

import com.hm.bean.DaoMaster;
import com.hm.bean.DaoSession;

import freemarker.template.utility.Constants;

/**
 * Created by Administrator on 2016/4/11.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if(mInstance == null)
            mInstance = this;
    }
    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, com.hm.greendao.Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
