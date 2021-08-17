<template>
<div>

  <!--人脸登录区域-->
  <div id="face">
    <video id="video" width="424" height="245"></video>
    <canvas id="canvas" width="424" height="505"></canvas>
    <!--光影特效-->
    <div class="renlian">
      <div class="line"></div>
    </div>
  </div>

</div>
</template>

<script>
import qs from "qs";

export default {
  name: "Face",
  data(){
    return{
      imgName:'',
      loading:'',

    }
  },
  methods:{
    //执行拍照
    startTakingPhotos(context,video){

      this.loading = false;

      context.drawImage(video,0,0,500,500);

      //下面的代码是保存canvas标签里的图片并且将其按一定的规则重命名
      var type = 'jpg';
      var _fixType = function(type) {
        type = type.toLowerCase().replace(/jpg/i, 'jpeg');
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
      };

      //获取canvas标签里的图片内容
      var imgData = document.getElementById('canvas').toDataURL(type);
      imgData = imgData.replace(_fixType(type),'image/octet-stream');

      var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
      save_link.href = imgData;

      save_link.download = this.imgName;

      this.child_face_methods_img(imgData);


      //上传后台
      console.log(imgData);

      var faceData = new FormData();
      faceData.append('faceBase64',imgData);

      this.$http.post('/login?',faceData).then(res=>{

        const jwt=res.headers['authorization']

        this.$store.commit('SET_TOKEN',jwt)




      })


      // this.loading = this.$loading({
      //   lock: true,
      //   text: '人脸正在检测中',
      //   spinner: 'el-icon-loading',
      //   background: 'rgba(0, 0, 0, 0.7)'
      // });
      //
      // setTimeout(() => {
      //   this.loading.close();
      // }, 10000);


    }
  },
  props:['child_face_methods_img']
}
</script>

<style scoped>
@import "../../assets/css/login/faceCss/liudong.css";
</style>