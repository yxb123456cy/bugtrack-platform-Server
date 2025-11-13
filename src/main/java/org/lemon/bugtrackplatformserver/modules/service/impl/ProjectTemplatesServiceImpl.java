package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.ProjectTemplates;
import org.lemon.bugtrackplatformserver.modules.service.ProjectTemplatesService;
import org.lemon.bugtrackplatformserver.modules.mapper.ProjectTemplatesMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【project_templates(项目模板表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class ProjectTemplatesServiceImpl extends ServiceImpl<ProjectTemplatesMapper, ProjectTemplates>
    implements ProjectTemplatesService{

}




