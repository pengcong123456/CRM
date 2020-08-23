package com.pc.crm.workbench.service.impl;

import com.pc.crm.settings.dao.UserDao;
import com.pc.crm.settings.domain.User;
import com.pc.crm.utils.SqlSessionUtil;
import com.pc.crm.vo.PaginationVO;
import com.pc.crm.workbench.dao.ActivityDao;
import com.pc.crm.workbench.dao.ActivityRemarkDao;
import com.pc.crm.workbench.domain.Activity;
import com.pc.crm.workbench.domain.ActivityRemark;
import com.pc.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 彭聪
 * @date : 2020-08-05 21:58
 **/
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag = true;
        int count = activityDao.save(a);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得datelist
        List<Activity> datalist = activityDao.getActivityListByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(datalist);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;

        int count1 = activityRemarkDao.getCountByAids(ids);
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 != count2) {
            flag = false;

        }

        int count3 = activityDao.delete(ids);
        if (count3 != ids.length) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //取得uList
        List<User> ulist = userDao.getUserList();
        //取得a
        Activity a = activityDao.getById(id);
        //返回map
        Map<String, Object> map = new HashMap<>();
        map.put("uList", ulist);
        map.put("a", a);

        return map;
    }

    @Override
    public boolean update(Activity a) {

        boolean flag = true;
        int count = activityDao.update(a);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        //根据id查询单挑活动数据
        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> remarkList = activityRemarkDao.getRemarkListByAid(activityId);

        return remarkList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteById(id);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;

        int count = activityRemarkDao.saveRemark(ar);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag =true;
         int count=activityRemarkDao.updateRemark(ar);
         if (count!=1){
             flag=false;
         }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList=activityDao.getActivityListByClueId(clueId);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> aList =activityDao.getActivityListByNameAndNotByClueId(map);

        return aList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> aList=activityDao.getActivityListByName(aname);
        return aList;
    }
}
