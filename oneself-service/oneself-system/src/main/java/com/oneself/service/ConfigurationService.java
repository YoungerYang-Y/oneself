package com.oneself.service;

import com.oneself.model.dto.ConfigurationDTO;
import com.oneself.model.dto.ConfigurationQueryDTO;
import com.oneself.model.dto.PageDTO;
import com.oneself.model.vo.ConfigurationVO;
import com.oneself.model.vo.PageVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * @author liuhuan
 * date 2025/9/10
 * packageName com.oneself.service
 * interfaceName ConfigurationService
 * description
 * version 1.0
 */
public interface ConfigurationService {

    /**
     * 新增系统配置
     *
     * @param dto 配置信息数据传输对象
     * @return 新增配置的主键 ID
     */
    String add(@Valid ConfigurationDTO dto);

    /**
     * 根据 ID 查询系统配置
     *
     * @param id 配置主键 ID
     * @return 配置详情视图对象
     */
    ConfigurationVO get(@Valid @NotBlank String id);

    /**
     * 更新系统配置
     *
     * @param id  配置主键 ID
     * @param dto 配置信息数据传输对象
     * @return 是否更新成功
     */
    boolean update(@Valid @NotBlank String id, @Valid ConfigurationDTO dto);

    /**
     * 批量删除系统配置
     *
     * @param ids 配置主键 ID 列表
     * @return 是否删除成功
     */
    boolean delete(@Valid @NotEmpty List<String> ids);

    /**
     * 分页查询系统配置
     *
     * @param dto 分页查询条件对象
     * @return 配置分页结果
     */
    PageVO<ConfigurationVO> page(@Valid PageDTO<ConfigurationQueryDTO> dto);
}
