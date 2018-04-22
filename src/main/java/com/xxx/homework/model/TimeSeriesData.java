package com.xxx.homework.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author sh
 */
@Data
public class TimeSeriesData {
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("trading_date")
    private Timestamp tradingDate;
    @SerializedName("stock_code")
    private String stockCode;
    @SerializedName("item_value_one")
    private Double itemValueOne;
    @SerializedName("item_value_two")
    private Double itemValueTwo;
    @SerializedName("item_value_three")
    private Double itemValueThree;

    public TimeSeriesData() {}

    public static class Builder {
        private String itemId;
        private Timestamp tradingDate;
        private String stockCode;
        private Double itemValueOne;
        private Double itemValueTwo;
        private Double itemValueThree;

        public Builder(String itemId, Timestamp tradingDate) {
            this.itemId = itemId;
            this.tradingDate = tradingDate;
        }

        public Builder stockCode(String val) {
            stockCode = val;
            return this;
        }

        public Builder itemValueOne(Double val) {
            itemValueOne = val;
            return this;
        }

        public Builder itemValueTwo(Double val) {
            itemValueTwo = val;
            return this;
        }

        public Builder itemValueThree(Double val) {
            itemValueThree = val;
            return this;
        }

        public TimeSeriesData build() {
            return new TimeSeriesData(this);
        }
    }

    private TimeSeriesData(Builder builder) {
        this.itemId = builder.itemId;
        this.tradingDate = builder.tradingDate;
        this.stockCode = builder.stockCode;
        this.itemValueOne = builder.itemValueOne;
        this.itemValueTwo = builder.itemValueTwo;
        this.itemValueThree = builder.itemValueThree;
    }
}
