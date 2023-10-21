package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HomeInfo;
import cc.mrbird.febs.cos.service.IHomeInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/home-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeInfoController {

    private final IHomeInfoService homeInfoService;

    @GetMapping("/data")
    public R getHomeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("home", homeInfoService.getById(1));
            }
        };
        return R.ok(result);
    }

    @GetMapping("/detail")
    public R getHome() {
        return R.ok(homeInfoService.getById(1));
    }

    /**
     * 分页查询首页信息
     *
     * @param page
     * @param homeInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, HomeInfo homeInfo) {
        return R.ok();
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(homeInfoService.list());
    }

    /**
     * 新增首页信息
     *
     * @param homeInfo
     * @return
     */
    @PostMapping
    public R save(HomeInfo homeInfo) {
        return R.ok(homeInfoService.save(homeInfo));
    }

    /**
     * 修改首页信息
     *
     * @param homeInfo
     * @return
     */
    @PutMapping
    public R edit(HomeInfo homeInfo) {
        homeInfo.setId(1);
        return R.ok(homeInfoService.updateById(homeInfo));
    }

    /**
     * 删除首页信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(homeInfoService.removeByIds(ids));
    }
}
