package com.yazuo.xiaoya.swagger.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/6-22:10
 * Project:version_16
 * Package:PACKAGE_NAME
 * To change this template use File | Settings | File Templates.
 */

public class Entity {
    private String outBizNo;
    private String name;
    private List<Condition> conditions;

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public static class Condition{
        private String op;
        private String tagCode;
        private Object value;

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public String getTagCode() {
            return tagCode;
        }

        public void setTagCode(String tagCode) {
            this.tagCode = tagCode;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
    private String operatorId;
    private String operatorType;

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}
