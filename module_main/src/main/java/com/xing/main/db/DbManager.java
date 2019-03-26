package com.xing.main.db;

import android.database.sqlite.SQLiteDatabase;

import com.xing.commonbase.base.BaseApplication;

public class DbManager {

    private static DbManager instance;
    private final SearchHistoryDao searchHistoryDao;

    private DbManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getApplication().getApplicationContext(), "WanAndroid");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        searchHistoryDao = daoSession.getSearchHistoryDao();
    }

    public static DbManager getInstance() {
        if (instance == null) {
            synchronized (DbManager.class) {
                if (instance == null) {
                    instance = new DbManager();
                }
            }
        }
        return instance;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return searchHistoryDao;
    }
}
