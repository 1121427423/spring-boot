package com.springboot.oauth.server.service.impl;

import com.springboot.oauth.server.domain.TreeSelect;
import com.springboot.oauth.server.domain.table.SysMenu;
import com.springboot.oauth.server.mapper.SysMenuMapper;
import com.springboot.oauth.server.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("SysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long menuId) {
        return sysMenuMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(SysMenu row) {
        return sysMenuMapper.insert(row);
    }

    @Override
    public int insertSelective(SysMenu row) {
        return sysMenuMapper.insertSelective(row);
    }

    @Override
    public SysMenu selectByPrimaryKey(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenu row) {
        return sysMenuMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(SysMenu row) {
        return sysMenuMapper.updateByPrimaryKey(row);
    }

    @Override
    public List<SysMenu> selectMenuList() { return sysMenuMapper.selectMenuList(); }

    @Override
    public List<SysMenu> selectMenuListById(Long userId) { return sysMenuMapper.selectMenuListById(userId); }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();) {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (Objects.isNull(t.getParentId())) {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        return new HashSet<>(sysMenuMapper.selectMenuPermsByUserId(userId));
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tList = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (!Objects.isNull(n.getParentId()) && n.getParentId().longValue() == t.getMenuId().longValue()) {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
