package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.ProjectMembers;
import org.lemon.bugtrackplatformserver.modules.service.ProjectMembersService;
import org.lemon.bugtrackplatformserver.modules.mapper.ProjectMembersMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【project_members(项目成员表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class ProjectMembersServiceImpl extends ServiceImpl<ProjectMembersMapper, ProjectMembers>
    implements ProjectMembersService{

}




