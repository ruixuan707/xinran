package com.monco.core.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.monco.core.listener.EntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity - 实体基类
 *
 * @author monco
 * @version 1.0.0
 */
@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5022086810720654443L;

    /**
     * 主键
     */
    private ID id;
    /**
     * 版本
     */
    private Long version;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态标志位  0:停用 1:正常
     */
    private Integer dataStatus = 1;
    /**
     * 删除标志位  0:正常 1:删除
     */
    private Integer dataDelete = 0;
    /**
     * 创建人ID
     */
    private Long createdId;
    /**
     * 创建人
     */
    private String createdName;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改人ID
     */
    private Long updatedId;
    /**
     * 修改人
     */
    private String updatedName;
    /**
     * 修改时间
     */
    private Date updateDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Column(nullable = false, updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Version
    @Column(nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getDataDelete() {
        return dataDelete;
    }

    public void setDataDelete(Integer dataDelete) {
        this.dataDelete = dataDelete;
    }

    @Column(nullable = false)
    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    @Column(nullable = false)
    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Long updatedId) {
        this.updatedId = updatedId;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    @Transient
    @JSONField(serialize = false)
    public boolean isNew() {
        return getId() == null;
    }

}