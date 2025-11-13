package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Projects;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.ProjectResponse;
import org.lemon.bugtrackplatformserver.modules.service.ProjectsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/projects")
@Validated
public class ProjectsController {

    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    /**
     * 创建项目
     * @param request 创建项目请求体，包含空间ID、项目唯一标识、名称、描述、负责人、状态
     * @return 创建成功后的项目信息
     */
    @PostMapping
    @ApiOperationLog(description = "创建项目")
    public Response<ProjectResponse> create(@Valid @RequestBody ProjectsCreateRequest request) {
        Projects p = Projects.builder()
                .spaceId(request.getSpaceId())
                .key(request.getKey())
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(request.getOwnerId())
                .status(request.getStatus() == null ? 1 : request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        projectsService.save(p);
        Projects saved = projectsService.getById(p.getId());
        return Response.success(toResp(saved));
    }

    /**
     * 查询项目详情
     * @param request 项目ID请求
     * @return 项目信息
     */
    @GetMapping("/detail")
    @ApiOperationLog(description = "查询项目详情")
    public Response<ProjectResponse> detail(@Valid ProjectsIdRequest request) {
        Projects p = projectsService.getById(request.getId());
        return Response.success(toResp(p));
    }

    /**
     * 分页查询项目
     * @param query 查询参数：页码、页长、关键字、状态、空间ID
     * @return 分页项目信息
     */
    @GetMapping
    @ApiOperationLog(description = "分页查询项目")
    public Response<Page<ProjectResponse>> page(@Valid ProjectsPageQueryRequest query) {
        QueryWrapper<Projects> qw = getProjectsQueryWrapper(query);
        qw.orderByDesc("id");
        Page<Projects> page = projectsService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<ProjectResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    private static QueryWrapper<Projects> getProjectsQueryWrapper(ProjectsPageQueryRequest query) {
        QueryWrapper<Projects> qw = new QueryWrapper<>();
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            qw.and(w -> w.like("name", query.getKeyword())
                    .or().like("key", query.getKeyword())
                    .or().like("description", query.getKeyword()));
        }
        if (query.getStatus() != null) {
            qw.eq("status", query.getStatus());
        }
        if (query.getSpaceId() != null) {
            qw.eq("space_id", query.getSpaceId());
        }
        return qw;
    }

    /**
     * 更新项目
     * @param request 更新项目信息（包含ID）
     * @return 更新后的项目信息
     */
    @PutMapping("/update")
    @ApiOperationLog(description = "更新项目")
    public Response<ProjectResponse> update(@Valid @RequestBody ProjectsUpdateRequest request) {
        Projects existing = projectsService.getById(request.getId());
        if (existing == null) {
            return Response.success(null);
        }
        if (request.getSpaceId() != null) {
            existing.setSpaceId(request.getSpaceId());
        }
        if (request.getKey() != null) {
            existing.setKey(request.getKey());
        }
        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }
        if (request.getOwnerId() != null) {
            existing.setOwnerId(request.getOwnerId());
        }
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
        existing.setUpdatedAt(LocalDateTime.now());
        projectsService.updateById(existing);
        Projects updated = projectsService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    /**
     * 删除项目
     * @param request 项目ID请求
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除项目")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = projectsService.removeById(request.getId());
        return Response.success(removed);
    }

    private ProjectResponse toResp(Projects p) {
        if (p == null) return null;
        ProjectResponse r = new ProjectResponse();
        r.setId(p.getId());
        r.setSpaceId(p.getSpaceId());
        r.setKey(p.getKey());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setOwnerId(p.getOwnerId());
        r.setStatus(p.getStatus());
        r.setCreatedAt(p.getCreatedAt());
        r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }
}

