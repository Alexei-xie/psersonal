package com.itself.file;

import cn.hutool.json.JSONUtil;
import com.itself.utils.fileUtils.FileContentUtil;
import com.itself.utils.fileUtils.ZipUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: duJi
 * @Date: 2025-03-18
 **/
@RequestMapping("/file")
public class FileController {

    /**
     * 解析classes路径下的压缩包并读取文件内容
     */
    @GetMapping("unZip")
    public void unZip(){
        String result = null;
        try {
            // 解压 classpath 中的 myfiles.zip 到 extracted 目录
            String zipPath = "classpath:groovyScript.zip";
            String outputDir = "extracted";
            ZipUtil.unzipFromClasspath(zipPath, outputDir);
            // 获取解压后的文件列表
            List<Path> files = ZipUtil.getExtractedFiles("extracted");
            files.forEach(file -> System.out.println("解压文件: " + file));
            result =  "解压成功，共处理文件: " + files.size() + " 个";
            // 遍历并输出所有文件内容
            Map<String, String> stringMap = FileContentUtil.traverseAndCollectFiles(outputDir);
            System.out.println("解析所有文件成功：" + JSONUtil.toJsonStr(stringMap));
            //数据存储
            // databaseService.save(filename, content);
        } catch (IOException e) {
            result =  "同步失败: " +e.getMessage();
        }
        System.out.println("result："+ result);
    }
}
