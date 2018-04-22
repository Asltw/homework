package com.xxx.homework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sh
 */
@Slf4j
public class ReadLineFile {

    public static List<String> readLines(String fileName) {
        try(Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("[ReadLineFile] read line of controller error {}", e.getCause());
        }
        return null;
    }
}
