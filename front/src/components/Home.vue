<template>
  <el-container>

    <!--头部区域-->
    <el-header>
      <div>
        <img src="../assets/img/loginImg/logo.jpg" style="border-radius: 50%;">
        <router-link to="/index" class="router-link-active">&nbsp;&nbsp;爱丽丝医疗口腔管理系统</router-link>
      </div>
<!--      <el-button  @click="loginOut" type="primary" plain>退出登录</el-button>-->

      <el-dropdown>
        <span class="el-dropdown-link" >
          {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <!--页面主体区域-->
    <el-container>

      <!--侧边栏-->
      <el-aside :width="isCollapsed ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">
          <i class="el-icon-arrow-left"></i> <i class="el-icon-arrow-right"></i>
        </div>

        <!--侧边栏菜单-->
        <el-menu
            background-color="#333744"
            text-color="white"
            active-text-color="#C4C4C4"

            :collapse="isCollapsed"
            :collapse-transition="false"

            router>

          <!--一级菜单-->
          <el-submenu :index="item.name" v-for="item in menuList">


            <!--一级菜单模板区域-->
            <template slot="title">
              <i :class="item.icon">{{item.statu}}</i>
              <span>{{ item.title }}</span>

            </template>

            <!--二级菜单-->
            <router-link class="router-link-active"  @click.native='jump'  :to="it.path" v-for="it in item.children">
            <el-menu-item :index="it.name" @click="selectMenu(it)">
              <template slot="title">
                <i :class="it.icon"></i>
                <span>{{ it.title }}</span>
              </template>
            </el-menu-item>
            </router-link>

          </el-submenu>

        </el-menu>
      </el-aside>


      <!--右侧内容主体-->
      <el-main>

        <!--标签页-->
        <Tabs></Tabs>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Tabs from "@/components/Tabs";

export default {
  name: "Home",
  created() {

    this.getUserInfo();

    var isCollapsed = window.sessionStorage.getItem("isCollapsed");

    if(isCollapsed=='true'){
      this.isCollapsed = true
    }else{
      this.isCollapsed = false
    }

  },
  data() {

    return {
      //是否折叠
      isCollapsed:false,

      username:'',


    }
  },

  computed: {

    menuList: {
        get(){
          return this.$store.state.menus.menuList
        }
    }

  },

  components:{
    Tabs
  },

  methods: {
    //退出登录
    loginOut() {
      this.$http.post('/logout').then(res => {

        localStorage.clear()
        sessionStorage.clear()
        this.$store.commit("restState")
        this.$router.push("/login")

      })


    },

    jump(){
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 350);
    },
    selectMenu(item){
      this.$store.commit("addTab",item)
    },

    //点击按钮，切换菜单的折叠与展开
    toggleCollapse(){
      this.isCollapsed = !this.isCollapsed

      window.sessionStorage.setItem("isCollapsed",this.isCollapsed);
    },

    //获取用户信息
    getUserInfo(){
      this.$http.get("/sys/userInfo").then(res => {
          this.username = res.data.data.username
      })
    }

  }
}
</script>

<style scoped>
@import "../assets/css/home/home.css";

.router-link-active {
  color: white;
  text-decoration: none;
}

.el-dropdown {
  vertical-align: top;
}
.el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}

.el-main{
  padding: 5px;
}
.toggle-button{
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: white;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
.el-dropdown-link{
  color: white;
  cursor: pointer;
}
.router-link-active{
  text-decoration: none;
}
</style>