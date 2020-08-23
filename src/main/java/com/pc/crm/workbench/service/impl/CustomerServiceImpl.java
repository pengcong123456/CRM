package com.pc.crm.workbench.service.impl;

import com.pc.crm.utils.ServiceFactory;
import com.pc.crm.utils.SqlSessionUtil;
import com.pc.crm.workbench.dao.CustomerDao;
import com.pc.crm.workbench.domain.Customer;
import com.pc.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @author : 彭聪
 * @date : 2020-08-09 10:44
 **/
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }
}
