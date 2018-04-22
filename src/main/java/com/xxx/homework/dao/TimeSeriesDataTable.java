package com.xxx.homework.dao;

import org.jooq.Name;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;

import static org.jooq.util.mysql.MySQLDataType.DOUBLE;
import static org.jooq.util.mysql.MySQLDataType.TIMESTAMP;
import static org.jooq.util.mysql.MySQLDataType.VARCHAR;


/**
 * @author sh
 */
public class TimeSeriesDataTable extends TableImpl<Record> {
    private final static String TABLE_NAME = "time_series_data";
    public final static TimeSeriesDataTable TIME_SERIES_DATE_TABLE = new TimeSeriesDataTable(DSL.name(TABLE_NAME));

    public final TableField<Record, String> ITEM_ID = createField(TimeSeriesDataFields.ITEM_ID.toString(), VARCHAR, this);
    public final TableField<Record, Timestamp> TRADING_DATE = createField(TimeSeriesDataFields.TRADING_DATE.toString(), TIMESTAMP, this);
    public final TableField<Record, String> STOCK_CODE = createField(TimeSeriesDataFields.STOCK_CODE.toString(), VARCHAR, this);
    public final TableField<Record, Double> ITEM_VALUE_ONE = createField(TimeSeriesDataFields.ITEM_VALUE_ONE.toString(), DOUBLE, this);
    public final TableField<Record, Double> ITEM_VALUE_TWO = createField(TimeSeriesDataFields.ITEM_VALUE_TWO.toString(), DOUBLE, this);
    public final TableField<Record, Double> ITEM_VALUE_THREE = createField(TimeSeriesDataFields.ITEM_VALUE_THREE.toString(), DOUBLE, this);

    private TimeSeriesDataTable(Name name) {
        super(name);
    }
}
