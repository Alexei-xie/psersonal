package com.itself.utils;

import java.io.File;

public class DeleteFilesUtils {

    /**
     * 删除目录下所有文件
     *
     * @param filePath 文件目录
     */
    public static void deleteAllFile(String filePath) {
        File file = new File(filePath);
        //如果这个文件不存在就返回为false
        if (!file.exists()) {
            return;
        }
        //如果这个文件不是一个目录也返回为false
        if (!file.isDirectory()) {
            return;
        }
        //返回一个字符串数组，命名由此抽象路径名表示的目录中的文件和目录。 因为我们要删除的就是目录中的文件和目录,所以用list()方法很合适
        String[] list = file.list();
        if (list == null) {
            return;
        }
        //然后创建一个File类的temp对象为空的。
        File temp;
        //然后遍历这个字符串数组
        for (String s : list) {
            if (filePath.endsWith(File.separator)) {
                //如果你输入的这个文件路径是以分隔号结尾的
                //那么就直接把这个输入的路径及路径下的文件给到这个temp
                temp = new File(filePath + s);
            } else {
                //如果你输入的文件路径不是以分隔号结尾的
                //那么你就要把文件路径+分隔号+路径下的子文件给到这个temp
                temp = new File(filePath + File.separator + s);
            }
            //然后进行判断，如果这个temp是文件
            if (temp.isFile()) {
                temp.delete();
            }
            //如果是目录
            if (temp.isDirectory()) {
                //就先删除这个文件夹中的所有子文件
                deleteAllFile(filePath + "/" + s);
                //再删除这个空的文件夹
                deleteFolder(filePath + "/" + s);
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹路径
     */
    public static void deleteFolder(String folderPath) {
        //先删除这个文件夹路径下的所有文件
        deleteAllFile(folderPath);
        //然后把这个输入的文件夹路径赋给这个String类型的字符串filePath
        //由于第一步我们就删除了这个文件夹路径下的所有文件，所以现在的这个filePath就是一个空的文件夹
        //然后我们再去创建File类对象把这个空的文件夹作为参数传进去
        File myFilePath = new File(folderPath);
        //然后删除这个空的文件夹，因为delete()可以直接删除空的文件夹
        myFilePath.delete();
    }
}
