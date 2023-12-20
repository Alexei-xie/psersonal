package com.itself.thread.queue;

import com.itself.domain.User;
import com.itself.redis.AserviceImpl;
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
        for (int i = 0; i < 2; i++) {
            ExportRequest request = new ExportRequest();
            request.setRequestId(String.valueOf(i));
            request.setService(new AserviceImpl());
            request.setUser(new User());
            exportManager.submitExportRequest(request);
        }
    }
}

