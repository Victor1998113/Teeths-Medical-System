<template>
<div>
  <el-row>
    <el-col :span="24">
      <div class="grid-content bg-purple-dark">

        <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
          <el-row>
            <el-col :xs="24">
              <el-card header="财务统计">
                <el-row>
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                      <div class="grid-content bg-purple">
                        <el-input
                            placeholder="请输入"
                            v-model="input"
                            clearable>
                        </el-input>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="grid-content bg-purple-light">
                      <el-button icon="el-icon-search" @click="search" type="primary" plain>搜索</el-button>
                    </div>
                  </el-col>
                  <el-col :span="16"><div class="grid-content bg-purple" style="text-align: left"></div></el-col>
                </el-row>



              </el-card>
            </el-col>
            <el-col :xs="24">
              <el-table
                  :data="tableData"
                  style="width: 100%"
                  :default-sort = "{prop: 'date', order: 'descending'}">
                <el-table-column
                    prop="orderid"
                    label="订单编号"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="count"
                    label="购买数量"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="paytime"
                    label="支付时间"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="payment"
                    label="支付方式"
                    sortable>
                </el-table-column>

                <el-table-column
                    prop="payamount"
                    label="订单总金额"
                    sortable>
                </el-table-column>
              </el-table>

              <div class="block" style="background-color: white">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes, prev, pager, next, jumper"
                    :page-sizes="[5, 10, 30, 50]"
                    :current-page="current"
                    :page-size="size"
                    :total="total">
                </el-pagination>
              </div>
            </el-col>

          </el-row>
        </div>
      </div>
    </el-col>
  </el-row>
</div>
</template>

<script>
export default {
  data() {
    return {
      input: '',
      total: 0,
      size: 5,
      current: 1,
      currentPage4: 1,

      //表格数据呈现
      tableData: []
    }


  },
  created() {
    this.getList()
  },
  methods: {
    formatter(row, column) {
      return row.address;
    },

    search(){
      this.getList()
    },
    getList(){
      this.$http.get("/orderDetails/list",{params:{
          orderid: this.input,
          current: this.current,
          size: this.size
        }}).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      });
    },
    //分页
    handleSizeChange(val) {

      this.size = val
      this.getList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getList()
    },


    //删除

  },
  name: "OrderDetails"
}
</script>

<style scoped>
/*表格样式*/
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
