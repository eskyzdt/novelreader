package com.eskyzdt.novelreader;

import com.eskyzdt.novelreader.reader.NovelReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class NovelreaderApplication {

    public static void main(String[] args) throws IOException {
        //SpringApplication.run(NovelreaderApplication.class, args);
        NovelReader.run();
    }

}
