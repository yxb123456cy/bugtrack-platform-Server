package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Settings;
import org.lemon.bugtrackplatformserver.modules.service.SettingsService;
import org.lemon.bugtrackplatformserver.modules.mapper.SettingsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【settings(项目配置表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings>
    implements SettingsService{

}




