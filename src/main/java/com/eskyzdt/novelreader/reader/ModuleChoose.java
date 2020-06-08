package com.eskyzdt.novelreader.reader;

import java.io.InputStreamReader;

public interface ModuleChoose {

    /**
     * 根据入参选择不同的阅读模式
     * @param type 1 自动翻页 2 点击翻页
     */
    void chooseModule(String type, InputStreamReader inputStreamReader);

    /**
     * 从指定的页数开始阅读 todo
     * @param page
     */
    InputStreamReader startFrom(int page, InputStreamReader inputStreamReader);

    default void read(int page, String type, InputStreamReader inputStreamReader) {
        inputStreamReader = startFrom(page, inputStreamReader);
        chooseModule(type, inputStreamReader);
    }
}
