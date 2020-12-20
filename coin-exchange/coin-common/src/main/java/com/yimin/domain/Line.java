package com.yimin.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Line {

    /**
     * 时间
     */
    private DateTime time;

    /**
     * 开盘价
     */
    private BigDecimal open = BigDecimal.ZERO;

    /**
     * 最高价
     */
    private BigDecimal high= BigDecimal.ZERO;

    /**
     * 最低价
     */
    private BigDecimal low =  BigDecimal.ZERO;

    /**
     * 收盘价
     */
    private BigDecimal close = BigDecimal.ZERO;

    /**
     * 总交易量
     */
    private BigDecimal volume = BigDecimal.ZERO;

    public Line() {
    }

    /**
     * 通过价格构造
     *
     * @param time   时间
     * @param price  成交价
     * @param volume 成交量
     */
    public Line(DateTime time, BigDecimal price, BigDecimal volume) {
        this.time = time;
        this.open = price;
        this.high = price;
        this.low = price;
        this.close = price;
        this.volume = volume;
    }

    /**
     * 通过K线构造
     *
     * @param line k线
     */
    public Line(String line) {
        JSONArray jsonArray = JSON.parseArray(line);
        this.time = new DateTime(jsonArray.getLong(0));
        this.open = jsonArray.getBigDecimal(1);
        this.high = jsonArray.getBigDecimal(2);
        this.low = jsonArray.getBigDecimal(3);
        this.close = jsonArray.getBigDecimal(4);
        this.volume = jsonArray.getBigDecimal(5);
    }

    /**
     * 格式化称kline
     *
     * @return
     */
    public String toKline() {
        // 时间，开，高，低，收，量
        JSONArray array = new JSONArray();
        array.add(time.getMillis());
        array.add(open);
        array.add(high);
        array.add(low);
        array.add(close);
        array.add(volume);
        return array.toJSONString();
    }
}
