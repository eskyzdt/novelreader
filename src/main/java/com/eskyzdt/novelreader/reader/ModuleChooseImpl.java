package com.eskyzdt.novelreader.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ModuleChooseImpl implements ModuleChoose {

    /**
     * 一行多少个字
     */
    final private static int LINEWORDCOUNT = 60;

    /**
     * 当前页数
     */
    private static long currentPage = 0;

    @Override
    public Map<String, Object> firstStep() {
        System.out.println("请选择阅读模式(1.自动翻页 2.手动翻页):");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        while (!"1".equals(type) && !"2".equals(type)) {
            System.out.println("输入错误,请重新选择:");
            type = scanner.nextLine();
        }
        System.out.println("请选择从多少页开始阅读:");
        String page = scanner.nextLine();
        System.out.println("请输入每页的行数:");
        int count = scanner.nextInt();

        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("page", page);
        result.put("count", count);
        return result;
    }

    @Override
    public InputStream startFrom(String page, int column, InputStream inputStream) {
        try {
            Long pageLong = Long.valueOf(page);
            // 把当前页数置为跳过的页数+1
            currentPage = pageLong + 1;
            // LINEWORDCOUNT是一行多少个字符,而一个字符占两个字节,所以要乘以2
            // 再乘以column,从第多少页开始
            long byteSize = pageLong * LINEWORDCOUNT * 2 * column;
            inputStream.skip(byteSize);
        } catch (Exception e) {
            System.out.println("请重新选择从多少页开始阅读");
            Scanner scanner = new Scanner(System.in);
            page = scanner.nextLine();
            startFrom(page, column, inputStream);
        }
        return inputStream;
    }

    /**
     * 根据入参选择不同的阅读模式
     *
     * @param type 1 自动翻页 2 点击翻页
     */
    @Override
    public void chooseModule(String type, int count, InputStreamReader inputStreamReader) {
        switch (type) {
            case "1":
                auto(inputStreamReader, count);
                break;
            case "2":
                hand(inputStreamReader, count);
                break;
            default:
                break;
        }
    }

    /**
     * 自动翻页的方法
     *
     * @param inputStreamReader 字节流到字符流的桥接器
     */
    private void auto(InputStreamReader inputStreamReader, int count) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入多少秒翻页一次:");
        int seconds = scanner.nextInt();
        while (true) {
            // 输入流
            int i;
            try {
                i = readLine(count, inputStreamReader);
                // 自动读取时不需要pressEnter,所以要加个空行
                System.out.println("                                          " + (currentPage++));
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
     *
     * @param inputStreamReader
     */
    private void hand(InputStreamReader inputStreamReader, int count) {
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
     * @param n                 一次读取的行数
     * @param inputStreamReader 文件的输入流
     * @return
     * @throws IOException
     */
    private static int readLine(int n, InputStreamReader inputStreamReader) throws IOException {
        int result;
        // 读取字符,每次读取这么多,当然去除掉空格什么的达不到要求的行数
        // 但是就这样,设计如此
        int lengthPerRead = n * LINEWORDCOUNT;
        char[] readChar = new char[lengthPerRead];
        // 读取字符,如果返回值为-1说明读完了
        result = inputStreamReader.read(readChar);
        // 对读取到的字符进行处理
        // 创建一个临时的字节
        char[] charTem = new char[LINEWORDCOUNT];
        int countTem = 0;
        for (int i = 0; i < lengthPerRead; i++) {
            char aChar = readChar[i];
            // 去年空格和换行符之类的
            if (aChar != '\r' & aChar != '\n' & aChar != ' ') {
                try {
                    charTem[countTem] = aChar;
                } catch (Exception e) {
                    // 存满了,打印
                    System.out.println(charTem);
                    // 初始化
                    charTem = new char[LINEWORDCOUNT];
                    countTem = 0;
                }
                countTem++;
            }
        }
        return result;
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
