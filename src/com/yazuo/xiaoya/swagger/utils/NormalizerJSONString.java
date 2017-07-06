package com.yazuo.xiaoya.swagger.utils;


/**
 * 使用该类标准化json字符串比直接用JSON.parseObject大约多50%的性能消耗 json字符串越大性能会有一些提升
 * <p>
 * json:"{" +
 * "\"out_biz_no\":\"1331313113313\"," +
 * "\"name\":\"30岁以下白领\"," +
 * "\"conditions\":\"[ \"{ \\\"op\\\": \\\"IN\\\", \\\"tagCode\\\": \\\"pam_gender\\\",  \\\"value\\\": \\\"1\\\"}\"]\"," +
 * "\"operator_id\":\"2088102146931393\"," +
 * "\"operator_type\":\"PROVIDER\"" +
 * "  }"
 * <p>
 * 循环100w次
 * <p>
 * 生成100w个NormalizerJSONString实例需要1347毫秒
 * 直接JSON.parseObject花费 4355毫秒
 * 标准化后调用JSON.parseObject花费 6462毫秒
 *
 *
 */

public class NormalizerJSONString {
    public static final byte OBJECT_START = 123;
    public static final byte OBJECT_END = 125;
    public static final byte ARRAY_START = 91;
    public static final byte ARRAY_END = 93;
    public static final byte SPLITER = 92;
    public static final byte WHITE_SPACE = 32;
    public static final byte TOKEN = 34;
    public static final byte ENTER = 10;
    private byte[] source;
    private byte[] normalizerData;
    private int deleteCount = 0;
    private int counter = 0;

    public NormalizerJSONString(String json) {
        normalizer(json);
    }

    private byte[] normalizer(String json) {
        this.source = json.getBytes();
        for (int i = 0; i < source.length - 1; i++) {
            byte point = source[i];
            if (point == TOKEN) {
                byte before = source[i - 1];
                byte after = source[i + 1];
                if (before == SPLITER) source[i - 1] = WHITE_SPACE;
                if (after == ARRAY_START || after == OBJECT_START) {
                    source[i] = WHITE_SPACE;
                    deleteCount++;
                }
                if (before == ARRAY_END || before == OBJECT_END) {
                    source[i] = WHITE_SPACE;
                    deleteCount++;
                }
            }
            if (point == ENTER) source[i] = WHITE_SPACE;
        }
        this.normalizerData = new byte[source.length - deleteCount];
        for (byte data : source) {
            if (data == WHITE_SPACE) continue;
            normalizerData[counter++] = data;
        }
        return normalizerData;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }

    public byte[] getNormalizerData() {
        return normalizerData;
    }

    public void setNormalizerData(byte[] normalizerData) {
        this.normalizerData = normalizerData;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(int deleteCount) {
        this.deleteCount = deleteCount;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }



}
