<template>


  <!--最外层-->
    <div class="login_container">

        <!--中间层-->
        <div class="login_box">
          <!--图标区域-->
          <div class="avatar_box"  :title="faceTitle" v-loading.fullscreen.lock="fullscreenLoading" style="cursor: pointer" @click="faceSubmit">
              <img :src="faceImgSrc"/>
          </div>

          <!--登录表单区域-->
          <el-tabs @tab-click="face" type="border-card" class="tabs_login">
            <el-tab-pane>
              <span slot="label"><i class="el-icon-unlock"></i> 密码登录</span>
                  <div class="tabs_login_div">
                    <el-form :rules="loginFormRules" :model="loginForm" ref="loginFormRef" >
                      <!--账号-->
                      <el-form-item prop="username">
                        <el-input v-model="loginForm.username"
                                  type="text" placeholder="账号"
                        prefix-icon="el-icon-s-custom"
                        ></el-input>
                      </el-form-item>
                      <!--密码-->
                      <el-form-item prop="password">
                        <el-input v-model="loginForm.password"
                                  type="password" placeholder="密码"
                        prefix-icon="el-icon-key"
                        ></el-input>
                      </el-form-item>

                      <el-form-item class="btns">
                        <el-button @click="login" type="primary">登录</el-button>
                        <el-button @click="restLogin" type="info">重置</el-button>
                      </el-form-item>

                    </el-form>
                  </div>

            </el-tab-pane>


          </el-tabs>

        </div>

    </div>



</template>

<script>

import Face from "@/components/login/Face";
import qs from "qs";

export default {
  name: "Login",
  components: {Face},
  data(){
    return{
      loginForm:{
        username:'',
        password:''
      },
      faceState:false,
      faceTitle:'',
      faceImgSrc:'https://thisuseravatar.oss-cn-guangzhou.aliyuncs.com/adminSystem/logo.jpg',
      context:'',
      video:'',
      canvas:'',
      imgName:'',
      fullscreenLoading:false,
      loginFormRules:{
        username: [
          {message:"请输入您的账号",trigger:'blur',required:true},
          {min:6,message:"至少大于等于6个字符",trigger:'blur'}
        ],
        password: [
          {message:"请输入您的密码",trigger:'blur',required:true},
          {min:6,message:"至少大于等于6个字符",trigger:'blur'}
        ]
      }
    }
  },
  methods:{

    //当选中第二个tab时，开启摄像头
    face(e){
      if(e.index==1){

        this.faceState = true;
        this.faceTitle = '开始登录';
        this.faceImgSrc = 'https://thisuseravatar.oss-cn-guangzhou.aliyuncs.com/adminSystem/facedong3.gif';


        function getUserMedia(constraints,success,error){
          if(navigator.mediaDevices.getUserMedia){
            navigator.mediaDevices.getUserMedia(constraints).then(success);
          }else if (navigator.webkitGetUserMedia) {
            navigator.webkitGetUserMedia(constraints,success,error);
          }else if (navigator.mozGetUserMedia) {
            navigator.mozGetUserMedia(constraints,success,error);
          }else if (navigator.getUserMedia) {
            navigator.getUserMedia(constraints,success,error)
          }
        }
        this.video = document.getElementById('video');
        this.canvas = document.getElementById('canvas');
        this.context = canvas.getContext('2d');
        var video = this.video;
        //成功回调
        function success(stream){
          video.srcObject = stream;
          video.play();
        }
        //失败回调
        function error(error) {
          console.log("访问用户媒体失败");
        }
        //开启摄像头
        if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
          getUserMedia({video:{width:500,height:500}},success,error)
        }else {
          alert("不支持");
        }

      }else{

        this.faceState = false;
        this.faceTitle = '';
        this.faceImgSrc = 'https://thisuseravatar.oss-cn-guangzhou.aliyuncs.com/adminSystem/logo.jpg';

        //重新刷新页面
        location. reload();
      }
    },

    //提交人脸信息
    faceSubmit(){
      if(this.faceState){
        //调用子组件 实现拍照的功能
        this.$refs.faceChild.startTakingPhotos(this.context,this.video);
      }

    },
    //修改图片的值
    updateImg(src){
      this.faceImgSrc = src ;
    },
    //重置表单
    restLogin(){
      this.$refs.loginFormRef.resetFields();
    },

    //开始登录
    login(){

      this.$refs.loginFormRef.validate((valid)=>{
          if(valid){

            this.$http.post('/login?'+qs.stringify(this.loginForm)).then(res=>{

              const jwt=res.headers['authorization']

              this.$store.commit('SET_TOKEN',jwt)
              const loading = this.$loading({
                lock: true,
                text: '登录中....',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
              });
              setTimeout(() => {
                loading.close();
                this.$router.push("/home");
              }, 2000);

            })
          }
      })
    }

  }
}
</script>

<style  scoped>
@import "../assets/css/login/login.css";
</style>