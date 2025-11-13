package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Spaces;
import org.lemon.bugtrackplatformserver.modules.dto.request.ProjectsIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpacesCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpacesPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.SpacesUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.SpaceResponse;
import org.lemon.bugtrackplatformserver.modules.service.SpacesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/spaces")
@Validated
public class SpacesController {

    private final SpacesService spacesService;

    public SpacesController(SpacesService spacesService) {
        this.spacesService = spacesService;
    }

    @PostMapping
    @ApiOperationLog(description = "创建空间")
    public Response<SpaceResponse> create(@Valid @RequestBody SpacesCreateRequest request) {
        Spaces s = Spaces.builder()
                .name(request.getName())
                .key(request.getKey())
                .ownerId(request.getOwnerId())
                .description(request.getDescription())
                .visibility(request.getVisibility())
                .status(request.getStatus() == null ? 1 : request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        spacesService.save(s);
        Spaces saved = spacesService.getById(s.getId());
        return Response.success(toResp(saved));
    }

    @GetMapping("/detail")
    @ApiOperationLog(description = "空间详情")
    public Response<SpaceResponse> detail(@Valid ProjectsIdRequest request) {
        Spaces s = spacesService.getById(request.getId());
        return Response.success(toResp(s));
    }

    @GetMapping
    @ApiOperationLog(description = "分页查询空间")
    public Response<Page<SpaceResponse>> page(@Valid SpacesPageQueryRequest query) {
        QueryWrapper<Spaces> qw = new QueryWrapper<>();
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            qw.and(w -> w.like("name", query.getKeyword()).or().like("key", query.getKeyword()));
        }
        if (query.getVisibility() != null) qw.eq("visibility", query.getVisibility());
        if (query.getStatus() != null) qw.eq("status", query.getStatus());
        qw.orderByDesc("id");
        Page<Spaces> page = spacesService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<SpaceResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    @PutMapping("/update")
    @ApiOperationLog(description = "更新空间")
    public Response<SpaceResponse> update(@Valid @RequestBody SpacesUpdateRequest request) {
        Spaces existing = spacesService.getById(request.getId());
        if (existing == null) return Response.success(null);
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getKey() != null) existing.setKey(request.getKey());
        if (request.getOwnerId() != null) existing.setOwnerId(request.getOwnerId());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getVisibility() != null) existing.setVisibility(request.getVisibility());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        spacesService.updateById(existing);
        Spaces updated = spacesService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除空间")
    public Response<Boolean> delete(@Valid ProjectsIdRequest request) {
        boolean removed = spacesService.removeById(request.getId());
        return Response.success(removed);
    }

    private SpaceResponse toResp(Spaces s) {
        if (s == null) return null;
        SpaceResponse r = new SpaceResponse();
        r.setId(s.getId());
        r.setName(s.getName());
        r.setKey(s.getKey());
        r.setOwnerId(s.getOwnerId());
        r.setDescription(s.getDescription());
        r.setVisibility(s.getVisibility());
        r.setStatus(s.getStatus());
        r.setCreatedAt(s.getCreatedAt());
        r.setUpdatedAt(s.getUpdatedAt());
        return r;
    }
}
