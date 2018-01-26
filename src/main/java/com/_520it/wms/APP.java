package com._520it.wms;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

public class APP {
    @Test
    public void test1()throws Exception{
        String admin = ConfigTools.encrypt("admin");
        System.out.println(admin);
    }
}
