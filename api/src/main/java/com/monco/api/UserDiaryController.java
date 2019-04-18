package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.User;
import com.monco.core.entity.UserDiary;
import com.monco.core.manager.UserManager;
import com.monco.core.page.RoomOrderPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.UserDiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: monco
 * @Date: 2019/4/11 22:17
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("user-diary")
public class UserDiaryController {

    @Autowired
    UserDiaryService userDiaryService;

    @PostMapping
    public ApiResult save(@RequestBody UserDiary userDiary) {
        User user = UserManager.get();
        userDiary.setUser(user);
        userDiaryService.save(userDiary);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody UserDiary userDiary) {
        User user = UserManager.get();
        userDiary.setUser(user);
        userDiaryService.save(userDiary);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        UserDiary userDiary = userDiaryService.find(id);
        userDiary.setDataDelete(ConstantUtils.DELETE);
        userDiaryService.save(userDiary);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        UserDiary userDiary = userDiaryService.find(id);
        return ApiResult.ok(userDiary);
    }


    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          UserDiary userDiary, OrderQuery orderQuery) {
        Page<UserDiary> result = userDiaryService.getUserDiaryList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), userDiary);
        return ApiResult.ok(result);
    }


}
