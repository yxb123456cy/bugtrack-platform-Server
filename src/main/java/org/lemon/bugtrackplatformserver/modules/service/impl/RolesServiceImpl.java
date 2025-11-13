package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Roles;
import org.lemon.bugtrackplatformserver.modules.service.RolesService;
import org.lemon.bugtrackplatformserver.modules.mapper.RolesMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【roles(角色定义表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles>
    implements RolesService{

}




