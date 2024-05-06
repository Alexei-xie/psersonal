package com.itself.test;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author duJi
 * @Date 2023/11/05
 */
@Slf4j
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String str = "111";
        log.info("数据字典管理导入失败， 错误信息： {}",str);
        System.out.println(str);
    }
}
