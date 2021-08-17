import Vue from 'vue'
import Vuex from 'vuex'
import ta from "element-ui/src/locale/lang/ta";


Vue.use(Vuex)

export default {
    state: {

        menuList:[],
        permList: [],
        hasRoutes: false,

        editableTabsValue: 'Index',
        editableTabs: [{
            title: '首页',
            name: 'Index',
        }]

    },
    mutations: {

        setMenuList(state,menus){
            state.menuList = menus
        },
        setPermList(state,perms){
            state.permList = perms
        },
        changeRouteStatus(state, hasRoutes){
            state.hasRoutes = hasRoutes
        },
        addTab(state,tab) {

            let index = state.editableTabs.findIndex(e => e.name === tab.name)

            if(index === -1){
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                });
            }

            state.editableTabsValue = tab.name


        },
        restState:(state) =>{
            state.menuList = null
            state.permList = null
            state.hasRoutes = false
            state.editableTabsValue = 'Index'
            state.editableTabs = [{
                title: '首页',
                name: 'Index',
            }]
        }
    },
    actions: {

    },
    modules: {
    }
}
