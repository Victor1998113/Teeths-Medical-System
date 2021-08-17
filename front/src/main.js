import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import store from './store'
import global from './globalFun'
import axios from "@/axios";

import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.prototype.$http = axios;

Vue.config.productionTip = false

//require("./mock.js")

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
