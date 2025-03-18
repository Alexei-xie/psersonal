package com.itself.utils.fileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * 基于ResourceLoader进行压缩包读取解析，需要进行Spring实例化
 */
@Service  
public class ZipServiceUtil {

    @Autowired
    private ResourceLoader resourceLoader;  

    /**  
     * 解压 classpath 中的 ZIP 文件  
     * @param classpathZipPath classpath 路径（如 "classpath:groovyScript.zip"）
     * @param outputDir 解压目录（如 "extracted"）  
     */  
    public void processClasspathZip(String classpathZipPath, String outputDir) throws IOException {  
        // 通过 classpath 加载 ZIP 文件  
        Resource resource = resourceLoader.getResource(classpathZipPath);
        try (InputStream zipStream = resource.getInputStream();  
             ZipInputStream zipIn = new ZipInputStream(zipStream)) {
            // 创建解压目录（输出到项目根目录下的文件夹）  
            Path outputPath = Paths.get(outputDir).toAbsolutePath();  
            Files.createDirectories(outputPath);
            // 遍历 ZIP 条目  
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                // 新增过滤逻辑：跳过 macOS 元数据文件
                if (shouldSkipEntry(entry)) {
                    // 重要！必须关闭当前条目
                    zipIn.closeEntry();
                    continue;
                }
                Path entryPath = outputPath.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    // 确保父目录存在  
                    Files.createDirectories(entryPath.getParent());  
                    // 解压文件
                    // 在解压前检查文件数量或总大小
                    if (entry.getSize() > 100_000_000) {
                        throw new IOException("文件过大，疑似 ZIP 炸弹: " + entry.getName());
                    }
                    try (OutputStream os = Files.newOutputStream(entryPath)) {  
                        byte[] buffer = new byte[1024];  
                        int len;  
                        while ((len = zipIn.read(buffer)) > 0) {  
                            os.write(buffer, 0, len);  
                        }  
                    }  
                }  
                zipIn.closeEntry();  
            }
        }
    }
    /**
     * 智能过滤 macOS 系统文件（安全跳过不影响其他文件）
     */
    private boolean shouldSkipEntry(ZipEntry entry) {
        String entryName = entry.getName();
        // 白名单文件
        // if (isWhitelisted(Paths.get(entry.getName()))) {
        //     return false;
        // }
        // 规则1：跳过整个 __MACOSX 目录及其子内容
        if (entryName.contains("__MACOSX")) {
            return true;
        }
        // 规则2：跳过所有以 . 开头的文件/目录（包括 .DS_Store）
        String[] pathSegments = entryName.split("/");
        for (String segment : pathSegments) {
            if (segment.startsWith(".") || segment.startsWith("._")) {
                return true;
            }
        }
        // 规则3：跳过 macOS 特有的双文件格式（._前缀文件）
        return entryName.contains("/._");
    }

    /**  
     * 获取解压后的文件列表  
     */  
    public List<Path> getExtractedFiles(String outputDir) throws IOException {
        Path outputPath = Paths.get(outputDir).toAbsolutePath();  
        try (Stream<Path> paths = Files.walk(outputPath)) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        }  
    }

    /**
     * 可添加白名单机制（允许特定隐藏文件）
     */
    private boolean isWhitelisted(Path path) {
        return path.endsWith(".gitkeep") || path.endsWith(".htaccess");
    }

}