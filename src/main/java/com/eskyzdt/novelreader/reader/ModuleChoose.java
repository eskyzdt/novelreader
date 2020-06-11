package com.eskyzdt.novelreader.reader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public interface ModuleChoose {

    /**
     * 模板方法
     * @param fileInputStream
     */
    default void read(InputStream fileInputStream) {
        // 第一步 提示用户选择阅读模式和起始位置
        Map<String, Object> firstStep = firstStep();
        String type = (String) firstStep.get("type");
        String page = (String) firstStep.get("page");
        int count = (int) firstStep.get("count");
        // 第二步 提示用户选择指定的页数
        fileInputStream = startFrom(page, count, fileInputStream);
        // 字节流转为字符流
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        // 根据入参执行方法,选择阅读模式
        chooseModule(type, count, inputStreamReader);
    }

    /**
     * 选择阅读模式和起始位置
     * @return
     */
    Map<String, Object> firstStep();

    /**
     * 从指定的页数开始阅读
     * @param page 页数
     * @param count 每页多少行
     * @param inputStream 输入流
     */
    InputStream startFrom(String page, int count,InputStream inputStream);

    /**
     * 根据入参选择不同的阅读模式
     * @param type 1 自动翻页 2 点击翻页
     */
    void chooseModule(String type, int count, InputStreamReader inputStreamReader);

}
