package com.yazuo.xiaoya.swagger.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/6-20:41
 * Project:version_16
 * Package:PACKAGE_NAME
 * To change this template use File | Settings | File Templates.
 */

public class Test {


    public static final String json = "{data:[\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\",\"{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":\"[ \"{\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}\"]\",\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}\"]}";
    public static final String json1= "{data:[{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"},{\"out_biz_no\":\"1331313113313\",\"name\":\"30岁以下白领\",\"conditions\":[ {\"op\":\"IN\",\"tagCode\":\"pam_gender\",\"value\":\"1\"}],\"operator_id\":\"2088102146931393\",\"operator_type\":\"PROVIDER\"}]}";

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for(int i=0;i<1000000;i++){
                    NormalizerJSONString normalizerJSONString =  new NormalizerJSONString(json);
                }
                System.out.println("标准化字符串=>"+(System.currentTimeMillis()-start));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for(int i=0;i<1000000;i++){
                    NormalizerJSONString normalizerJSONString =  new NormalizerJSONString(json);
                    ListEntity entity = JSON.parseObject(normalizerJSONString.getNormalizerData(),ListEntity.class);
                }
                System.out.println("格式化后JSON=>"+(System.currentTimeMillis()-start));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for(int i=0;i<1000000;i++){
                    ListEntity entity = JSON.parseObject(json1,ListEntity.class);
                }
                System.out.println("直接JSON=>"+(System.currentTimeMillis()-start));
            }
        }).start();





    }
}
