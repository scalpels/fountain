package com.scalpels.fountain.basic;


import java.util.ArrayList;
import java.util.List;

public  class Criteria {
    protected List<Criterion> criteria;

    public Criteria() {
        super();
        criteria = new ArrayList<Criterion>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
        return criteria;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andConditionIsNull(String condition) {
        addCriterion(String.format("%s is null",condition));
        return  this;
    }

    public Criteria andConditionIsNotNull(String condition) {
        addCriterion(String.format("%s is not null",condition));
        return  this;
    }

    public Criteria andConditionEqualTo(String condition,Object value) {
        addCriterion(String.format("%s =",condition), value, condition);
        return this;
    }

    public Criteria andConditionNotEqualTo(String condition,Object value) {
        addCriterion(String.format("%s <>",condition), value, "id");
        return  this;
    }

    public Criteria andConditionGreaterThan(String condition,Object value) {
        addCriterion(String.format("%s >",condition), value, condition);
        return  this;
    }

    public Criteria andConditionGreaterThanOrEqualTo(String condition,Object value) {
        addCriterion(String.format("%s >=",condition), value, condition);
        return  this;
    }

    public Criteria andConditionLessThan(String condition,Object value) {
        addCriterion(String.format("%s <",condition), value, condition);
        return  this;
    }

    public Criteria andConditionLessThanOrEqualTo(String condition,Object value) {
        addCriterion(String.format("%s <=",condition), value, condition);
        return  this;
    }

    public Criteria andConditionIn(String condition,List<Object> values) {
        addCriterion(String.format("%s in",condition), values, condition);
        return  this;
    }

    public Criteria andConditionNotIn(String condition,List<Object> values) {
        addCriterion(String.format("%s not in",condition), values, condition);
        return  this;
    }

    public Criteria andConditionBetween(String condition,Object value1, Object value2) {
        addCriterion(String.format("%s between",condition), value1,value2, condition);
        return  this;
    }

    public Criteria andConditionNotBetween(String condition,Object value1, Object value2) {
        addCriterion(String.format("%s not between",condition), value1,value2, condition);
        return  this;
    }


    public Criteria andConditionLike(String condition,String value) {
        addCriterion(String.format("%s like",condition), value, condition);
        return  this;
    }

    public Criteria andConditionNotLike(String condition,String value) {
        addCriterion(String.format("%s not like",condition), value, condition);
        return  this;
    }

}
