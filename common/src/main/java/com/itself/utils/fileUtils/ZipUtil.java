package com.itself.utils.fileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ZIP文件解压工具类
 *
 * <p>提供安全的ZIP文件解压能力，支持类路径资源和通用输入流的处理，
 * 包含系统文件过滤、ZIP炸弹防护等安全机制。</p>
 * <p>设计为静态方法工具类，线程安全，无需实例化。</p>
 */
public class ZipUtil {

    /**
     * 调用方式：String zipPath = "classpath:groovyScript.zip";
     *         String outputDir = "extracted";
     *         ZipUtil.unzipFromClasspath(zipPath, outputDir);
     * 解压 classpath 中的 myfiles.zip 到 extracted 目录
     * @param classpathPath 类路径地址，格式示例："myfiles.zip"
     * @param outputDir 输出目录路径
     */
    public static void unzipFromClasspath(String classpathPath, String outputDir) throws IOException {
        // 通过ClassLoader直接获取资源
        try (InputStream zipStream = getResourceAsStream(classpathPath)) {
            if (zipStream == null) {
                throw new FileNotFoundException("ZIP文件未找到: " + classpathPath);
            }
            unzip(zipStream, outputDir);
        }
    }

    /**
     * 调用方式：// 使用任意ZIP输入流
     * try (InputStream is = new FileInputStream("external.zip")) {
     *     ZipUtils.unzip(is, "external_files");
     * }
     * 通用ZIP解压方法
     * @param zipStream ZIP输入流
     * @param outputDir 输出目录
     */
    public static void unzip(InputStream zipStream, String outputDir) throws IOException {
        Path outputPath = Paths.get(outputDir).toAbsolutePath();
        Files.createDirectories(outputPath);

        try (ZipInputStream zipIn = new ZipInputStream(zipStream)) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                processZipEntry(zipIn, entry, outputPath);
            }
        }
    }

    /**
     * 处理单个ZIP条目
     *
     * @param zipIn ZIP输入流
     * @param entry 当前处理的条目
     * @param outputPath 解压根目录
     */
    private static void processZipEntry(ZipInputStream zipIn, ZipEntry entry, Path outputPath) throws IOException {
        // 过滤系统文件
        if (shouldSkip(entry)) {
            zipIn.closeEntry();
            return;
        }
        // 路径规范化处理
        Path targetPath = outputPath.resolve(entry.getName()).normalize();
        if (!targetPath.startsWith(outputPath)) {
            throw new IOException("非法路径: " + entry.getName());
        }
        // 创建目录或解压文件
        if (entry.isDirectory()) {
            Files.createDirectories(targetPath);
        } else {
            extractFile(zipIn, targetPath, entry);
        }
        zipIn.closeEntry();
    }

    /**
     * 解压单个文件
     *
     * @param zipIn ZIP输入流
     * @param targetPath 目标文件路径
     * @param entry ZIP条目信息
     */
    private static void extractFile(ZipInputStream zipIn, Path targetPath, ZipEntry entry) throws IOException {
        Files.createDirectories(targetPath.getParent());
        // ZIP炸弹防护
        if (entry.getSize() > 100_000_000) {
            throw new IOException("文件大小超过安全限制: " + entry.getName());
        }
        try (OutputStream os = Files.newOutputStream(targetPath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 获取类路径资源流（替代ResourceLoader）
     */
    private static InputStream getResourceAsStream(String resourcePath) {
        return Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath.replace("classpath:", ""));
    }

    /**
     * 过滤系统文件
     */
    private static boolean shouldSkip(ZipEntry entry) {
        String name = entry.getName();
        return name.contains("__MACOSX") ||
                Arrays.stream(name.split("/"))
                        .anyMatch(seg -> seg.startsWith(".") || seg.startsWith("._"));
    }

    /**
     * 获取解压后的文件列表  
     */
    public static List<Path> getExtractedFiles(String outputDir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(outputDir))) {
            return stream.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
    }
}  