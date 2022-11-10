import {getMenuTreeByUsername} from '../../api/system/sys_menu'
import router from "@/router/index"
import systemRoutes from '@/router/modules/system'

const HOME_PAGE = "/home"
const FIRST_PAGE = HOME_PAGE + "/firstpage"
const state = {
    //存放{ route: 路由路径, name: tab显示名称}对象数组
    maintabs:[{route: FIRST_PAGE, name: "首页",closable:false}],
    //当前被激活显示的那个Tab内容对应的route
    activeRoute: FIRST_PAGE,
    //当前激活的Tab的功能名称
    activeTabName:"",
    menuList:[
      /*{  //这部分已经在后端“菜单管理”中进行动态加载，所以不需要静态代码配置
          id:0,
          name:"非菜单路由,但是需要显示Tab,请定义在这里",
          hidden:true,
          children:[
            {name:"首页",path:"/home/firstpage",hidden:true},
            {name:"个人中心",path:"/home/personal",hidden:true}
          ]
      },
      {   id:1,
          name:"系统管理",
          path:"/system",
          icon:"el-icon-lock",
          children:[
              {id:3,name:"用户管理",path:"/home/sysuser"},
              {id:4,name:"角色管理",path:"/home/sysrole"},
              {id:6,name:"组织管理",path:"/home/sysorg"},
              {id:7,name:"菜单管理",path:"/home/sysmenu"},
              {id:8,name:"接口管理",path:"/home/sysapi"},
          ]
      },
      {   id:2,
          name:"订单管理",
          path:"/order",
          icon:"el-icon-eleme",
          children:[
              {id:5,name:"订单详情",path:"/home/order"},
          ]
      }*/
    ]

}
const actions = {
  addTab({state,commit},route){
    getMenuTreeByUsername().then(res => {
        state.menuList = res.data
        commit("addTabMutation",route);
    })
  }
}
const mutations = {
    //加载后台菜单数据之后调用该方法
    addTabMutation(state, route) {
        //维护主界面Tab信息（从菜单数据中获取）
        let isAlreadyIn =
            state.maintabs.some(item => item.route === route)
        this.commit("findMenuNameByRoute",route);
        state.activeRoute = route;
        if(!isAlreadyIn && state.activeTabName !== ""){
            state.maintabs.push({route:route,name:state.activeTabName});
        }

        //重点从这里开始
        let loadedRoutes = []; //动态加载的前端路由初始化
        //将菜单转换为路由
        menuToRoutes(state.menuList,loadedRoutes)
        //固定路由与动态加载的前端路由进行组合
        for(let i in systemRoutes){
          if(systemRoutes[i].path === HOME_PAGE){
            systemRoutes[i].children = loadedRoutes
          }
        }
        //最后让路由生效
        router.$addRoutes(systemRoutes)
    },
    removeTab(state, route){
        if(route !== FIRST_PAGE){
            state.maintabs = state.maintabs.filter(
                item => item.route !== route
            )
            state.activeRoute = state.maintabs[state.maintabs.length-1].route
        }
    },
    findMenuNameByRoute(state, route){
        let findOne;
        for(let i in state.menuList){
            let tmpArr = state.menuList[i].children.filter(
                item => item.path === route
            )
            if(tmpArr.length > 0) {
                findOne = tmpArr[0]
                break;
            }
        }
        state.activeTabName = findOne?findOne.name:"";
    }
}
const getters = {

}

//将菜单转换为前端路由
function menuToRoutes(menuList,loadedRoutes){
  for(let i in menuList){
    menuList[i].children.forEach(item => {
      if(item.isLeaf){  //为权限菜单中的叶子节点，添加前端路由
        loadedRoutes.push({
          path: item.url.replace(HOME_PAGE + "/",""),
          name: item.url.replace(HOME_PAGE + "/",""),
          component: resolve => {
            require(["@/views/" + item.viewImport ], resolve)
          }
        })
      }else{
        //递归处理菜单树形结构
        menuToRoutes(item.children,loadedRoutes);
      }
    })
  }
}

export default {
    state,actions,mutations,getters
}