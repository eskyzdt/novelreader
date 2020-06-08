package com.eskyzdt.novelreader.reader;

import java.io.InputStreamReader;

public interface ModuleChoose {

    /**
     * 根据入参选择不同的阅读模式
     * @param type 1 自动翻页 2 点击翻页
     */
    void chooseModule(String type, InputStreamReader inputStreamReader);

    /**
     * TODO 从指定的页数开始阅读
     * @param page
     */
    InputStreamReader startFrom(int page, InputStreamReader inputStreamReader);

    /**
     * 模板方法
     * @param page
     * @param type
     * @param inputStreamReader
     */
    default void read(int page, String type, InputStreamReader inputStreamReader) {
        // 第一步,选择指定的页数
        inputStreamReader = startFrom(page, inputStreamReader);
        // 第二步,选择阅读模式
        chooseModule(type, inputStreamReader);
    }
}
