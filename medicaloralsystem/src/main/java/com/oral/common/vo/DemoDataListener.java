package com.oral.common.vo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author tanyongpeng
 * @Date 2021/7/15 10:33
 * @Version 1.0
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final int BATCH_COUNT = 5;

    List<DemoData> list = new ArrayList<>();

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
       // System.out.println(JSON.toJSONString(demoData));
        System.out.println(demoData);
        list.add(demoData);

        if(list.size()>=BATCH_COUNT){
            DemoDao.save(list);

            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            DemoDao.save(list);

    }
}