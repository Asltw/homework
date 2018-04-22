package com.xxx.homework;

import com.xxx.homework.expection.ErrorCode;
import com.xxx.homework.expection.FormatException;
import com.xxx.homework.model.TimeSeriesData;
import com.xxx.homework.util.Asserts;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestReadFile {

    @Test
    public void testReadFile() {
        File file = new File("/DT/idea/homework/src/main/resources");
        File[] files = file.listFiles();
        List<String> list = Arrays.asList(files).stream().filter(f -> f.getName().endsWith(".csv"))
                .map(ff -> ff.getPath()).collect(Collectors.toList());
        try(Stream<String> lines = Files.lines(Paths.get(list.get(0)))) {
            List<TimeSeriesData> datas = lines.map(line -> {
                String[] strings = line.split(Pattern.quote(","));
                Asserts.assertTrue(strings.length == 4, () -> new FormatException(ErrorCode.CSV_FORMAT_ERROR));
                TimeSeriesData data = new TimeSeriesData();
                data.setItemId(UUID.randomUUID().toString());
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime dt = formatter.parseDateTime("2018-04-21");
                data.setTradingDate(new Timestamp(dt.getMillis()));
                Asserts.assertNotNull(strings[0], () -> new FormatException(ErrorCode.NULL_STR_FORMAT_ERROR));
                data.setStockCode(strings[0]);
                Asserts.assertTrue(isDouble(strings[1]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR, strings[1] + " not double"));
                data.setItemValueOne(Double.parseDouble(strings[1]));
                Asserts.assertTrue(isDouble(strings[2]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR));
                data.setItemValueTwo(Double.parseDouble(strings[2]));
                Asserts.assertTrue(isDouble(strings[3]), () -> new FormatException(ErrorCode.NUMBER_FORMAT_ERROR, strings[3] + " not double"));
                data.setItemValueThree(Double.parseDouble(strings[3]));
                return data;
            }).collect(Collectors.toList());
            System.err.println(">>>>>>> " + datas);
        } catch (IOException e) {
            System.err.println("read csv error " + e.getMessage());
        }

        System.out.println("game over");
    }

    /**
     * 判断是否是double
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
}
