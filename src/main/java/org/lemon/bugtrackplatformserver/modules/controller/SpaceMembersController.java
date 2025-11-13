package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.SpaceMembers;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpaceMembersCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpaceMembersPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpaceMembersUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.SpaceMemberResponse;
import org.lemon.bugtrackplatformserver.modules.service.SpaceMembersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/space-members")
@Validated
public class SpaceMembersController {

    private final SpaceMembersService spaceMembersService;

    public SpaceMembersController(SpaceMembersService spaceMembersService) {
        this.spaceMembersService = spaceMembersService;
    }

    @PostMapping
    @ApiOperationLog(description = "添加空间成员")
    public Response<SpaceMemberResponse> create(@Valid @RequestBody SpaceMembersCreateRequest request) {
        SpaceMembers sm = SpaceMembers.builder()
                .spaceId(request.getSpaceId())
                .userId(request.getUserId())
                .role(request.getRole())
                .joinedAt(LocalDateTime.now())
                .build();
        spaceMembersService.save(sm);
        SpaceMembers saved = spaceMembersService.getById(sm.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "空间成员详情")
    public Response<SpaceMemberResponse> detail(@Valid ProjectsIdRequest request) {
        SpaceMembers sm = spaceMembersService.getById(request.getId());
        return Response.success(toResp(sm));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询空间成员")
    public Response<Page<SpaceMemberResponse>> page(@Valid SpaceMembersPageQueryRequest query) {
        QueryWrapper<SpaceMembers> qw = new QueryWrapper<>();
        if (query.getSpaceId() != null) qw.eq("space_id", query.getSpaceId());
        if (query.getUserId() != null) qw.eq("user_id", query.getUserId());
        if (query.getRole() != null) qw.eq("role", query.getRole());
        qw.orderByDesc("id");
        Page<SpaceMembers> page = spaceMembersService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<SpaceMemberResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新空间成员")
    public Response<SpaceMemberResponse> update(@Valid @RequestBody SpaceMembersUpdateRequest request) {
        SpaceMembers existing = spaceMembersService.getById(request.getId());
        if (existing == null) return Response.success(null);
        if (request.getRole() != null) existing.setRole(request.getRole());
        existing.setJoinedAt(existing.getJoinedAt() == null ? LocalDateTime.now() : existing.getJoinedAt());
        spaceMembersService.updateById(existing);
        SpaceMembers updated = spaceMembersService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除空间成员")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = spaceMembersService.removeById(request.getId());
        return Response.success(removed);
    }

    private SpaceMemberResponse toResp(SpaceMembers sm) {
        if (sm == null) return null;
        SpaceMemberResponse r = new SpaceMemberResponse();
        r.setId(sm.getId());
        r.setSpaceId(sm.getSpaceId());
        r.setUserId(sm.getUserId());
        r.setRole(sm.getRole());
        r.setJoinedAt(sm.getJoinedAt());
        return r;
    }
}
