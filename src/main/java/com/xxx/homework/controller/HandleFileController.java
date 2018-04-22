package com.xxx.homework.controller;

import com.google.gson.Gson;
import com.xxx.homework.Service.TimeSeriesDataService;
import com.xxx.homework.expection.ErrorCode;
import com.xxx.homework.expection.FormatException;
import com.xxx.homework.model.TimeSeriesData;
import com.xxx.homework.util.Asserts;
import com.xxx.homework.util.ReadLineFile;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class HandleFileController {

    private final static String COMMA = ",";
    private final static String SPOT = ".";
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String CSV_SUFFIX = ".csv";

    @Resource
    private Gson gson;

    @Resource
    private TimeSeriesDataService service;

    public boolean readAndInsertDatabase(String pathname) {
        List<TimeSeriesData> list = read(pathname);
        Optional<List<TimeSeriesData>> optional = Optional.ofNullable(list);
        if (optional.isPresent()) {
            return service.insertValues(optional.get());
        }
        log.info("[HandleFileController] insert into data is null");
        return false;
    }

    /**
     * 读取一个csv文件或一个文件夹下得所有csv文件
     * @param pathname
     * @return
     */
    private List<TimeSeriesData> read(String pathname) {
        File file = new File(pathname);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Optional<File[]> optional = Optional.ofNullable(files);
            if (optional.isPresent()) {
                return Arrays.asList(optional.get()).stream().filter(f -> f.getName().endsWith(CSV_SUFFIX))
                        .map(ff -> handle(ff.getPath(), ff.getName().split(Pattern.quote(SPOT))[0]))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
            }
            return null;
        }
        return handle(file.getPath(), file.getName().split(Pattern.quote(SPOT))[0]);
    }

    private List<TimeSeriesData> handle(String fileName, String date) {
        // 读取csv文件，并将每一行放入list
        List<String> readLines = ReadLineFile.readLines(fileName);
        Optional<List<String>> optional = Optional.ofNullable(readLines);
        if (optional.isPresent()) {
            return optional.get().stream().map(line -> {
                TimeSeriesData seriesData = toTimeSeriesData(line, date);
                log.debug("[HandleFile] TimeSeriesData json is {}", gson.toJson(seriesData));
                return seriesData;
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 每一行字符串转换成对应的 TimeSeriesData 对象
     * @param line
     * @param date
     * @return
     */
    private TimeSeriesData toTimeSeriesData(String line, String date) {
        String[] data = line.split(Pattern.quote(COMMA));
        isValidate(data);
        return new TimeSeriesData.Builder(getUUID(), new Timestamp(getTimestamp(date)))
                .stockCode(data[0]).itemValueOne(Double.parseDouble(data[1]))
                .itemValueTwo(Double.parseDouble(data[2]))
                .itemValueThree(Double.parseDouble(data[3])).build();
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    private long getTimestamp(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTime dt = formatter.parseDateTime(date);
        return dt.getMillis();
    }

    /**
     * 判断字符串是否是double类型
     * @param str
     * @return
     */
    private boolean isDouble(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证数据格式
     * @param data
     */
    private void isValidate (String[] data) {
        log.info("[HandleFile] data length {}", data.length);
        Asserts.assertTrue(data.length == 4, () -> new FormatException(ErrorCode.CSV_FORMAT_ERROR, "csv_format_error"));
        log.info("[HandleFile] stock code [{}]", data[0]);
        Asserts.assertNotNull(data[0], () -> new FormatException(ErrorCode.NULL_STR_FORMAT_ERROR, "not_number"));
        log.info("[HandleFile] item value one [{}]", data[1]);
        Asserts.assertTrue(isDouble(data[1]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR, "not_number"));
        log.info("[HandleFile] item value two [{}]", data[2]);
        Asserts.assertTrue(isDouble(data[2]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR, "not_number"));
        log.info("[HandleFile] item value three [{}]", data[3]);
        Asserts.assertTrue(isDouble(data[3]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR, "not_number"));
    }
}
