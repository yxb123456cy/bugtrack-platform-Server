package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.IssueTags;
import org.lemon.bugtrackplatformserver.modules.service.IssueTagsService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssueTagsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issue_tags(问题标签关联表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssueTagsServiceImpl extends ServiceImpl<IssueTagsMapper, IssueTags>
    implements IssueTagsService{

}




