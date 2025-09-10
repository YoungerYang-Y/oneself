package com.oneself.client;

import com.oneself.client.fallback.UserClientFallbackFactory;
import com.oneself.model.vo.ResponseVO;
import com.oneself.model.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liuhuan
 * date 2025/1/24
 * packageName com.oneself.client
 * interfaceName UserClient
 * description 用户相关 Feign 接口
 * version 1.0
 */
@Tag(name = "feign system")
@FeignClient(value = "oneself-system", path = "/oneself-system/user",
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {

    @Operation(summary = "根据用户名查询登录用户信息")
    @GetMapping("/get/login/user/by/{name}")
    ResponseVO<UserVO> getLoginUserByName(@PathVariable("name") @Valid @NotBlank String name);
}