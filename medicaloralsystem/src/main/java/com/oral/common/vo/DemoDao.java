package com.oral.common.vo;

import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/15 10:31
 * @Version 1.0
 */
public class DemoDao {
    public static void save(List<DemoData> list) {

        System.out.println("DemoDao===保存");
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}