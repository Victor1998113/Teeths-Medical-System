<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row>
            <el-col :xs="24">
                <el-card >
                  <div style="display: flex;  height: 80px;">
                  <transition name="el-zoom-in-bottom">
                    <div class="transition-box">总收益(加会员充值)<br/>
                      <span style="font-size: 25px;color: red">{{calculateexpense.total}}</span>¥</div>
                  </transition>

                  <transition name="el-zoom-in-center">
                    <div class="transition-box">本月收益<br/>
                      <span style="font-size: 25px;color: red">{{calculateexpense.monthincome}}</span>¥</div>
                  </transition>

                  <transition name="el-zoom-in-top">
                    <div class="transition-box">今日收益<br/>
                      <span style="font-size: 25px;color: red">{{calculateexpense.dayincome}}</span>¥</div>
                  </transition>

                  <transition name="el-zoom-in-bottom">
                    <div  class="transition-box">患者统计<br/>
                      <span style="font-size: 25px;color: red">{{calculateexpense.patientscount}}</span>人</div>
                  </transition>

                  <transition name="el-zoom-in-top">
                    <div class="transition-box2">预约人数<br/>
                      <span style="font-size: 25px;color: red">{{calculateexpense.appointmentcount}}</span>人</div>
                  </transition>

                </div>
                  <br/><br/>
                <!--    折线图-->
                  <div id="myChart2" style="width:100%;height:400px;float:left;background-color: white"></div>

                </el-card>

            </el-col>
          <el-card shadow="never">
            <el-col :xs="24">
              <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="来源类型" > </el-table-column>
                <el-table-column label="网站基础指标" align="center">
                    <el-table-column prop="province" label="访客数(UV)" > </el-table-column>
                    <el-table-column prop="city" label="IP数" > </el-table-column>
                </el-table-column>
                <el-table-column label="流量质量指标" align="center">
                  <el-table-column prop="address" label="跳出率" > </el-table-column>
                  <el-table-column prop="zip" label="平均访问时长" > </el-table-column>
                </el-table-column>
              </el-table>
              <br/>
          </el-col>

          </el-card>
        </el-row>

      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",


  data () {
    return {
      tableData: [{
        date: '直接访问',
        name: '57,847',
        province: '7,129',
        city: '8,330',
        address: '26.38%',
        zip: '00:20:35'
      }, {
        date: '搜索引擎',
        name: '5,942',
        province: '1,338',
        city: '1,449',
        address: '33.49%',
        zip: '00:11:31'
      }, {
        date: '外部链接',
        name: '1,926',
        province: '469',
        city: '500',
        address: '44.53%',
        zip: '00:08:49'
      }],


      myChart2: '',
      calculateexpense:{
        total:'',//总收益
        monthincome:'',// 本月收益
        dayincome:'',// 今日收益
        patientscount:'',// 患者统计
        appointmentcount:'',// 预定人数
        yesterdayincome:'',//昨天收益
        nearlyincome:'',//近七天收益情况
        lastmonthincome:'',//上个月收益
      }
    }
  },
  mounted: function () {

    this.$http.get("/calculate/expense").then(res => {
        this.calculateexpense = res.data.data
        this.drawLine1(this.calculateexpense)
    });


  },
  methods: {
    drawLine1 (calculateexpense) {
      // 基于准备好的dom，初始化echarts实例
      this.myChart2 = this.$echarts.init(document.getElementById('myChart2'))
      // 绘制图表
      this.myChart2.setOption({
        title: {
          text: '爱丽丝总收益情况', // 主标题
          subtext: '', // 副标题
          x: 'left' // x轴方向对齐方式
        },
        tooltip: {
          trigger: 'axis' // axis   item   none三个值
        },
        xAxis: {
          type: 'category', // 还有其他的type，可以去官网喵两眼哦
          data: ['总收益(加会员充值)', '本月收益', '今日收益', '昨日收益', '近七天收益情况', '上个月收益'], // x轴数据
          name: ''
        },
        yAxis: {
          type: 'value',
          name: '收益'
        },
        legend: {
          orient: 'vertical',
          x: 'center',
          y: 'top',
          data: ['收益情况']
        },
        series: [
          {
            name: '收益情况',
            data: [calculateexpense.total,
              calculateexpense.monthincome,
              calculateexpense.dayincome,
              calculateexpense.yesterdayincome,
              calculateexpense.nearlyincome,
              calculateexpense.lastmonthincome],
            type: 'line'
          }
        ]
      })
    }
  }

}
</script>

<style scoped>

.transition-box {
  margin-bottom: 10px;
  width: 25%;
  height: 80px;
  text-align: center;
  color: #959595;
  padding: 20px 20px;
  box-sizing: border-box;
  border-right: 1px solid #d4d4d4;
}
.transition-box2{
  margin-bottom: 10px;
  width: 25%;
  height: 80px;
  text-align: center;
  color: #959595;
  padding: 20px 20px;
  box-sizing: border-box;
}

</style>