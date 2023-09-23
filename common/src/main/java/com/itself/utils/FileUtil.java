package com.itself.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xxw
 * @Date 2023/08/03
 */
public class FileUtil {
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
                //todo
                fileContents.add("===>结束一个文件");
            }
        }
    }
    return fileContents;
}

    /**
     * 注意：todo FileReader 类只能用于读取字符数据，不能读取字节数据；如果需要读取字节数据，可以使用 FileInputStream 类或其它字节输入流。
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
            //todo
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            //todo
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
