package com.zimug.commons.cachekey;

/**
 * @创建人 字母哥
 * @创建时间 2021/1/27
 * @描述  将不同模块使用到的缓存名称进行统一的定义，并在这里明确写出缓存的设计规则
 **/
public class RBACCacheKey {

  // 用户信息缓存(用于JWT用户登录验证及接口权限验证)，缓存策略：key=用户名
  // 用户信息被修改、删除、重置修改密码的时候，清除该缓存。 缓存清除策略：key=用户名
  public static final String USER_DETAIL = "user_detail";


  // 某个用户角色列表信息缓存，缓存策略：key=用户名
  // 角色被修改或删除或角色禁用状态被更新的时候，清除所有用户的角色列表缓存信息
  // 某个用户被重新分配角色的时候，清除该用户的角色列表缓存信息,，缓存清除策略：key=用户名
  public static final String ROLE_CODES = "role_codes";


  // 某个角色可以访问的api接口列表的缓存，缓存策略：key=roleCode
  // API接口被修改或删除或API接口禁用状态被更新的时候，清除所有角色对应的API接口缓存数据
  // 某个角色被重新分配API接口权限的时候，清除该角色对应的API接口权限缓存信息,缓存清除策略：key=roleCode
  public static final String API_URLS = "api_urls";

}
