package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.IssueSequence;
import org.lemon.bugtrackplatformserver.modules.service.IssueSequenceService;
import org.lemon.bugtrackplatformserver.modules.mapper.IssueSequenceMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【issue_sequence(项目问题编号序列表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class IssueSequenceServiceImpl extends ServiceImpl<IssueSequenceMapper, IssueSequence>
    implements IssueSequenceService{

}




