package com.eskyzdt.novelreader.reader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class NovelReader implements Runnable{

    // 文件路径,本地环境是在项目的根目录下,打包后路径会变
    // 这里因为没在spring的管理下,所以注入不进去,另外@Value也不能注入static属性中
    // 另外用@Value注解时, 即使在application.properties中写的是数字, 接收时也会被识别为char类型, 用int这种类型接收会null
    //@Value("${filepath}")
    //private String filePath;
    // private static String filePath = "imnot.txt";

    // 可调用系统代码
    // Runtime.getRuntime().exec("cmd /c start cls ");
    // Runtime.exit()可关闭虚拟机

    @Override
    public void run() {
        String filepath = "";
        try {
            Resource resource = new ClassPathResource("application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            filepath = props.getProperty("filepath");
            // 使用ClassPathResource可以获得resources目录下的文件,这样即使打包也可以看了
            // Resource resource2 = new ClassPathResource("xxx.txt");
            // File file = resource2.getFile();
            // Resource resource3 = new ClassPathResource("com/eskyzdt/novelreader/NovelreaderApplication.class");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        File file = new File(filepath);
        ModuleChoose choose = new ModuleChooseImpl();
        // 输入流读取文件字节流
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            Runtime.getRuntime().exit(0);
        }
        // 模板方法
        choose.read(fileInputStream);
        // 结束程序
        Runtime.getRuntime().exit(0);
    }


}
