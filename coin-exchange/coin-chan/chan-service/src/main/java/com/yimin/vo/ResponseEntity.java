package com.yimin.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.tio.websocket.common.WsResponse;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {

    private String subbed;

    private String canceled;

    private String event;

    private String id;

    private String ch;

    private String status;

    public Long getTs() {
        return new DateTime().getMillis();
    }

    private Long  ts;

    private Map<String, Object> extend = new LinkedHashMap<>();

    public WsResponse build() {
        extend.put("id", this.getId());
        extend.put("ch", this.getCh());
        extend.put("status", this.getStatus());
        extend.put("subbed", this.getSubbed());
        extend.put("canceled", this.getCanceled());
        extend.put("event", this.getEvent());
        extend.put("ts", this.getTs());
        return WsResponse.fromText(JSONObject.toJSONString(extend), "utf-8");
    }

    public ResponseEntity put(String key, Object value) {
        extend.put(key, value);
        return this;
    }

    public ResponseEntity putAll(Map<String, Object> m) {
        extend.putAll(m);
        return this;
    }
}