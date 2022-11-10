package com.zimug.dongbb.server.jwt.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zimug.commons.exception.CustomException;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.commons.model.tree.DataTreeUtil;
import com.zimug.dongbb.persistence.system.mapper.MySystemMapper;
import com.zimug.dongbb.persistence.system.mapper.SysMenuMapper;
import com.zimug.dongbb.persistence.system.mapper.SysRoleMenuMapper;
import com.zimug.dongbb.persistence.system.model.SysMenu;
import com.zimug.dongbb.persistence.system.model.SysRoleMenu;
import com.zimug.dongbb.server.jwt.system.model.SysMenuNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuService {

  @Resource
  private MySystemMapper mySystemMapper;
  @Resource
  private SysMenuMapper sysMenuMapper;
  @Resource
  private SysRoleMenuMapper sysRoleMenuMapper;

  /**
   * 菜单管理：根据查询条件查询树形结构菜单列表
   * @param menuNameLike 菜单名称
   * @param menuStatus 菜单可用状态
   * @return 菜单列表或树形列表
   */
  public List<SysMenuNode> getMenuTree(String menuNameLike,
                                       Boolean menuStatus) {
    //保证数据库里面level=1的节点只有一个，根节点
    SysMenu rootSysMenu = sysMenuMapper.selectOne(
      new QueryWrapper<SysMenu>().eq("level",1));

    if (rootSysMenu != null) {
      Long rootMenuId = rootSysMenu.getId();

      List<SysMenu> sysMenus
        = mySystemMapper.selectMenuTree(rootMenuId, menuNameLike, menuStatus);

      List<SysMenuNode> sysMenuNodes = sysMenus.stream().map(item -> {
        SysMenuNode bean = new SysMenuNode();
        BeanUtils.copyProperties(item, bean);
        return bean;
      }).collect(Collectors.toList());

      if (StringUtils.isNotEmpty(menuNameLike) || menuStatus != null) {
        return sysMenuNodes;//根据菜单名称查询，返回平面列表
      } else {//否则返回菜单的树型结构列表
        return DataTreeUtil.buildTree(sysMenuNodes, rootMenuId);
      }

    } else {
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "请先在数据库内为菜单配置一个分类的根节点，level=1");
    }
  }


  public void updateMenu(SysMenu sysmenu){
    if(sysmenu.getId() == null){
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "修改操作必须带主键");
    }else{
      sysMenuMapper.updateById(sysmenu);
    }
  }

  @Transactional
  public void addMenu(SysMenu sysmenu){
    setMenuIdsAndLevel(sysmenu);

    sysmenu.setIsLeaf(true); //新增的菜单节点都是子节点，没有下级
    SysMenu parent = new SysMenu();
    parent.setId(sysmenu.getMenuPid());
    parent.setIsLeaf(false);//更新父节点为非子节点。
    sysMenuMapper.updateById(parent);

    sysmenu.setStatus(false);//设置是否禁用，新增节点默认可用
    sysMenuMapper.insert(sysmenu);
  }


  @Transactional
  public void deleteMenu(SysMenu sysMenu){
    //查找被删除节点的子节点
    List<SysMenu> myChilds = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
      .like("menu_pids","["+ sysMenu.getId() +"]"));

    if(myChilds.size() > 0){
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "不能删除含有下级菜单的菜单");
    }

    //查找被删除节点的父节点
    List<SysMenu> myFatherChilds = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
      .like("menu_pids","["+ sysMenu.getMenuPid() +"]"));

    //我的父节点只有我这一个子节点，而我还要被删除，更新父节点为叶子节点。
    if(myFatherChilds.size() == 1){
      SysMenu parent = new SysMenu();
      parent.setId(sysMenu.getMenuPid());
      parent.setIsLeaf(true);//更新父节点为叶子节点。
      sysMenuMapper.updateById(parent);
    }
    //删除节点
    sysMenuMapper.deleteById(sysMenu.getId());
  }

  //设置某子节点的所有祖辈id
  private void setMenuIdsAndLevel(SysMenu child){
    List<SysMenu> allMenus = sysMenuMapper.selectList(null);
    for(SysMenu sysMenu: allMenus){
      //从组织列表中找到自己的直接父亲
      if(sysMenu.getId().equals(child.getMenuPid())){
        //直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
        //爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
        child.setMenuPids(sysMenu.getMenuPids() + ",[" + child.getMenuPid() + "]" );
        child.setLevel(sysMenu.getLevel() + 1);
      }
    }
  }

  //获取某角色勾选的菜单权限
  public List<String> getCheckedKeys(Long roleId){
    return mySystemMapper.selectMenuCheckedKeys(roleId);
  }
  //获取在菜单中展开的菜单项
  public List<String> getExpandedKeys(){
    return mySystemMapper.selectMenuExpandedKeys();
  }
  //保存为某角色新勾选的菜单项目
  @Transactional
  public void saveCheckedKeys(Long roleId,List<Long> checkedIds){

    //保存之前先删除
    sysRoleMenuMapper.delete(new UpdateWrapper<SysRoleMenu>()
                                .eq("role_id",roleId));
    mySystemMapper.insertRoleMenuIds(roleId,checkedIds);
  }

  //根据某用户的用户名查询该用户可以访问的菜单项（系统左侧边栏菜单）
  public List<SysMenuNode> getMenuTreeByUsername(String username) {
    List<SysMenu> sysMenus = mySystemMapper.selectMenuByUsername(username);

    if (sysMenus.size() > 0) {
      Long rootMenuId = sysMenus.get(0).getId();

      List<SysMenuNode> sysMenuNodes =
        sysMenus.stream().map(item -> {
          SysMenuNode bean = new SysMenuNode();
          BeanUtils.copyProperties(item, bean);
          return bean;
        }).collect(Collectors.toList());
      return DataTreeUtil.buildTreeWithoutRoot(sysMenuNodes, rootMenuId);
    }
    return new ArrayList<>();
  }

  //菜单管理：更新菜单的禁用状态
  public void updateStatus(Long id,Boolean status){
    Assert.isTrue(id != null, "修改操作必须带主键");
    SysMenu sysMenu = new SysMenu();
    sysMenu.setId(id);
    sysMenu.setStatus(status);
    sysMenuMapper.updateById(sysMenu);
  }


  //菜单管理：更新菜单隐藏状态
  public void updateHidden(Long id,Boolean hidden){
    Assert.isTrue(id != null, "修改操作必须带主键");
    SysMenu sysMenu = new SysMenu();
    sysMenu.setId(id);
    sysMenu.setHidden(hidden);
    sysMenuMapper.updateById(sysMenu);
  }
}
