package com.zimug.dongbb.server.jwt.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zimug.commons.exception.CustomException;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.commons.model.tree.DataTreeUtil;
import com.zimug.dongbb.persistence.system.mapper.MySystemMapper;
import com.zimug.dongbb.persistence.system.mapper.SysApiMapper;
import com.zimug.dongbb.persistence.system.mapper.SysRoleApiMapper;
import com.zimug.dongbb.persistence.system.model.SysApi;
import com.zimug.dongbb.persistence.system.model.SysMenu;
import com.zimug.dongbb.persistence.system.model.SysRoleApi;
import com.zimug.dongbb.server.jwt.system.model.SysApiNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.zimug.commons.cachekey.RBACCacheKey.API_URLS;
import static com.zimug.commons.cachekey.RBACCacheKey.ROLE_CODES;

@Service
public class SysApiService {

  @Resource
  private MySystemMapper mySystemMapper;
  @Resource
  private SysApiMapper sysApiMapper;
  @Resource
  private SysRoleApiMapper sysRoleApiMapper;

  public List<SysApiNode> getApiTree(String apiNameLike,
                                         Boolean apiStatus) {
    //查找level=1的API节点，即：根节点
    SysApi rootSysApi = sysApiMapper.selectOne(
      new QueryWrapper<SysApi>().eq("level",1));

    if (rootSysApi != null) {
      Long rootApiId = rootSysApi.getId();

      List<SysApi> sysApis = mySystemMapper.selectApiTree(rootApiId, apiNameLike, apiStatus);

      List<SysApiNode> sysApiNodes = sysApis.stream().map(item -> {
        SysApiNode bean = new SysApiNode();
        BeanUtils.copyProperties(item, bean);
        return bean;
      }).collect(Collectors.toList());

      if (StringUtils.isNotEmpty(apiNameLike) || apiStatus != null) {
        return sysApiNodes;//根据api名称等查询会破坏树形结构，返回平面列表
      } else {//否则返回树型结构列表
        return DataTreeUtil.buildTree(sysApiNodes, rootApiId);
      }

    } else {
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "请先在数据库内为API配置一个分类的根节点，level=1");
    }
  }


  @Transactional
  public void addApi(SysApi sysapi){
    setApiIdsAndLevel(sysapi);

    sysapi.setIsLeaf(true); //新增的菜单节点都是子节点，没有下级
    SysApi parent = new SysApi();
    parent.setId(sysapi.getApiPid());
    parent.setIsLeaf(false);//更新父节点为非子节点。
    sysApiMapper.updateById(parent);

    sysapi.setStatus(false);//设置是否禁用，新增节点默认可用
    sysApiMapper.insert(sysapi);
  }

  //api接口权限管理-更新api接口
  @CacheEvict(value = API_URLS,allEntries = true)
  public void updateApi(SysApi sysapi){
    if(sysapi.getId() == null){
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "修改操作必须带主键");
    }else{
      sysApiMapper.updateById(sysapi);
    }
  }

  //api接口权限管理-删除api接口
  @Transactional
  @CacheEvict(value = API_URLS,allEntries = true)
  public void deleteApi(SysApi sysApi){
    //查找被删除节点的子节点
    List<SysApi> myChild = sysApiMapper.selectList(new QueryWrapper<SysApi>()
      .like("api_pids","["+ sysApi.getId() +"]"));

    if(myChild.size() > 0){
      throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
        "不能删除含有下级API接口的节点");
    }

    //查找被删除节点的父节点
    List<SysApi> myFatherChild = sysApiMapper.selectList(new QueryWrapper<SysApi>()
      .like("api_pids","["+ sysApi.getApiPid() +"]"));
    //我的父节点只有我这一个子节点，而我还要被删除，更新父节点为叶子节点。
    if(myFatherChild.size() == 1){
      SysApi parent = new SysApi();
      parent.setId(sysApi.getApiPid());
      parent.setIsLeaf(true);//更新父节点为叶子节点。
      sysApiMapper.updateById(parent);
    }
    //删除节点
    sysApiMapper.deleteById(sysApi.getId());
  }

  //设置某子节点的所有祖辈id
  private void setApiIdsAndLevel(SysApi child){
    List<SysApi> allApis = sysApiMapper.selectList(null);
    for(SysApi sysApi: allApis){
      //从组织列表中找到自己的直接父亲
      if(sysApi.getId().equals(child.getApiPid())){
        //直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
        //爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
        child.setApiPids(sysApi.getApiPids() + ",[" + child.getApiPid() + "]" );
        child.setLevel(sysApi.getLevel() + 1);
      }
    }
  }

  //获取某角色勾选的API访问权限
  public List<String> getCheckedKeys(Long roleId){
    return mySystemMapper.selectApiCheckedKeys(roleId);
  }
  //获取在API分类树中展开的项
  public List<String> getExpandedKeys(){
    return mySystemMapper.selectApiExpandedKeys();
  }

  //保存为某角色新勾选的API项
  @Transactional
  @CacheEvict(value = API_URLS,key = "#roleCode")
  public void saveCheckedKeys(String roleCode, Long roleId,List<Long> checkedIds){
    //保存之前先删除
    sysRoleApiMapper.delete(new UpdateWrapper<SysRoleApi>().eq("role_id",roleId));
    mySystemMapper.insertRoleApiIds(roleId,checkedIds);
  }


  //接口管理：更新接口的禁用状态
  @CacheEvict(value = API_URLS,allEntries = true)
  public void updateStatus(Long id,Boolean status){
    Assert.isTrue(id != null, "修改操作必须带主键");
    SysApi sysApi = new SysApi();
    sysApi.setId(id);
    sysApi.setStatus(status);
    sysApiMapper.updateById(sysApi);
  }
}
