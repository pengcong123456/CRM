package com.pc.crm.workbench.dao;

import com.pc.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory th);
    List<TranHistory> getHistoryListByTranId(String tranId);
}
