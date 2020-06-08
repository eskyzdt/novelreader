package com.eskyzdt.novelreader.reader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

;

public class ModuleChooseImpl implements ModuleChoose {

    /**
     * 一次阅读多少行
     */
    static CountDownLatch count;

    /**
     * 根据入参选择不同的阅读模式
     * @param type 1 自动翻页 2 点击翻页
     */
    @Override
    public void chooseModule(String type, InputStreamReader inputStreamReader) {
        switch (type) {
            case "1" :
                auto(inputStreamReader);
                break;
            case "2" :
                hand(inputStreamReader);
                break;
            default:
                break;
        }
    }

    /**
     * 自动翻页的方法
     * @param inputStreamReader
     */
    private void auto(InputStreamReader inputStreamReader) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一次翻页的行数:");
        int count = scanner.nextInt();
        System.out.println("请输入多少秒翻页一次:");
        int seconds = scanner.nextInt();
        while (true) {
            // 输入流
            int i = 0;
            try {
                i = readLine(count, inputStreamReader);
                TimeUnit.SECONDS.sleep(seconds);
            } catch (Exception e) {
                // todo 记录阅读位置
                break;
            }
            // 文件读取完就停止
            if (i == -1) {
                break;
            }
        }
    }

    /**
     * 手动翻页的方法
     * @param inputStreamReader
     */
    private void hand(InputStreamReader inputStreamReader) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一次翻页的行数:");
        int count = scanner.nextInt();
        while (true) {
            // 输入流
            int result;
            try {
                result = readLine(count, inputStreamReader);
            } catch (IOException e) {
                // todo 记录阅读位置
                break;
            }
            // 文件读取完就停止
            if (result == -1) {
                break;
            }
            pressEnter();
        }
    }

    /**
     *
     * @param n 一次读取的行数
     * @param inputStreamReader 文件的输入流
     * @return
     * @throws IOException
     */
    private static int readLine(int n, InputStreamReader inputStreamReader) throws IOException {
        count = new CountDownLatch(n);
        int read = -1;
        while (count.getCount() != 0) {
            char[] chars = new char[60];
            read = inputStreamReader.read(chars);
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                // 去年空格和换行符之类的
                if (aChar != '\r' & aChar != '\n' & aChar != ' ') {
                    System.out.print(chars[i]);
                }
            }
            // 打印换行
            System.out.println();
            // 每执行一次锁减一
            count.countDown();
        }
        return read;
    }

    private static void pressEnter() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String next = sc.nextLine();
            if ("".equals(next)) {
                break;
            }
        }
    }
}
