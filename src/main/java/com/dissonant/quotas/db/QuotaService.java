package com.dissonant.quotas.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.dissonant.quotas.model.QuotaModel;

public class QuotaService {

    private QuotaDao mQuotaDao;

    public QuotaService(Context context) {
        mQuotaDao = new QuotaDao(context);
    }

    public boolean addQuota(QuotaModel quota) {
        try {
            mQuotaDao.add(quota);
        } catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Long updateQuota(QuotaModel quota) {
        return mQuotaDao.update(quota);
    }

    public boolean removeQuota(QuotaModel quota) {
        mQuotaDao.remove(quota);
        QuotaModel model = mQuotaDao.get(Long.valueOf(quota.getId()));

        if (model == null) {
            return true;
        } else {
            return false;
        }
    }

    public List<QuotaModel> listQuotas(){
        return mQuotaDao.list();
    }

    public QuotaModel getQuota(Long id) {
        return mQuotaDao.get(id);
    }
}
