package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Recommend;
import com.monco.core.entity.RoomInfo;
import com.monco.core.page.RecommendPage;
import com.monco.core.service.RecommendService;
import com.monco.core.service.RoomInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("recommend")
public class RecommendController {

    @Autowired
    RecommendService recommendService;

    @Autowired
    RoomInfoService roomInfoService;

    @PostMapping
    public ApiResult save(@RequestBody List<RecommendPage> recommendPageList) {
        List<Recommend> recommendList = new ArrayList<>();
        for (RecommendPage recommendPage : recommendPageList) {
            Recommend recommend = new Recommend();
            BeanUtils.copyProperties(recommendPage, recommend);
            recommendList.add(recommend);
        }
        recommendService.saveCollection(recommendList);
        return ApiResult.ok();
    }


    @PutMapping
    public ApiResult update(@RequestBody RecommendPage recommendPage) {
        Recommend recommend = new Recommend();
        BeanUtils.copyProperties(recommendPage, recommend);
        recommendService.save(recommend);
        return ApiResult.ok();
    }


    @DeleteMapping
    public ApiResult update(@RequestParam Long id) {
        Recommend recommend = recommendService.find(id);
        recommend.setDataDelete(ConstantUtils.DELETE);
        recommendService.save(recommend);
        return ApiResult.ok();
    }

    @GetMapping("all")
    public ApiResult get() {
        Recommend recommend = new Recommend();
        recommend.setDataDelete(ConstantUtils.UN_DELETE);
        Example<Recommend> recommendExample = Example.of(recommend);
        List<Recommend> recommendList = recommendService.findAll(recommendExample, Sort.by("id"));
        List<RecommendPage> recommendPageList = new ArrayList<>();
        for (Recommend recommend1 : recommendList) {
            RecommendPage recommendPage = new RecommendPage();
            if (recommend1.getType() == 1) {
                RoomInfo roomInfo = roomInfoService.find(recommend1.getRoomInfoId());
                recommendPage.setPic(roomInfo.getPic());
                recommendPage.setUserPic(roomInfo.getUser().getPic());
                recommendPage.setRoomUserName(roomInfo.getUser().getNickName());
                recommendPage.setPrice(roomInfo.getPrice());
            } else {
                recommendPage.setPic(recommend1.getPic());
                recommendPage.setCityName(recommend.getCityName());
                recommendPage.setRoomCount(100);
            }
            recommendPageList.add(recommendPage);
        }
        return ApiResult.ok(recommendPageList);
    }
}
