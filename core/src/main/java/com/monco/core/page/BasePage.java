package com.monco.core.page;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BasePage
 *
 * @author Mengke
 * @version 1.0.0
 */
@Data
public class BasePage implements Serializable {
    private static final long serialVersionUID = 6470839785182956096L;
    private Long id;
    private Integer dataStatus;
    private Integer dataDelete;
    private String remarks;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    /*private Long[] batchIds;*/
    private Long createdId;
    private String createdName;
    private Long updatedId;
    private String updatedName;
    private Long version;
}