package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Notifications;
import org.lemon.bugtrackplatformserver.modules.dto.request.NotificationCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.NotificationPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.NotificationUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.NotificationResponse;
import org.lemon.bugtrackplatformserver.modules.service.NotificationsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @PostMapping
    @ApiOperationLog(description = "新增通知")
    public Response<NotificationResponse> create(@Valid @RequestBody NotificationCreateRequest request) {
        Notifications n = Notifications.builder()
                .userId(request.getUserId())
                .projectId(request.getProjectId())
                .type(request.getType())
                .targetId(request.getTargetId())
                .content(request.getContent())
                .isRead(0)
                .createdAt(LocalDateTime.now())
                .build();
        notificationsService.save(n);
        Notifications saved = notificationsService.getById(n.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "通知详情")
    public Response<NotificationResponse> detail(@Valid ProjectsIdRequest request) {
        Notifications n = notificationsService.getById(request.getId());
        return Response.success(toResp(n));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询通知")
    public Response<Page<NotificationResponse>> page(@Valid NotificationPageQueryRequest query) {
        QueryWrapper<Notifications> qw = new QueryWrapper<>();
        if (query.getUserId() != null) qw.eq("user_id", query.getUserId());
        if (query.getIsRead() != null) qw.eq("is_read", query.getIsRead());
        if (query.getType() != null) qw.eq("type", query.getType());
        qw.orderByDesc("id");
        Page<Notifications> page = notificationsService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<NotificationResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新通知状态")
    public Response<NotificationResponse> update(@Valid @RequestBody NotificationUpdateRequest request) {
        Notifications existing = notificationsService.getById(request.getId());
        if (existing == null) return Response.success(null);
        if (request.getIsRead() != null) existing.setIsRead(request.getIsRead());
        if (request.getContent() != null) existing.setContent(request.getContent());
        notificationsService.updateById(existing);
        Notifications updated = notificationsService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除通知")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = notificationsService.removeById(request.getId());
        return Response.success(removed);
    }

    private NotificationResponse toResp(Notifications n) {
        if (n == null) return null;
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setUserId(n.getUserId());
        r.setProjectId(n.getProjectId());
        r.setType(n.getType());
        r.setTargetId(n.getTargetId());
        r.setContent(n.getContent());
        r.setIsRead(n.getIsRead());
        r.setCreatedAt(n.getCreatedAt());
        return r;
    }
}
