package com.itself.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author xxw
 * @Date 2023/08/03
 */
public class FileUtil {

    /**
     * 删除文件或者文件夹
     * TODO 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹 某个文件删除失败会终止删除操作
     * @param file 文件对象
     */
    public static boolean del(File file)  {
        if (file == null || false == file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            clean(file);
        }
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 清空文件夹
     * TODO 注意：清空文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹 某个文件删除失败会终止删除操作
     * @param directory 文件夹
     */
    public static boolean clean(File directory)  {
        if (directory == null || directory.exists() == false || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        for (File childFile : files) {
            boolean isOk = del(childFile);
            if (isOk == false) {
                // 删除一个出错则本次删除任务失败
                return false;
            }
        }
        return true;
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名全称 如：用户管理.xlsx
     * @return 扩展名 如：xlsx
     */
    public static String extName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        } else {
            String ext = fileName.substring(index + 1);
            // 扩展名中不能包含路径相关的符号
            return (ext.contains(String.valueOf("/")) || ext.contains(String.valueOf("\\"))) ? "" : ext;
        }
    }

    /**
     * 返回文件名
     * @param fileName 文件名全称 如：用户管理.xlsx
     * @return 文件名 如：用户管理
     */
    public static String mainName(String fileName) {
        if (StringUtils.isBlank(fileName) || false == fileName.contains(".")) {
            return fileName;
        }
        // 切割字符串到指定位置
        return fileName.substring(0, fileName.lastIndexOf("."));
    }


    /**
     * 读取路径下的所有文件内容（文件格式：.md和.txt）
     * @param directoryPath 文件路径
     * @return 所有内容
     */
    public static List<String> readDirectory(String directoryPath) {
    List<String> fileContents = new ArrayList<>();
    File directory = new File(directoryPath);
    File[] files = directory.listFiles();
    if (files != null) {
        for (File file : files) {
            if (file.isDirectory()) {
                fileContents.addAll(readDirectory(file.getAbsolutePath()));
            } else {
                fileContents.add(readFile(file.getAbsolutePath(),file.getName()));
                //TODO
                fileContents.add("===>结束一个文件");
            }
        }
    }
    return fileContents;
}

    /**
     * 注意：TODO FileReader 类只能用于读取字符数据，不能读取字节数据；如果需要读取字节数据，可以使用 FileInputStream 类或其它字节输入流。
     * 读取文件内容
     * @param filePath 文件路径
     * @param fileName  文件名称
     * @return 一个文件的内容json格式
     * {"fileContext":"阿达大大的\r\n\r\nadaadsadasda\r\n","fileName":"ss.txt:"}
     */
    private static String readFile(String filePath,String fileName) {
        FileData data = new FileData(); //返回值封装实体
        data.setFileName(fileName+"：");//设置文件名，带后缀
        try {
            //创建一个读取文件的 FileReader 对象
            FileReader fileReader = new FileReader(filePath);
            //传递给 BufferedReader 类，以便更高效地读取文件内容
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();//拼接内容
            while ((line = bufferedReader.readLine()) != null) { //读取行内容，包含空行
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            data.setFileContext(stringBuilder.toString());
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            //TODO
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            //TODO
            System.err.println("Error reading file: " + e.getMessage());
        }
        return JSONObject.toJSONString(data);
    }
    public static void main(String[] args) {
        String directoryPath = "D:\\diboot-workflow\\docs\\sss";
        List<String> fileContents = FileUtil.readDirectory(directoryPath);
        for (String fileContent : fileContents) {
            System.out.println(fileContent);
        }
    }
}
@Data
class FileData{
    /**
     * 文件名称：xxx.txt
     */
    private String fileName;
    /**
     * 文件内容
     */
    private String fileContext;
}
