package com.monco.core.listener;


import com.monco.core.entity.BaseEntity;
import com.monco.core.entity.User;
import com.monco.core.manager.UserManager;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * EntityListener - 创建日期、修改日期、版本处理
 *
 * @author monco
 * @version 1.0.0
 */
public class EntityListener {

    @PrePersist
    public void prePersist(BaseEntity<?> entity) {
        User user = UserManager.get();
        if (user != null) {
            entity.setCreatedId(user.getId());
            entity.setCreatedName(user.getUsername());
            entity.setUpdatedId(user.getId());
            entity.setUpdatedName(user.getUsername());
        } else {
            entity.setCreatedId(1L);
            entity.setCreatedName("monco");
            entity.setUpdatedId(1L);
            entity.setUpdatedName("monco");
        }
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        entity.setDataDelete(0);
        entity.setDataStatus(1);
        entity.setVersion(0L);
    }

    @PreUpdate
    public void preUpdate(BaseEntity<?> entity) {
        User user = UserManager.get();
        if (user != null) {
            entity.setUpdatedId(user.getId());
            entity.setUpdatedName(user.getUsername());
        } else {
            entity.setUpdatedId(1L);
            entity.setUpdatedName("monco");
        }
        entity.setUpdateDate(new Date());
    }
}