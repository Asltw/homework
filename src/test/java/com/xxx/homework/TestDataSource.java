package com.xxx.homework;


import com.xxx.homework.db.SqlPoolFactory;
import com.xxx.homework.model.TimeSeriesData;
import org.jooq.InsertValuesStep6;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.xxx.homework.dao.TimeSeriesDataTable.TIME_SERIES_DATE_TABLE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDataSource {

    @Resource
    private SqlPoolFactory sqlPoolFactory;

    @Test
    public void testSingleDataSource() {
//        System.out.println("init size >>>>" + sqlPoolFactory.getDataSource());
        List<TimeSeriesData> list = new ArrayList<>();
        TimeSeriesData data = new TimeSeriesData();
        data.setItemId(UUID.randomUUID().toString());
        data.setTradingDate(new Timestamp(System.currentTimeMillis()));
        data.setStockCode("100001");
        data.setItemValueOne(10.1);
        data.setItemValueTwo(10.2);
        data.setItemValueThree(10.3);
        list.add(data);
        TimeSeriesData data2 = new TimeSeriesData();
        data2.setItemId(UUID.randomUUID().toString());
        data2.setTradingDate(new Timestamp(System.currentTimeMillis()));
        data2.setStockCode("200001");
        data2.setItemValueOne(20.1);
        data2.setItemValueTwo(20.2);
        data2.setItemValueThree(20.3);
        list.add(data2);
        InsertValuesStep6<Record, String, Timestamp, String, Double, Double, Double> step = DSL.using(SQLDialect.MYSQL).insertInto(TIME_SERIES_DATE_TABLE, TIME_SERIES_DATE_TABLE.ITEM_ID, TIME_SERIES_DATE_TABLE.TRADING_DATE,
                TIME_SERIES_DATE_TABLE.STOCK_CODE, TIME_SERIES_DATE_TABLE.ITEM_VALUE_ONE, TIME_SERIES_DATE_TABLE.ITEM_VALUE_TWO, TIME_SERIES_DATE_TABLE.ITEM_VALUE_THREE);
        for (TimeSeriesData t : list) {
            step.values(t.getItemId(), t.getTradingDate(), t.getStockCode(), t.getItemValueOne(), t.getItemValueTwo(), t.getItemValueThree());
        }
        String sql = step.getSQL(ParamType.INLINED);
        System.err.println("sql is >>>>" + sql);
        try {
            Connection conn = sqlPoolFactory.take();
            PreparedStatement statement = conn.prepareStatement(sql);
            int i = statement.executeUpdate();
            System.err.println("success number " + i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
