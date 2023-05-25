package cn.mojit.fragmentcode.test.str;

import org.junit.Test;

public class TestStr {

    /**
     * 格式化成三位显示
     */
    @Test
    public void testnum(){
        Integer count =1;
        String serialNumber = String.format("%03d", count);
        System.out.println(serialNumber);

    }


}
