package com.itself.utils.fileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileContentUtil {

    public static Map<String, String> traverseAndCollectFiles(String directoryPath) throws IOException {
        Path dirPath = Paths.get(directoryPath);
        Map<String, String> contentMap = new ConcurrentHashMap<>();

        Files.walkFileTree(dirPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (isMacMetadata(file))
                    return FileVisitResult.CONTINUE;
                try {
                    String content = readFileContent(file);
                    contentMap.put(file.getFileName().toString(), content);
                } catch (IOException e) {
                    System.err.println("文件处理失败: " + file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        deleteDirectory(dirPath);
        return contentMap;
    }

    // 高效读取文件内容的三种实现方式
    public static String readFileContent(Path file) throws IOException {
        // 方式1：使用Collectors.joining（推荐）
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }

        // 方式2：StringBuilder（更可控）
    /*
    try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.length() > 0 ? sb.deleteCharAt(sb.length()-1).toString() : ""; // 去除最后一个换行
    }
    */

        // 方式3：并行流处理（超大文件慎用）
    /*
    try (Stream<String> stream = Files.lines(file, StandardCharsets.UTF_8)) {
        return stream.parallel()
                .collect(Collectors.joining("\n"));
    }
    */
    }

    // 新增过滤方法
    public static boolean isMacMetadata(Path path) {
        // 过滤规则：
        // 1. 位于__MACOSX目录下的所有文件
        // 2. 以._开头的文件
        // 3. 系统隐藏文件
        return path.toString().contains("__MACOSX") ||
                path.getFileName().toString().startsWith("._") ||
                isHiddenFile(path);
    }

    // 判断隐藏文件（跨平台）
    public static boolean isHiddenFile(Path path) {
        try {
            return Files.isHidden(path) || path.getFileName().toString().startsWith(".");
        } catch (IOException e) {
            return false;
        }
    }

    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}