package com.pc.crm.web.listener;

import com.pc.crm.settings.domain.DicValue;
import com.pc.crm.settings.service.DicService;
import com.pc.crm.settings.service.Impl.DicServiceImpl;
import com.pc.crm.utils.ServiceFactory;
import com.pc.crm.utils.SqlSessionUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * @author : 彭聪
 * @date : 2020-08-07 15:06
 **/
public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("全局作用域对象创建成功");

        ServletContext application=event.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
     /*
            应该管业务层要
            7个list
            可以打包成为一个map
            业务层应该是这样来保存数据的：
                map.put("appellationList",dvList1);
                map.put("clueStateList",dvList2);
                map.put("stageList",dvList3);
                ....
                ...
         */
        Map<String, List<DicValue>> map =dicService.getAll();
        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for(String key:set){

            application.setAttribute(key, map.get(key));

        }
        System.out.println("服务器缓存处理数据字典结束");


        //数据字典处理完毕后，处理Stage2Possibility.properties文件
        /*

            处理Stage2Possibility.properties文件步骤：
                解析该文件，将该属性文件中的键值对关系处理成为java中键值对关系（map）

                Map<String(阶段stage),String(可能性possibility)> pMap = ....
                pMap.put("01资质审查",10);
                pMap.put("02需求分析",25);
                pMap.put("07...",...);

                pMap保存值之后，放在服务器缓存中
                application.setAttribute("pMap",pMap);

         */

        //解析properties文件
        System.out.println("解析properties文件开始");

        Map<String,String> pMap = new HashMap<String,String>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){

            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key, value);

        }

        //将pMap保存到服务器缓存中
        application.setAttribute("pMap", pMap);
        System.out.println("解析properties文件结束，数据存放到全局作用域中");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("全局作用域对象销毁成功");
    }
}
