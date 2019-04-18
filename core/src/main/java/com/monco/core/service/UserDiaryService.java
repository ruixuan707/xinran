package com.monco.core.service;

import com.monco.core.entity.UserDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/4/11 22:08
 * @Description:
 */
public interface UserDiaryService extends BaseService<UserDiary, Long> {

    /**
     * 房东日记分页查询
     *
     * @param pageable
     * @param userDiary
     * @return
     */
    Page<UserDiary> getUserDiaryList(Pageable pageable, UserDiary userDiary);
}
