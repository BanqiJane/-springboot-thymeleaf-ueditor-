package xyz.acproject.blogs.entity;

import java.util.ArrayList;
import java.util.List;

public class ArticleScaleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public ArticleScaleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCommentnumIsNull() {
            addCriterion("commentNum is null");
            return (Criteria) this;
        }

        public Criteria andCommentnumIsNotNull() {
            addCriterion("commentNum is not null");
            return (Criteria) this;
        }

        public Criteria andCommentnumEqualTo(Long value) {
            addCriterion("commentNum =", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumNotEqualTo(Long value) {
            addCriterion("commentNum <>", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumGreaterThan(Long value) {
            addCriterion("commentNum >", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumGreaterThanOrEqualTo(Long value) {
            addCriterion("commentNum >=", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumLessThan(Long value) {
            addCriterion("commentNum <", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumLessThanOrEqualTo(Long value) {
            addCriterion("commentNum <=", value, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumIn(List<Long> values) {
            addCriterion("commentNum in", values, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumNotIn(List<Long> values) {
            addCriterion("commentNum not in", values, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumBetween(Long value1, Long value2) {
            addCriterion("commentNum between", value1, value2, "commentnum");
            return (Criteria) this;
        }

        public Criteria andCommentnumNotBetween(Long value1, Long value2) {
            addCriterion("commentNum not between", value1, value2, "commentnum");
            return (Criteria) this;
        }

        public Criteria andArticlepvIsNull() {
            addCriterion("articlePv is null");
            return (Criteria) this;
        }

        public Criteria andArticlepvIsNotNull() {
            addCriterion("articlePv is not null");
            return (Criteria) this;
        }

        public Criteria andArticlepvEqualTo(Long value) {
            addCriterion("articlePv =", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvNotEqualTo(Long value) {
            addCriterion("articlePv <>", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvGreaterThan(Long value) {
            addCriterion("articlePv >", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvGreaterThanOrEqualTo(Long value) {
            addCriterion("articlePv >=", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvLessThan(Long value) {
            addCriterion("articlePv <", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvLessThanOrEqualTo(Long value) {
            addCriterion("articlePv <=", value, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvIn(List<Long> values) {
            addCriterion("articlePv in", values, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvNotIn(List<Long> values) {
            addCriterion("articlePv not in", values, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvBetween(Long value1, Long value2) {
            addCriterion("articlePv between", value1, value2, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepvNotBetween(Long value1, Long value2) {
            addCriterion("articlePv not between", value1, value2, "articlepv");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseIsNull() {
            addCriterion("articlePraise is null");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseIsNotNull() {
            addCriterion("articlePraise is not null");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseEqualTo(Long value) {
            addCriterion("articlePraise =", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseNotEqualTo(Long value) {
            addCriterion("articlePraise <>", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseGreaterThan(Long value) {
            addCriterion("articlePraise >", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseGreaterThanOrEqualTo(Long value) {
            addCriterion("articlePraise >=", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseLessThan(Long value) {
            addCriterion("articlePraise <", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseLessThanOrEqualTo(Long value) {
            addCriterion("articlePraise <=", value, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseIn(List<Long> values) {
            addCriterion("articlePraise in", values, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseNotIn(List<Long> values) {
            addCriterion("articlePraise not in", values, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseBetween(Long value1, Long value2) {
            addCriterion("articlePraise between", value1, value2, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andArticlepraiseNotBetween(Long value1, Long value2) {
            addCriterion("articlePraise not between", value1, value2, "articlepraise");
            return (Criteria) this;
        }

        public Criteria andApidIsNull() {
            addCriterion("apid is null");
            return (Criteria) this;
        }

        public Criteria andApidIsNotNull() {
            addCriterion("apid is not null");
            return (Criteria) this;
        }

        public Criteria andApidEqualTo(Integer value) {
            addCriterion("apid =", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidNotEqualTo(Integer value) {
            addCriterion("apid <>", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidGreaterThan(Integer value) {
            addCriterion("apid >", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidGreaterThanOrEqualTo(Integer value) {
            addCriterion("apid >=", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidLessThan(Integer value) {
            addCriterion("apid <", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidLessThanOrEqualTo(Integer value) {
            addCriterion("apid <=", value, "apid");
            return (Criteria) this;
        }

        public Criteria andApidIn(List<Integer> values) {
            addCriterion("apid in", values, "apid");
            return (Criteria) this;
        }

        public Criteria andApidNotIn(List<Integer> values) {
            addCriterion("apid not in", values, "apid");
            return (Criteria) this;
        }

        public Criteria andApidBetween(Integer value1, Integer value2) {
            addCriterion("apid between", value1, value2, "apid");
            return (Criteria) this;
        }

        public Criteria andApidNotBetween(Integer value1, Integer value2) {
            addCriterion("apid not between", value1, value2, "apid");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}