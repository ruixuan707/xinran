package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.User;
import com.monco.core.entity.UserDiary;
import com.monco.core.manager.UserManager;
import com.monco.core.page.PageResult;
import com.monco.core.page.RoomOrderPage;
import com.monco.core.page.UserDiaryPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.UserDiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/4/11 22:17
 * @Description: 房东日记
 */
@Slf4j
@RestController
@RequestMapping("diary")
public class UserDiaryController {

    @Autowired
    UserDiaryService userDiaryService;

    @PostMapping
    public ApiResult save(@RequestBody UserDiaryPage userDiaryPage) {
        UserDiary userDiary = new UserDiary();
        pageToEntity(userDiaryPage, userDiary);
        User user = UserManager.get();
        userDiary.setUser(user);
        userDiaryService.save(userDiary);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody UserDiaryPage userDiaryPage) {
        UserDiary userDiary = new UserDiary();
        pageToEntity(userDiaryPage, userDiary);
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
        UserDiaryPage userDiaryPage = new UserDiaryPage();
        entityToPage(userDiary, userDiaryPage);
        return ApiResult.ok(userDiaryPage);
    }


    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          UserDiary userDiary, OrderQuery orderQuery) {
        Page<UserDiary> result = userDiaryService.getUserDiaryList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), userDiary);
        List<UserDiary> userDiaryList = result.getContent();
        List<UserDiaryPage> userDiaryPageList = new ArrayList<>();
        for (UserDiary diary : userDiaryList) {
            UserDiaryPage userDiaryPage = new UserDiaryPage();
            entityToPage(diary, userDiaryPage);
            userDiaryPageList.add(userDiaryPage);
        }
        PageResult pageResult = new PageResult(result.getPageable(), userDiaryPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    @GetMapping("home")
    public ApiResult getAll() {
        UserDiary userDiary = new UserDiary();
        userDiary.setDataDelete(ConstantUtils.UN_DELETE);
        Example<UserDiary> userDiaryExample = Example.of(userDiary);
        List<UserDiary> userDiaryList = userDiaryService.findAll(userDiaryExample, Sort.by("createDate").descending());
        List<UserDiaryPage> userDiaryPageList = new ArrayList<>();
        int i = 0;
        for (UserDiary diary : userDiaryList) {
            UserDiaryPage userDiaryPage = new UserDiaryPage();
            entityToPage(diary, userDiaryPage);
            userDiaryPageList.add(userDiaryPage);
            i++;
            if (i == 5) {
                break;
            }
        }
        return ApiResult.ok(userDiaryPageList);
    }

    public void entityToPage(UserDiary userDiary, UserDiaryPage userDiaryPage) {
        BeanUtils.copyProperties(userDiary, userDiaryPage);
        userDiaryPage.setId(userDiary.getId());
        if (userDiary.getUser() != null) {
            userDiaryPage.setNickName(userDiary.getUser().getNickName());
            userDiaryPage.setUserPic(userDiary.getUser().getPic());
        }
    }

    public void pageToEntity(UserDiaryPage userDiaryPage, UserDiary userDiary) {
        BeanUtils.copyProperties(userDiaryPage, userDiary);
    }
}
