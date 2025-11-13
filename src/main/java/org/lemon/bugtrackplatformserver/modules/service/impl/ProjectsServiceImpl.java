package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Projects;
import org.lemon.bugtrackplatformserver.modules.service.ProjectsService;
import org.lemon.bugtrackplatformserver.modules.mapper.ProjectsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【projects(项目表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects>
    implements ProjectsService{

}




