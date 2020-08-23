package com.pc.crm.workbench.service.impl;

import com.pc.crm.utils.DateTimeUtil;
import com.pc.crm.utils.SqlSessionUtil;
import com.pc.crm.utils.UUIDUtil;
import com.pc.crm.workbench.dao.CustomerDao;
import com.pc.crm.workbench.dao.TranDao;
import com.pc.crm.workbench.dao.TranHistoryDao;
import com.pc.crm.workbench.domain.Customer;
import com.pc.crm.workbench.domain.Tran;
import com.pc.crm.workbench.domain.TranHistory;
import com.pc.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 彭聪
 * @date : 2020-08-09 09:47
 **/
public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag = true;
         /*

            交易添加业务：

                在做添加之前，参数t里面就少了一项信息，就是客户的主键，customerId

                先处理客户相关的需求

                （1）判断customerName，根据客户名称在客户表进行精确查询
                       如果有这个客户，则取出这个客户的id，封装到t对象中
                       如果没有这个客户，则再客户表新建一条客户信息，然后将新建的客户的id取出，封装到t对象中

                （2）经过以上操作后，t对象中的信息就全了，需要执行添加交易的操作

                （3）添加交易完毕后，需要创建一条交易历史

         */

        Customer cus = customerDao.getCustomerByName(customerName);
        //如果客户为空
        if (cus == null) {

            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            //添加客户
            int count1 = customerDao.save(cus);
            if (count1 != 1) {
                flag = false;
            }
        }
        //如果客户不为空
        t.setCustomerId(cus.getId());
        //添加交易
        int count2 = tranDao.save(t);
        if (count2 != 1) {

            flag = false;
        }
        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        int count3 = tranHistoryDao.save(th);
        if (count3 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> tList = tranHistoryDao.getHistoryListByTranId(tranId);
        return tList;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;
        //改变交易阶段
        int count1 = tranDao.changeStage(t);
        if (count1 != 1) {
            flag = false;
        }

        //添加历史操作
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        th.setStage(t.getStage());

        int count2 = tranHistoryDao.save(th);
        if (count2 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        //取得total
        int total = tranDao.getTotal();

        //取得dataList
        List<Map<String, Object>> dataList = tranDao.getCharts();
        //返回map
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
