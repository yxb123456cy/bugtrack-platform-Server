package org.lemon.bugtrackplatformserver.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemon.bugtrackplatformserver.modules.domain.Notifications;
import org.lemon.bugtrackplatformserver.modules.service.NotificationsService;
import org.lemon.bugtrackplatformserver.modules.mapper.NotificationsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【notifications(系统通知表)】的数据库操作Service实现
* @createDate 2025-11-13 16:52:24
*/
@Service
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications>
    implements NotificationsService{

}




