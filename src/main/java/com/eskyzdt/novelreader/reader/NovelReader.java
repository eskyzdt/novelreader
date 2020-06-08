package com.eskyzdt.novelreader.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class NovelReader {

    // 可调用系统代码的命令,等研究
     // Runtime.getRuntime().exec("cmd /c start cls ");

    public static void main(String[] args) throws IOException {
        // 文件路径
        String path = "D:\\development\\IdeaProjects\\eskyzdt\\eskyzdt-server\\src\\main\\resources\\imnot.txt";
        File file = new File(path);
        // 输入流读取文件字节流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 字节流转为字符流
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        ModuleChoose choose = new ModuleChooseImpl();
        System.out.println("请选择阅读模式(1. 自动翻页 2.手动翻页):");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        while (!"1".equals(type) && !"2".equals(type)) {
            System.out.println("输入错误,请重新选择:");
            type = scanner.nextLine();
        }
        choose.chooseModule(type, inputStreamReader);
    }



}
