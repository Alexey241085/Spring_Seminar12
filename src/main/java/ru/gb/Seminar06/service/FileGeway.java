package ru.gb.Seminar06.service;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.logging.FileHandler;

@MessagingGateway(defaultRequestChannel = "textInputChannel")
public interface FileGeway {

    void fileWriteMethod(@Header(FileHeaders.FILENAME) String filename, String fileData);
}
