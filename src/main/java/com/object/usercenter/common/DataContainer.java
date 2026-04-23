package com.object.usercenter.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据容器
 * @param <T> 数据类型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "包装增删改数据的容器")
public class DataContainer<T> implements Serializable {

    /**
     * 新增数据
     */
    private List<T> addedData = null;

    /**
     * 修改数据
     */
    private List<T> modifiedData = null;

    /**
     * 删除数据
     */
    private List<T> removedData = null;

}
