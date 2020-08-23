package com.pc.crm.workbench.dao;

import com.pc.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}
