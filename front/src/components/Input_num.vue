<template>
  <div class="input_num">
    <button @click="minus">-</button>
    <input type="text" :value="counter_num" disabled />
    <button @click="add">+</button>
  </div>
</template>

<script>
export default {
  name: "Input_num",
  data() {
    return {
      counter_num: this.value, // 计数
      min_num: this.min, // 最小值
      max_num: this.max // 最大值
    }
  },
  props: ['min', 'max', 'value'],
  methods: {
    minus() {
      if (this.counter_num <= this.min_num) {
        this.counter_num = this.min_num
      } else {
        this.counter_num--
      }
    },
    add() {
      if (this.counter_num >= this.max_num) {
        this.counter_num = this.max_num
      } else {
        this.counter_num++
      }
    }
  },
  watch: {
    counter_num(newVal) {
      // console.log(newVal)
      this.$emit('input', newVal)
    }
  }
}
</script>

<style scoped>

.input_num > button {
  width: 20%;
  height: 100%;
  float: left;
  box-sizing: border-box;
  border: 1px solid #dcdfe6;
  outline: none;
  cursor: pointer;
  background: #f5f7fa;
  border-radius: 2px;
}
.input_num > button:active {
  background: #ffffff;
}
.input_num > button:hover {
  color: #409eff;
}
.input_num > input {
  width: 40%;
  height: 100%;
  float: left;
  box-sizing: border-box;
  outline: none;
  border: 1px solid #dcdfe6;
  text-align: center;
  border-left: 0;
  border-right: 0;
  background: #fff;
}
</style>