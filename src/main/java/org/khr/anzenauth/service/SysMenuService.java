package org.khr.anzenauth.service;

import com.mybatisflex.core.service.IService;
import org.khr.anzenauth.model.entity.SysMenu;
import org.khr.anzenauth.model.vo.RouterVO;

import java.util.List;
import java.util.Set;

/**
 * 菜单表 服务层。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
public interface SysMenuService extends IService<SysMenu> {


    Set<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> selectMenuTreeByUserId(Long loginUserId, boolean includeButton);

    List<SysMenu> listMenu2Tree();

}
