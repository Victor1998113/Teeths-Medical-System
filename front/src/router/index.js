import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "@/components/Home";
import Index from "@/views/sys/Index";

import '../assets/css/glogin.css'
import axios from "../axios";
import store from "../store"
import menus from "@/store/modules/menus";

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
//push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
    if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
    return originalReplace.call(this, location).catch(err => err)
}

const routes = [

    {
        path: '/home',
        name: 'Home',
        component: Home,
        redirect: "/index",
        children: [
            {//首页
                path: '/index',
                name:'Index',
                component: Index
            },

        ],
        cache:true
    },
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: () => import('@/components/Login')
    }

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})


router.beforeEach((to, from, next) =>{


    let hasRoute = store.state.menus.hasRoutes

    let token = localStorage.getItem("token")

    if(to.path=='/login'){
        next()
    }else if(!token){
        next({path:'/login'})
    }else if(token &&!hasRoute){
        axios.get("/sys/menu/nav",{

            headers:{
                Authorization:localStorage.getItem("token")
            }

        }).then(res => {

            console.log("刚刚加载时的数据====")
            console.log(res.data.data.nav);

            //拿到列表
            store.commit("setMenuList",res.data.data.nav)
            //拿到用户权限
            store.commit("setPermList",res.data.data.authoritys)


            // 动态绑定路由
            let newRoutes = router.options.routes

            res.data.data.nav.forEach(menu => {
                if (menu.children) {
                    menu.children.forEach(e => {

                        // 转成路由
                        let route = menuToRoute(e)

                        // 吧路由添加到路由管理中
                        if (route) {
                            newRoutes[0].children.push(route)
                        }

                    })
                }
            })

            console.log(newRoutes)
            router.addRoutes(newRoutes)
            hasRoute = true
            store.commit("changeRouteStatus",hasRoute)
        })
    }


    next()
})

// 导航转成路由
const menuToRoute = (menu) => {

    if (!menu.component) {
        return null
    }

    let route = {
        name: menu.name,
        path: menu.path,
        meta: {
            icon: menu.icon,
            title: menu.title
        }
    }
    route.component = () => import('@/views/' + menu.component)

    return route
}

export default router
