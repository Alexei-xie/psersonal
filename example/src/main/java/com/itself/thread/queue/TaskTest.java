package com.itself.thread.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author duJi
 * @Date 2023/12/18
 */
@RestController
@RequestMapping("export")
public class TaskTest {

    @Autowired
    private ExportManager exportManager;
    @GetMapping("/test-export")
    public void testExport(){
        // 模拟多个导出请求
        for (int i = 0; i < 10; i++) {
            ExportRequest request = new ExportRequest("Request " + i);
            exportManager.submitExportRequest(request);
        }
    }
}

