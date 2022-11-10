truncate table sys_api;

truncate table sys_config;

truncate table sys_dict;

truncate table sys_menu;

truncate table sys_org;

truncate table sys_role;

truncate table sys_role_api;

truncate table sys_role_menu;

truncate table sys_user;

truncate table sys_user_role;

-- --------------------------------------------------------
-- 主机:                           192.168.161.3
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 devicedb.sys_api 结构
CREATE TABLE IF NOT EXISTS `sys_api` (
  `id` bigint(20) NOT NULL,
  `api_pid` bigint(20) NOT NULL COMMENT '接口父ID(即接口分组)',
  `api_pids` varchar(128) NOT NULL COMMENT '当前接口的所有上级id(即所有上级分组)',
  `is_leaf` tinyint(1) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `api_name` varchar(64) NOT NULL COMMENT '接口名称',
  `url` varchar(64) DEFAULT NULL COMMENT '跳转URL',
  `api_sort` int(11) DEFAULT NULL COMMENT '排序',
  `level` int(11) NOT NULL COMMENT '层级，1：接口分组，2：接口',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用，0:启用(否）,1:禁用(是)',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统Http接口表，配合sys_role_api控制接口访问权限';

-- 正在导出表  devicedb.sys_api 的数据：~55 rows (大约)
/*!40000 ALTER TABLE `sys_api` DISABLE KEYS */;
INSERT INTO `sys_api` (`id`, `api_pid`, `api_pids`, `is_leaf`, `api_name`, `url`, `api_sort`, `level`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1, 0, '[0]', 0, '系统数据接口', NULL, 1, 1, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(2, 1, '[0],[1]', 0, '系统管理模块', NULL, 1, 2, 0, '', '2021-01-16 01:50:45', 'admin', '2021-02-10 07:18:33'),
	(3, 2, '[0],[1],[2]', 1, '用户信息接口', '/sysuser/info', 1, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(4, 2, '[0],[1],[2]', 1, '组织管理-树形数据接口', '/sysorg/tree', 2, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(5, 2, '[0],[1],[2]', 1, '组织管理-新增组织接口', '/sysorg/add', 3, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(6, 2, '[0],[1],[2]', 1, '组织管理-修改组织接口', '/sysorg/update', 4, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(7, 2, '[0],[1],[2]', 1, '组织管理-删除组织接口', '/sysorg/delete', 5, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(8, 2, '[0],[1],[2]', 1, '菜单树形数据加载接口', '/sysmenu/tree', 6, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(9, 2, '[0],[1],[2]', 1, '菜单管理-新增菜单项接口', '/sysmenu/add', 7, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(10, 2, '[0],[1],[2]', 1, '菜单管理-修改菜单项接口', '/sysmenu/update', 8, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(11, 2, '[0],[1],[2]', 1, '菜单管理-删除菜单项接口', '/sysmenu/delete', 9, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(12, 2, '[0],[1],[2]', 1, '查询某角色已具备菜单权限接口', '/sysmenu/checkedtree', 10, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(13, 2, '[0],[1],[2]', 1, '保存某角色分配勾选的菜单权限', '/sysmenu/savekeys', 11, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(14, 2, '[0],[1],[2]', 1, '接口分类树形结构数据加载', '/sysapi/tree', 12, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(15, 2, '[0],[1],[2]', 1, '接口管理-新增接口', '/sysapi/add', 13, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(16, 2, '[0],[1],[2]', 1, '接口管理-更新接口数据', '/sysapi/update', 14, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(17, 2, '[0],[1],[2]', 1, '接口管理-删除接口', '/sysapi/delete', 15, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(18, 2, '[0],[1],[2]', 1, '查询某角色已具备的接口访问权限', '/sysapi/checkedtree', 16, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(19, 2, '[0],[1],[2]', 1, '保存某角色勾选分配的接口访问权限', '/sysapi/savekeys', 17, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(20, 2, '[0],[1],[2]', 1, '角色管理-列表查询', '/sysrole/query', 18, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(21, 2, '[0],[1],[2]', 1, '角色管理-新增角色', '/sysrole/add', 19, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(22, 2, '[0],[1],[2]', 1, '角色管理-更新角色数据', '/sysrole/update', 20, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(23, 2, '[0],[1],[2]', 1, '角色管理-删除角色', '/sysrole/delete', 21, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(24, 2, '[0],[1],[2]', 1, '查询某用户具备的角色id列表', '/sysrole/checkedroles', 22, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(25, 2, '[0],[1],[2]', 1, '保存为某用户分配的角色', '/sysrole/savekeys', 23, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(26, 2, '[0],[1],[2]', 1, '用户管理-用户列表查询', '/sysuser/query', 24, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(27, 2, '[0],[1],[2]', 1, '用户管理-新增用户', '/sysuser/add', 25, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(28, 2, '[0],[1],[2]', 1, '用户管理-修改用户信息', '/sysuser/update', 26, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(29, 2, '[0],[1],[2]', 1, '用户管理-删除用户', '/sysuser/delete', 27, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(30, 2, '[0],[1],[2]', 1, '为用户重置密码', '/sysuser/pwd/reset', 28, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(31, 2, '[0],[1],[2]', 1, '判断用户是否使用默认密码', '/sysuser/pwd/isdefault', 29, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(32, 2, '[0],[1],[2]', 1, '修改用户密码', '/sysuser/pwd/change', 30, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(33, 2, '[0],[1],[2]', 1, '菜单栏数据接口(根据登录用户)', '/sysmenu/tree/user', 6, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(34, 2, '[0],[1],[2]', 1, '获取系统全局配置参数', '/sysconfig/all', 31, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(35, 2, '[0],[1],[2]', 1, '条件查询全局配置参数接口', '/sysconfig/query', 32, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(36, 2, '[0],[1],[2]', 1, '新增配置参数接口', '/sysconfig/add', 33, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(37, 2, '[0],[1],[2]', 1, '修改配置参数接口', '/sysconfig/update', 34, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(38, 2, '[0],[1],[2]', 1, '删除配置参数接口', '/sysconfig/delete', 35, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(39, 2, '[0],[1],[2]', 1, '配置参数从数据库刷新到内存', '/sysconfig/refresh', 36, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(40, 2, '[0],[1],[2]', 1, '数据字典数据加载接口', '/sysdict/all', 37, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(41, 2, '[0],[1],[2]', 1, '数据字典条件查询接口', '/sysdict/query', 38, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(42, 2, '[0],[1],[2]', 1, '数据字典数据新增接口', '/sysdict/add', 39, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(43, 2, '[0],[1],[2]', 1, '数据字典数据修改接口', '/sysdict/update', 40, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(44, 2, '[0],[1],[2]', 1, '数据字典数据删除接口', '/sysdict/delete', 41, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1305351823497891842, 1, '[0],[1]', 0, '测试管理模块', 'test', 2, 2, 0, '', '2021-01-16 01:50:45', 'admin', '2021-01-24 16:09:16'),
	(1305352353884409857, 1305351823497891842, '[0],[1],[1305351823497891842]', 1, '根据条件查询货物', '/afficegoods/query', 2, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1305352533799079937, 1305351823497891842, '[0],[1],[1305351823497891842]', 1, '修改货物', '/afficegoods/update', 2, 3, 0, '', '2021-01-16 01:50:45', 'admin', '2021-01-25 09:16:44'),
	(1305352644314796033, 1305351823497891842, '[0],[1],[1305351823497891842]', 1, '删除货物', '/afficegoods/delete', 1, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1313305456969621506, 2, '[0],[1],[2]', 1, '用户管理-用户状态更新', '/sysuser/enabled/change', 28, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1313751534453927938, 2, '[0],[1],[2]', 1, '接口管理-更新禁用状态', '/sysapi/status/change', 16, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1313752151180193793, 2, '[0],[1],[2]', 1, '角色管理-更新禁用状态', '/sysrole/status/change', 22, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1313752655025156098, 2, '[0],[1],[2]', 1, '组织管理-更新组织机构禁用状态', '/sysorg/status/change', 5, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1313753498440970241, 2, '[0],[1],[2]', 1, '菜单管理-更新禁用状态', '/sysmenu/status/change', 10, 3, 0, '', '2021-01-16 01:50:45', '', '2021-01-16 01:50:45'),
	(1353253517831827458, 1305351823497891842, '[0],[1],[1305351823497891842]', 1, '新增货物', '/afficegoods/add', 5, 3, 0, 'admin', '2021-01-24 16:09:16', 'admin', '2021-01-31 09:22:03'),
	(1359280554647330818, 2, '[0],[1],[2]', 1, '菜单是否隐藏切换', '/sysmenu/hidden/change', 9, 3, 0, 'admin', '2021-02-10 07:18:33', 'admin', '2021-02-10 07:18:33');
/*!40000 ALTER TABLE `sys_api` ENABLE KEYS */;


-- 导出  表 devicedb.sys_config 结构
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` bigint(20) NOT NULL,
  `param_name` varchar(32) NOT NULL COMMENT '参数名称(中文)',
  `param_key` varchar(64) NOT NULL COMMENT '参数编码唯一标识(英文及数字)',
  `param_value` varchar(64) NOT NULL COMMENT '参数值',
  `param_desc` varchar(64) DEFAULT NULL COMMENT '参数描述备注',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统全局配置参数';

-- 正在导出表  devicedb.sys_config 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` (`id`, `param_name`, `param_key`, `param_value`, `param_desc`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1, '用户初始密码', 'user.init.password', 'abcd1234', '系统新增用户初始化密码（登录后会提示用户自行修改）', '', '2020-02-29 13:26:58', '', '2021-01-16 01:52:42');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;


-- 导出  表 devicedb.sys_dict 结构
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(64) NOT NULL COMMENT '分组名称',
  `group_code` varchar(64) NOT NULL COMMENT '分组编码',
  `item_name` varchar(16) NOT NULL COMMENT '字典项名称',
  `item_value` varchar(16) NOT NULL COMMENT '字典项Value',
  `item_desc` varchar(64) DEFAULT NULL COMMENT '字典项描述',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- 正在导出表  devicedb.sys_dict 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `group_name`, `group_code`, `item_name`, `item_value`, `item_desc`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1, '是否禁用', 'common.status', '未禁用', 'false', '通用数据记录的禁用状态', '', '2021-01-16 01:58:25', '', '2021-01-16 01:58:25'),
	(2, '是否禁用', 'common.status', '已禁用', 'true', '通用数据记录的禁用状态', '', '2021-01-16 01:58:25', '', '2021-01-16 01:58:25'),
	(3, '用户状态', 'sysuser.enabled', '已激活', 'true', '用户状态', '', '2021-01-16 01:58:25', '', '2021-01-16 01:58:25'),
	(4, '用户状态', 'sysuser.enabled', '已禁用', 'false', '用户状态', '', '2021-01-16 01:58:25', '', '2021-01-16 01:58:25');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;


-- 导出  表 devicedb.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` bigint(20) NOT NULL,
  `menu_pid` bigint(20) NOT NULL COMMENT '父菜单ID',
  `menu_pids` varchar(128) NOT NULL COMMENT '当前菜单所有父菜单',
  `menu_name` varchar(16) NOT NULL COMMENT '菜单名称',
  `menu_sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_leaf` tinyint(1) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `url` varchar(64) DEFAULT NULL COMMENT '跳转URL',
  `icon` varchar(45) DEFAULT NULL,
  `level` int(11) NOT NULL COMMENT '菜单层级',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用，0:启用(否）,1:禁用(是)',
  `hidden` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏，0:不隐藏，1:隐藏，某些页面入口不在菜单上显示',
  `view_import` varchar(64) DEFAULT NULL COMMENT '前端路由组件页面文件import路径',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- 正在导出表  devicedb.sys_menu 的数据：~16 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `menu_pid`, `menu_pids`, `menu_name`, `menu_sort`, `is_leaf`, `url`, `icon`, `level`, `status`, `hidden`, `view_import`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1, 0, '[0]', '系统根目录', 1, 0, '', '', 1, 0, 0, '', '', '2021-01-16 02:02:59', 'admin', '2021-02-14 09:30:34'),
	(2, 1, '[0],[1]', '系统管理', 2, 0, '', 'el-icon-fa fa-cogs', 2, 0, 0, '', '', '2021-01-16 02:02:59', 'admin', '2021-02-14 09:30:47'),
	(3, 2, '[0],[1],[2]', '用户管理', 1, 1, '/home/sysuser', 'el-icon-fa fa-user', 3, 0, 0, 'system/SystemUser.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 12:57:40'),
	(4, 2, '[0],[1],[2]', '角色管理', 2, 1, '/home/sysrole', 'el-icon-fa fa-users', 3, 0, 0, 'system/SystemRole.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:41'),
	(5, 2, '[0],[1],[2]', '组织管理', 3, 1, '/home/sysorg', 'el-icon-fa fa-sitemap', 3, 0, 0, 'system/SystemOrg.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:44'),
	(6, 2, '[0],[1],[2]', '菜单管理', 4, 1, '/home/sysmenu', 'el-icon-fa fa-list-ul', 3, 0, 0, 'system/SystemMenu.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:47'),
	(7, 2, '[0],[1],[2]', '接口管理', 5, 1, '/home/sysapi', 'el-icon-fa fa-plug', 3, 0, 0, 'system/SystemApi.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:50'),
	(10, 1, '[0],[1]', '文档工具', 3, 0, '', 'el-icon-eleme', 2, 0, 0, '', '', '2021-01-16 02:02:59', 'admin', '2021-02-28 10:36:46'),
	(11, 10, '[0],[1],[10]', '数据库文档', 1, 1, '/home/dbdocument', 'el-icon-lock', 3, 0, 0, 'tools/DBDocument.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:53'),
	(12, 2, '[0],[1],[2]', '参数配置', 6, 1, '/home/sysconfig', 'el-icon-fa fa-cog', 3, 0, 0, 'system/SystemConfig.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:34:57'),
	(13, 2, '[0],[1],[2]', '数据字典', 7, 1, '/home/sysdict', 'el-icon-fa fa-list-ol', 3, 0, 0, 'system/SystemDict.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:35:01'),
	(1301717053454974978, 10, '[0],[1],[10]', '代码生成器', 2, 1, '/home/generator', 'el-icon-fa fa-list-ul', 3, 0, 0, 'tools/MyGenerator.vue', '', '2021-01-16 02:02:59', 'admin', '2021-02-12 10:35:06'),
	(1360763330525454338, 1, '[0],[1]', '非菜单路由页面(请隐藏)', 1, 0, '', 'el-icon-warning', 2, 0, 1, '', 'admin', '2021-02-14 09:30:34', 'admin', '2021-02-14 13:57:04'),
	(1360764106014515202, 1360763330525454338, '[0],[1],[1360763330525454338]', '首页', 1, 1, '/home/firstpage', 'el-icon-s-home', 3, 0, 1, 'system/FirstPage.vue', 'admin', '2021-02-14 09:33:39', 'admin', '2021-02-12 12:57:34'),
	(1360764611331678210, 1360763330525454338, '[0],[1],[1360763330525454338]', '个人中心', 1, 1, '/home/personal', 'el-icon-s-custom', 3, 0, 1, 'system/PersonalCenter.vue', 'admin', '2021-02-14 09:35:40', 'admin', '2021-02-12 10:35:18'),
	(1365853419727286273, 10, '[0],[1],[10]', 'Swagger接口', 3, 1, '/home/apidocument', 'el-icon-grape', 3, 0, 0, 'tools/APIDocument.vue', 'admin', '2021-02-28 10:36:46', 'admin', '2021-02-28 10:45:57');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 devicedb.sys_org 结构
CREATE TABLE IF NOT EXISTS `sys_org` (
  `id` bigint(20) NOT NULL,
  `org_pid` bigint(20) NOT NULL COMMENT '上级组织编码',
  `org_pids` varchar(128) NOT NULL COMMENT '所有的父节点id',
  `org_name` varchar(32) NOT NULL COMMENT '组织名',
  `org_sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_leaf` tinyint(1) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `email` varchar(32) DEFAULT NULL COMMENT '邮件',
  `level` int(11) NOT NULL COMMENT '组织层级',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用，0:启用(否）,1:禁用(是)',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统组织结构表';

-- 正在导出表  devicedb.sys_org 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` (`id`, `org_pid`, `org_pids`, `org_name`, `org_sort`, `is_leaf`, `address`, `phone`, `email`, `level`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1, 0, '[0]', 'DongTech', 1, 0, NULL, NULL, NULL, 1, 0, '', '2021-01-16 02:05:41', '', '2021-01-16 02:05:41'),
	(1298067029349117953, 1, '[0],[1]', '西安分公司', 1, 0, NULL, '13215678932', '11111111@qq.com', 2, 0, '', '2021-01-24 15:56:09', 'admin', '2021-01-24 15:56:09'),
	(1298067159791972353, 1298067029349117953, '[0],[1],[1298067029349117953]', '测试部一', 1, 1, NULL, '', '444444444@qq.com', 3, 0, '', '2021-01-24 15:55:07', 'admin', '2021-01-24 15:55:02'),
	(1298067674592456706, 1, '[0],[1]', '上海分公司', 2, 0, NULL, '', '11111111@qq.com', 2, 0, '', '2021-01-16 02:05:41', '', '2021-01-16 02:05:41'),
	(1298067729978241025, 1298067674592456706, '[0],[1],[1298067674592456706]', '运维部一', 1, 1, NULL, '', '11111111@qq.com', 3, 0, '', '2021-01-16 02:05:41', '', '2021-01-16 02:05:41'),
	(1298067787712835585, 1298067674592456706, '[0],[1],[1298067674592456706]', '运维部二', 2, 1, NULL, '', '11111111@qq.com', 3, 0, '', '2021-01-16 02:05:41', '', '2021-01-16 02:05:41'),
	(1298067843731959809, 1298067674592456706, '[0],[1],[1298067674592456706]', '运维部三', 3, 1, NULL, '', '11111111@qq.com', 3, 0, '', '2021-01-16 02:05:41', '', '2021-01-16 02:05:41'),
	(1298068119314509825, 1298067029349117953, '[0],[1],[1298067029349117953]', '研发部一', 2, 1, NULL, '', '222222222@qq.com', 3, 0, '', '2021-01-24 15:46:03', 'admin', '2021-01-24 01:45:53'),
	(1353250220777488385, 1298067029349117953, '[0],[1],[1298067029349117953]', '研发部二', 3, 1, NULL, '13678901234', '777777777@qq.com', 3, 0, 'admin', '2021-01-24 16:04:41', 'admin', '2021-01-25 09:15:20');
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;


-- 导出  表 devicedb.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色名称(汉字)',
  `role_desc` varchar(128) NOT NULL DEFAULT '0' COMMENT '角色描述',
  `role_code` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色的英文code.如：ADMIN',
  `role_sort` int(11) NOT NULL DEFAULT '0' COMMENT '角色顺序',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用，0:启用(否）,1:禁用(是)',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- 正在导出表  devicedb.sys_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_name`, `role_desc`, `role_code`, `role_sort`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1298061556168273921, '管理员', '系统管理员', 'admin', 1, 0, '', '2021-01-10 20:51:09', '', '2021-01-10 20:51:09'),
	(1298063367197437954, '普通用户', '普通用户', 'common', 2, 0, '', '2021-01-10 20:51:09', '', '2021-01-10 20:51:09'),
	(1312962529638510594, '测试2', '测试2', 'ceshi2', 4, 0, '', '2021-01-16 04:53:20', 'admin', '2021-01-31 07:45:54'),
	(1353244952249323521, '测试1', '测试1', 'ceshi1', 3, 0, 'admin', '2021-01-24 01:35:13', 'admin', '2021-01-24 01:35:13');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 devicedb.sys_role_api 结构
CREATE TABLE IF NOT EXISTS `sys_role_api` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `api_id` bigint(20) NOT NULL COMMENT '接口id',
  PRIMARY KEY (`role_id`,`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色接口权限关系表';

-- 正在导出表  devicedb.sys_role_api 的数据：~159 rows (大约)
/*!40000 ALTER TABLE `sys_role_api` DISABLE KEYS */;
INSERT INTO `sys_role_api` (`role_id`, `api_id`) VALUES
	(1298061556168273921, 1),
	(1298061556168273921, 2),
	(1298061556168273921, 3),
	(1298061556168273921, 4),
	(1298061556168273921, 5),
	(1298061556168273921, 6),
	(1298061556168273921, 7),
	(1298061556168273921, 8),
	(1298061556168273921, 9),
	(1298061556168273921, 10),
	(1298061556168273921, 11),
	(1298061556168273921, 12),
	(1298061556168273921, 13),
	(1298061556168273921, 14),
	(1298061556168273921, 15),
	(1298061556168273921, 16),
	(1298061556168273921, 17),
	(1298061556168273921, 18),
	(1298061556168273921, 19),
	(1298061556168273921, 20),
	(1298061556168273921, 21),
	(1298061556168273921, 22),
	(1298061556168273921, 23),
	(1298061556168273921, 24),
	(1298061556168273921, 25),
	(1298061556168273921, 26),
	(1298061556168273921, 27),
	(1298061556168273921, 28),
	(1298061556168273921, 29),
	(1298061556168273921, 30),
	(1298061556168273921, 31),
	(1298061556168273921, 32),
	(1298061556168273921, 33),
	(1298061556168273921, 34),
	(1298061556168273921, 35),
	(1298061556168273921, 36),
	(1298061556168273921, 37),
	(1298061556168273921, 38),
	(1298061556168273921, 39),
	(1298061556168273921, 40),
	(1298061556168273921, 41),
	(1298061556168273921, 42),
	(1298061556168273921, 43),
	(1298061556168273921, 44),
	(1298061556168273921, 1305351823497891842),
	(1298061556168273921, 1305352353884409857),
	(1298061556168273921, 1305352533799079937),
	(1298061556168273921, 1305352644314796033),
	(1298061556168273921, 1313305456969621506),
	(1298061556168273921, 1313751534453927938),
	(1298061556168273921, 1313752151180193793),
	(1298061556168273921, 1313752655025156098),
	(1298061556168273921, 1313753498440970241),
	(1298061556168273921, 1353253517831827458),
	(1298061556168273921, 1359280554647330818),
	(1298063367197437954, 1),
	(1298063367197437954, 2),
	(1298063367197437954, 3),
	(1298063367197437954, 4),
	(1298063367197437954, 5),
	(1298063367197437954, 6),
	(1298063367197437954, 7),
	(1298063367197437954, 8),
	(1298063367197437954, 9),
	(1298063367197437954, 10),
	(1298063367197437954, 11),
	(1298063367197437954, 12),
	(1298063367197437954, 13),
	(1298063367197437954, 14),
	(1298063367197437954, 15),
	(1298063367197437954, 16),
	(1298063367197437954, 17),
	(1298063367197437954, 18),
	(1298063367197437954, 19),
	(1298063367197437954, 20),
	(1298063367197437954, 21),
	(1298063367197437954, 22),
	(1298063367197437954, 23),
	(1298063367197437954, 24),
	(1298063367197437954, 25),
	(1298063367197437954, 26),
	(1298063367197437954, 27),
	(1298063367197437954, 28),
	(1298063367197437954, 29),
	(1298063367197437954, 30),
	(1298063367197437954, 31),
	(1298063367197437954, 32),
	(1298063367197437954, 33),
	(1298063367197437954, 34),
	(1298063367197437954, 35),
	(1298063367197437954, 36),
	(1298063367197437954, 37),
	(1298063367197437954, 38),
	(1298063367197437954, 39),
	(1298063367197437954, 40),
	(1298063367197437954, 41),
	(1298063367197437954, 42),
	(1298063367197437954, 43),
	(1298063367197437954, 44),
	(1312962529638510594, 1),
	(1312962529638510594, 2),
	(1312962529638510594, 3),
	(1312962529638510594, 4),
	(1312962529638510594, 5),
	(1312962529638510594, 6),
	(1312962529638510594, 8),
	(1312962529638510594, 26),
	(1312962529638510594, 27),
	(1312962529638510594, 28),
	(1312962529638510594, 29),
	(1312962529638510594, 30),
	(1312962529638510594, 31),
	(1312962529638510594, 32),
	(1312962529638510594, 33),
	(1312962529638510594, 34),
	(1312962529638510594, 35),
	(1312962529638510594, 39),
	(1312962529638510594, 40),
	(1312962529638510594, 41),
	(1312962529638510594, 1305351823497891842),
	(1312962529638510594, 1305352353884409857),
	(1312962529638510594, 1305352533799079937),
	(1312962529638510594, 1305352644314796033),
	(1312962529638510594, 1313305456969621506),
	(1353244952249323521, 1),
	(1353244952249323521, 2),
	(1353244952249323521, 3),
	(1353244952249323521, 4),
	(1353244952249323521, 8),
	(1353244952249323521, 12),
	(1353244952249323521, 13),
	(1353244952249323521, 14),
	(1353244952249323521, 15),
	(1353244952249323521, 16),
	(1353244952249323521, 17),
	(1353244952249323521, 18),
	(1353244952249323521, 19),
	(1353244952249323521, 20),
	(1353244952249323521, 21),
	(1353244952249323521, 22),
	(1353244952249323521, 23),
	(1353244952249323521, 24),
	(1353244952249323521, 25),
	(1353244952249323521, 26),
	(1353244952249323521, 27),
	(1353244952249323521, 28),
	(1353244952249323521, 29),
	(1353244952249323521, 30),
	(1353244952249323521, 31),
	(1353244952249323521, 32),
	(1353244952249323521, 33),
	(1353244952249323521, 34),
	(1353244952249323521, 35),
	(1353244952249323521, 39),
	(1353244952249323521, 40),
	(1353244952249323521, 41),
	(1353244952249323521, 1313305456969621506),
	(1353244952249323521, 1313751534453927938),
	(1353244952249323521, 1313752151180193793);
/*!40000 ALTER TABLE `sys_role_api` ENABLE KEYS */;


-- 导出  表 devicedb.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单权限关系表';

-- 正在导出表  devicedb.sys_role_menu 的数据：~49 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
	(1298061556168273921, 1),
	(1298061556168273921, 2),
	(1298061556168273921, 3),
	(1298061556168273921, 4),
	(1298061556168273921, 5),
	(1298061556168273921, 6),
	(1298061556168273921, 7),
	(1298061556168273921, 10),
	(1298061556168273921, 11),
	(1298061556168273921, 12),
	(1298061556168273921, 13),
	(1298061556168273921, 1301717053454974978),
	(1298061556168273921, 1360763330525454338),
	(1298061556168273921, 1360764106014515202),
	(1298061556168273921, 1360764611331678210),
	(1298061556168273921, 1365853419727286273),
	(1298063367197437954, 1),
	(1298063367197437954, 2),
	(1298063367197437954, 3),
	(1298063367197437954, 5),
	(1298063367197437954, 6),
	(1298063367197437954, 7),
	(1298063367197437954, 10),
	(1298063367197437954, 11),
	(1298063367197437954, 12),
	(1298063367197437954, 13),
	(1298063367197437954, 1360763330525454338),
	(1298063367197437954, 1360764106014515202),
	(1298063367197437954, 1360764611331678210),
	(1312962529638510594, 1),
	(1312962529638510594, 2),
	(1312962529638510594, 3),
	(1312962529638510594, 4),
	(1312962529638510594, 5),
	(1312962529638510594, 1360763330525454338),
	(1312962529638510594, 1360764106014515202),
	(1312962529638510594, 1360764611331678210),
	(1353244952249323521, 1),
	(1353244952249323521, 2),
	(1353244952249323521, 3),
	(1353244952249323521, 4),
	(1353244952249323521, 5),
	(1353244952249323521, 6),
	(1353244952249323521, 7),
	(1353244952249323521, 12),
	(1353244952249323521, 13),
	(1353244952249323521, 1360763330525454338),
	(1353244952249323521, 1360764106014515202),
	(1353244952249323521, 1360764611331678210);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;


-- 导出  表 devicedb.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '0' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT '0' COMMENT '密码',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0禁用用户，1是激活用户',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT 'email',
  `create_by` varchar(64) NOT NULL COMMENT '本条记录创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本条记录创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '本条记录修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '本条记录的修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 正在导出表  devicedb.sys_user 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `password`, `org_id`, `enabled`, `phone`, `email`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
	(1297873308628307970, 'admin', '$2a$10$/yOSnA2NwMh4UwXK5K8gnOUI37y.Jl.eAwnXrfML6SXoTRwWThvN6', 1, 1, '13214456784', 'hahaha1@163.com', '', '2020-10-06 10:32:22', 'admin', '2021-01-31 06:35:52'),
	(1298090120930418690, 'yanfa1', '$2a$10$.Bj/zLMXc5RXzHadk2mbguHdHWdhSUSleDRhcf./SdxhlJyuYbvOe', 1, 1, '13245678901', '', '', '2020-08-24 21:49:12', '', '2021-01-10 20:29:35'),
	(1312546457345118209, 'nav', '$2a$10$dqFkbqOddDBGe28NqTb.NOkAOt64NJMEHxCZpxyn.LzNtoIu9S5P2', 1298067674592456706, 1, '13214456789', '', '', '2020-10-03 19:13:56', '', '2021-01-10 20:29:35'),
	(1353244456323207169, 'ccaa1', '$2a$10$A0OIoLpe6y5j76b1HdhZLONXZAc4uFjatP0EJvYuu2dvFTin8vrCu', 1298067029349117953, 1, '13215556666', '', 'admin', '2021-01-24 01:33:15', 'admin', '2021-01-27 08:24:08');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


-- 导出  表 devicedb.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色自增id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户自增id',
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- 正在导出表  devicedb.sys_user_role 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`role_id`, `user_id`) VALUES
	(1298061556168273921, 1297873308628307970),
	(1298063367197437954, 2),
	(1298063367197437954, 1298088185716301826),
	(1298063367197437954, 1298090120930418690),
	(1298063367197437954, 1312704409347657730),
	(1312962529638510594, 1),
	(1312962529638510594, 1312546457345118209),
	(1312962529638510594, 1313053560677601281),
	(1312962529638510594, 1354223802659696641),
	(1353244952249323521, 1353244456323207169);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
