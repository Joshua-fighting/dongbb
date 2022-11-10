import Login from '@/views/Login.vue'

//菜单项级别的前端路由，这部分数据从后台加载菜单动态获取，所以注释掉
/*const menuRouter = [
    {path:"",redirect: 'firstpage' },
    {
        name: 'firstpage',
        path: 'firstpage',
        component: FirstPage
    },
    {
        name: 'sysuser',
        path: 'sysuser',
        component: () => import('@/views/system/SystemUser.vue')
    },
    {
        name: 'sysrole',
        path: 'sysrole',
        component: () => import('@/views/system/SystemRole.vue')
    },
    {
        name: 'sysorg',
        path: 'sysorg',
        component: () => import('@/views/system/SystemOrg.vue')
    },
    {
      name: 'sysmenu',
      path: 'sysmenu',
      component: () => import('@/views/system/SystemMenu.vue')
    },
    {
      name: 'sysapi',
      path: 'sysapi',
      component: () => import('@/views/system/SystemApi.vue')
    },
    {
      name: 'sysconfig',
      path: 'sysconfig',
      component: () => import('@/views/system/SystemConfig.vue')
    },
    {
      name: 'sysdict',
      path: 'sysdict',
      component: () => import('@/views/system/SystemDict.vue')
    },
    {
        name: 'personal',
        path: 'personal',
        component: () => import('@/views/system/PersonalCenter.vue')
    },
];*/

//大部分的路由(实际上是菜单)，从后台数据库中动态获取
// 这里只定义一些固定菜单
export default [
  {
      path: '/',
      name: 'login',
      component: Login
  },
  {
    path: '/home',
    //name: 'home',
    component: () => import( '@/views/Home.vue'),
    children:[
      {path:"",redirect: 'firstpage' },
    ]
  }
]