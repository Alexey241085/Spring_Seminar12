package ru.gb.Seminar06.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.ServiceActivators;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class Config {

    @Bean
    public MessageChannel textInputChannel(){
        return  new DirectChannel();
    }


    @Bean
    public MessageChannel fileWrite(){
        return  new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWrite")
    public GenericTransformer<String, String> mainTransformer(){
        return text -> {text.toLowerCase();
            return text;
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWrite")
    public FileWritingMessageHandler myHandler(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/Users/aleksej/Desktop/Spring/Seminar12/Notes/src/main/java/ru/gb/Seminar06"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return  handler;
    }


}
