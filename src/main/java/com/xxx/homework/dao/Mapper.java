package com.xxx.homework.dao;

import com.xxx.homework.db.SqlPoolFactory;
import com.xxx.homework.expection.DBException;
import com.xxx.homework.expection.ErrorCode;
import com.xxx.homework.model.TimeSeriesData;
import com.xxx.homework.util.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.jooq.InsertValuesStep6;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.xxx.homework.dao.TimeSeriesDataTable.TIME_SERIES_DATE_TABLE;

/**
 * @author sh
 */
@Slf4j
@Repository("time_series_data_mapper")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class Mapper {

    @Resource
    private SqlPoolFactory sqlPoolFactory;

    public boolean executeInsert(List<TimeSeriesData> datas) {
        String sql = getInsertSql(datas);
        log.info("[time_series_data_mapper] insert into time_series_data sql {}", sql);
        Connection conn = null;
        try {
            conn = sqlPoolFactory.take();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);
            int size = statement.executeUpdate();
            Asserts.assertTrue(size == datas.size(), () -> new DBException(ErrorCode.DB_INSERT_ERROR));
            log.info("[time_series_data_mapper] insert size {}", size);
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            log.error("{}", e.getCause());
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    private String getInsertSql(List<TimeSeriesData> datas) {
        final InsertValuesStep6<Record, String, Timestamp, String, Double, Double, Double> step = DSL.using(SQLDialect.MYSQL).insertInto(TIME_SERIES_DATE_TABLE,
                TIME_SERIES_DATE_TABLE.ITEM_ID, TIME_SERIES_DATE_TABLE.TRADING_DATE,
                TIME_SERIES_DATE_TABLE.STOCK_CODE, TIME_SERIES_DATE_TABLE.ITEM_VALUE_ONE,
                TIME_SERIES_DATE_TABLE.ITEM_VALUE_TWO, TIME_SERIES_DATE_TABLE.ITEM_VALUE_THREE);
        datas.forEach(data -> step.values(data.getItemId(), data.getTradingDate(), data.getStockCode(), data.getItemValueOne(), data.getItemValueTwo(),
                data.getItemValueThree()));
        return step.getSQL(ParamType.INLINED);
    }
}
