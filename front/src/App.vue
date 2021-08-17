<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>


export default {
  name: 'app',
  // watch:{
  //   $route(to,from){
  //     if(to.path!= '/login'){
  //       let obj = {
  //         title: to.title,
  //         name: to.name,
  //       }
  //       this.$store.commit("addTab",obj)
  //     }
  //   }
  // }
}
</script>

<style>

</style>
