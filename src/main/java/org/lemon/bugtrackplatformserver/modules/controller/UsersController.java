package org.lemon.bugtrackplatformserver.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.lemon.bugtrackplatformserver.aspect.ApiOperationLog;
import org.lemon.bugtrackplatformserver.common.response.Response;
import org.lemon.bugtrackplatformserver.modules.domain.Users;
import org.lemon.bugtrackplatformserver.modules.dto.request.UsersCreateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.UsersIdRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.UsersPageQueryRequest;
import org.lemon.bugtrackplatformserver.modules.dto.request.UsersUpdateRequest;
import org.lemon.bugtrackplatformserver.modules.dto.response.UserResponse;
import org.lemon.bugtrackplatformserver.modules.service.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@Validated
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 创建用户
     * @param request 创建用户请求体，包含用户名、邮箱、显示名、密码哈希、状态
     * @return 创建成功后的用户信息
     */
    @PostMapping
    @ApiOperationLog(description = "创建用户")
    public Response<UserResponse> create(@Valid @RequestBody UsersCreateRequest request) {
        Users user = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .displayName(request.getDisplayName())
                .passwordHash(request.getPasswordHash())
                .status(request.getStatus() == null ? 1 : request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        usersService.save(user);
        Users saved = usersService.getById(user.getId());
        return Response.success(toResp(saved));
    }

    /**
     * 根据ID查询用户
     * @return 用户信息
     */
    @GetMapping("/detail")
    @ApiOperationLog(description = "查询用户详情")
    public Response<UserResponse> detail(@Valid UsersIdRequest request) {
        Users user = usersService.getById(request.getId());
        return Response.success(toResp(user));
    }

    /**
     * 分页查询用户
     * @return 用户分页列表
     */
    @GetMapping
    @ApiOperationLog(description = "分页查询用户")
    public Response<Page<UserResponse>> page(@Valid UsersPageQueryRequest query) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            qw.and(w -> w.like("username", query.getKeyword())
                    .or().like("email", query.getKeyword())
                    .or().like("display_name", query.getKeyword()));
        }
        if (query.getStatus() != null) {
            qw.eq("status", query.getStatus());
        }
        qw.orderByDesc("id");
        Page<Users> page = usersService.page(new Page<>(query.getPageNum(), query.getPageSize()), qw);
        Page<UserResponse> respPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        respPage.setRecords(page.getRecords().stream().map(this::toResp).toList());
        return Response.success(respPage);
    }

    /**
     * 更新用户
     * @param request 更新请求体，允许更新邮箱、显示名、状态、密码哈希
     * @return 更新后的用户信息
     */
    @PutMapping("/update")
    @ApiOperationLog(description = "更新用户")
    public Response<UserResponse> update(@Valid @RequestBody UsersUpdateRequest request) {
        Users existing = usersService.getById(request.getId());
        if (existing == null) {
            return Response.success(null);
        }
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getDisplayName() != null) {
            existing.setDisplayName(request.getDisplayName());
        }
        if (request.getPasswordHash() != null) {
            existing.setPasswordHash(request.getPasswordHash());
        }
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
        existing.setUpdatedAt(LocalDateTime.now());
        usersService.updateById(existing);
        Users updated = usersService.getById(existing.getId());
        return Response.success(toResp(updated));
    }

    /**
     * 根据ID删除用户
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    @ApiOperationLog(description = "删除用户")
    public Response<Boolean> delete(@Valid UsersIdRequest request) {
        boolean removed = usersService.removeById(request.getId());
        return Response.success(removed);
    }

    private UserResponse toResp(Users u) {
        if (u == null) return null;
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setUsername(u.getUsername());
        r.setEmail(u.getEmail());
        r.setDisplayName(u.getDisplayName());
        r.setStatus(u.getStatus());
        r.setCreatedAt(u.getCreatedAt());
        r.setUpdatedAt(u.getUpdatedAt());
        return r;
    }
}
