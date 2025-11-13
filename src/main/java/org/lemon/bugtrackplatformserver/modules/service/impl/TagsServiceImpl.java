package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Tags;
import org.lemon.bugtrackplatformserver.modules.service.TagsService;
import org.lemon.bugtrackplatformserver.modules.mapper.TagsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【tags(标签表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:25
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

}




