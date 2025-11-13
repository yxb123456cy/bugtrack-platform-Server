package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.ProjectMembers;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectMembersCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectMembersUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectMembersPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.ProjectMemberResponse;
import org.lemon.bugtrackplatformserver.modules.service.ProjectMembersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/project-members")
@Validated
public class ProjectMembersController {

    private final ProjectMembersService projectMembersService;

    public ProjectMembersController(ProjectMembersService projectMembersService) {
        this.projectMembersService = projectMembersService;
    }

    @PostMapping
    @ApiOperationLog(description = "添加项目成员")
    public Response<ProjectMemberResponse> create(@Valid @RequestBody ProjectMembersCreateRequest request) {
        ProjectMembers pm = ProjectMembers.builder()
                .projectId(request.getProjectId())
                .userId(request.getUserId())
                .role(request.getRole())
                .joinedAt(LocalDateTime.now())
                .build();
        projectMembersService.save(pm);
        ProjectMembers saved = projectMembersService.getById(pm.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "查询项目成员详情")
    public Response<ProjectMemberResponse> detail(@Valid ProjectsIdRequest request) {
        ProjectMembers pm = projectMembersService.getById(request.getId());
        return Response.success(toResp(pm));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询项目成员")
    public Response<Page<ProjectMemberResponse>> page(@Valid ProjectMembersPageQueryRequest query) {
        QueryWrapper<ProjectMembers> qw = new QueryWrapper<>();
        if (query.getProjectId() != null) {
            qw.eq("project_id", query.getProjectId());
        }
        if (query.getUserId() != null) {
            qw.eq("user_id", query.getUserId());
        }
        if (query.getRole() != null) {
            qw.eq("role", query.getRole());
        }
        qw.orderByDesc("id");
        Page<ProjectMembers> page = projectMembersService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<ProjectMemberResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新项目成员")
    public Response<ProjectMemberResponse> update(@Valid @RequestBody ProjectMembersUpdateRequest request) {
        ProjectMembers existing = projectMembersService.getById(request.getId());
        if (existing == null) {
            return Response.success(null);
        }
        if (request.getRole() != null) existing.setRole(request.getRole());
        existing.setJoinedAt(existing.getJoinedAt() == null ? LocalDateTime.now() : existing.getJoinedAt());
        projectMembersService.updateById(existing);
        ProjectMembers updated = projectMembersService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除项目成员")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = projectMembersService.removeById(request.getId());
        return Response.success(removed);
    }

    private ProjectMemberResponse toResp(ProjectMembers pm) {
        if (pm == null) return null;
        ProjectMemberResponse r = new ProjectMemberResponse();
        r.setId(pm.getId());
        r.setProjectId(pm.getProjectId());
        r.setUserId(pm.getUserId());
        r.setRole(pm.getRole());
        r.setJoinedAt(pm.getJoinedAt());
        return r;
    }
}
