package com.eskyzdt.novelreader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NovelReader {

    // 可调用系统代码的命令,等研究
     // Runtime.getRuntime().exec("cmd /c start cls ");

    public static void run() throws IOException {
        // 文件路径,在项目的根目录下
        String path = "imnot.txt";
        File file = new File(path);
        ModuleChoose choose = new ModuleChooseImpl();
        // 输入流读取文件字节流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 模板方法
        choose.read(fileInputStream);
    }



}
