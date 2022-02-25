package com.eskyzdt.novelreader;

import com.eskyzdt.novelreader.reader.NovelReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eskyzdt.novelreader.reader"})
public class NovelreaderApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NovelreaderApplication.class, args);
        NovelReader reader = new NovelReader();
        new Thread(reader).run();
    }

}
