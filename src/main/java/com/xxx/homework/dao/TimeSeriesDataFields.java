package com.xxx.homework.dao;

/**
 * @author sh
 */
public enum TimeSeriesDataFields {
    /**
     * uuid
     */
    ITEM_ID("item_id"),
    /**
     * 日期
     */
    TRADING_DATE("trading_date"),
    /**
     * 股票代码
     */
    STOCK_CODE("stock_code"),
    /**
     * 数据项1
     */
    ITEM_VALUE_ONE("item_value_one"),
    /**
     * 数据项2
     */
    ITEM_VALUE_TWO("item_value_two"),
    /**
     * 数据项3
     */
    ITEM_VALUE_THREE("item_value_three"),
    ;

    private final String description;

    TimeSeriesDataFields(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
