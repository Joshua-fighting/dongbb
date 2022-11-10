import Vue from 'vue'
import VueRouter from 'vue-router'
import systemRoutes from './modules/system'
import { refreshToken } from '@/api/system/sys_user'
import {setJwtToken} from "@/lib/utils";
import store from '@/store/index'

//导入NProgress进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'


Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [...systemRoutes]
})

//从后台动态加载前端路由的时候使用
router.$addRoutes = function (params){
  //router使用matcher来匹配前端路由，如果使用新建的matcher，就相当于清空了之前添加的路由
  router.matcher = new VueRouter().matcher;
  //重新添加路由
  router.addRoutes(params)
}

router.beforeEach((to,from,next) => {
  if(to.name !== 'login'){
    NProgress.start()
    refreshToken().then(res => {
      //没有获得新的token==null，
      // 表示旧的token已经失效，需要重新登录
      if(res.data == null){
        next({name: 'login'}) //去登录界面
        setJwtToken('') //清空token
      }else{//否则去你想去的界面，并把新的token保存起来
        //把全局配置加载完成再去你想去的页面
        store.dispatch('loadSysConfig').then(_ => {
          next()
        })
        setJwtToken(res.data)
      }
    })
  }else{//每次去到登录页面都刷新一下，清除token
    next()
    setJwtToken('') //清空token
  }
})
router.afterEach((to,from) => {
  if(to.name !== 'login'){
    store.dispatch('addTab',to.path)
    NProgress.done()
  }
})

export default router
