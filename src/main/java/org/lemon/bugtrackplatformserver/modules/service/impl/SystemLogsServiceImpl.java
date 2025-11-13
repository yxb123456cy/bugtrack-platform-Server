package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.SystemLogs;
import org.lemon.bugtrackplatformserver.modules.service.SystemLogsService;
import org.lemon.bugtrackplatformserver.modules.mapper.SystemLogsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【system_logs(系统日志表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:25
*/
@Service
public class SystemLogsServiceImpl extends ServiceImpl<SystemLogsMapper, SystemLogs>
    implements SystemLogsService{

}




