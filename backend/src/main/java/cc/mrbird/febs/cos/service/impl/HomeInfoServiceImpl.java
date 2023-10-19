package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.HomeInfo;
import cc.mrbird.febs.cos.dao.HomeInfoMapper;
import cc.mrbird.febs.cos.service.IHomeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class HomeInfoServiceImpl extends ServiceImpl<HomeInfoMapper, HomeInfo> implements IHomeInfoService {

}
