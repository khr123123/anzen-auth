package org.khr.anzenauth.model.vo;

import lombok.Data;

import java.util.List;

/**
 @author KK
 @create 2025-09-17-15:10
 */
@Data
public class RouterVO {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 路由参数：如 {"id": 1, "name": "ry"}
     */
    private String query;

    /**
     * 子路由
     */
    private List<RouterVO> children;

}
