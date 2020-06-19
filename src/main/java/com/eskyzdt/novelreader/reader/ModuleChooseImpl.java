package com.eskyzdt.novelreader.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 在java中, char的长度是两个字节,从0-65535 中文在这个范围内
 */
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
            // 如果输入第0页,那么默认是第一页
            pageLong = pageLong == 0 ? 1 : pageLong;
            currentPage = pageLong;
            // 跳过的页数为当前页-1
            long pageJump = pageLong - 1;
            // LINEWORDCOUNT是一行多少个字符,而一个字符占两个字节,所以要乘以2
            // 再乘以column,从第多少页开始
            long byteSize = (pageJump) * LINEWORDCOUNT * 2 * column;
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
                Runtime.getRuntime().exit(0);
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
                System.out.println("                                          " + (currentPage++));
            } catch (IOException e) {
                // todo 记录阅读位置
                break;
            }
            // 文件读取完就停止
            if (result == -1) {
                Runtime.getRuntime().exit(0);
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
        char[] readChar = new char[n * LINEWORDCOUNT];
        // 读取字符,如果返回值为-1说明读完了
        result = inputStreamReader.read(readChar);
        // 对读取到的字符进行处理
        // 创建一个临时的字节
        char[] charTem = new char[LINEWORDCOUNT];
        int countTem = 0;
        // 把字符流读取的这么多字符进行处理
        int total = readChar.length;
        for (int i = 0; i < total; i++) {
            // 1.取出当前字符
            char aChar = readChar[i];

            // 2.如果当前字符是读取的最后一个了
            if (i == total - 1) {
                // 当不是无用字符时放入数组中
                // 这里有个坑是打印char[]时,如果里面的\r是单个组成的,那么整个数组都不会被打印出来,只会打印一个换行
                if (aChar != '\r' & aChar != '\n' & aChar != ' ' & aChar != 12288) {
                    charTem[countTem] = aChar;
                }
                // 最后一个要被打印的数组
                System.out.println(charTem);
                // 跳出循环
                break;
            }

            // 3.去掉空格和换行符之类的
            if (aChar != '\r' & aChar != '\n' & aChar != ' ' & aChar != 12288) {
                // 一个临时数组,用来输出每一行
                charTem[countTem] = aChar;
                countTem++;
                // 数组最后的一个下标是lengthPerRead-1
                // 每当这个临时数组满了,就打印
                if (countTem == LINEWORDCOUNT - 1) {
                    // 存满了,打印
                    System.out.println(charTem);
                    // 把临时数组重新初始化初始化
                    charTem = new char[LINEWORDCOUNT];
                    countTem = 0;
                }
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
