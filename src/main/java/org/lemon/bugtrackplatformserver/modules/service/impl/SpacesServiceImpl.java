package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Spaces;
import org.lemon.bugtrackplatformserver.modules.service.SpacesService;
import org.lemon.bugtrackplatformserver.modules.mapper.SpacesMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【spaces(空间表（多租户/团队维度）)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:25
*/
@Service
public class SpacesServiceImpl extends ServiceImpl<SpacesMapper, Spaces>
    implements SpacesService{

}




