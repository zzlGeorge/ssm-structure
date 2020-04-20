package com.vct.goods.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author mading
 * @email zhang0909990@qq.com
 * @date 2020-04-15 11:27:59
 */
public class GoodsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private String goodsName;
    //
    private BigDecimal goodsWeight;
    //
    private BigDecimal goodsPrice;
    //
    private Date createTime;
    //
    private Date modifyTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public BigDecimal getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}
