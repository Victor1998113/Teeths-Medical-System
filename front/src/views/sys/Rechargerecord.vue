<template>
<div>
  <el-row>
    <el-col :span="24">
      <div class="grid-content bg-purple-dark">

        <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
          <el-row>
            <el-col :xs="24">
              <el-card header="充值记录">

                <el-row>
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                      <div class="grid-content bg-purple">
                        <el-input
                            placeholder="请输入订单编号"
                            v-model="searchForm.staid"
                            clearable>
                        </el-input>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="4">
                    <div class="grid-content bg-purple-light">
                      <el-button icon="el-icon-search" type="primary" @click="getList" plain >搜索</el-button>
                    </div>
                  </el-col>
                </el-row>

              </el-card>
            </el-col>
            <el-col :xs="24">
              <el-table
                  :data="tableData"
                  style="width: 100%"
                  :default-sort = "{prop: 'date', order: 'descending'}">
                <el-table-column
                    prop="staid"
                    label="充值订单编号"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="mname"
                    label="会员姓名"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="paymoney"
                    label="充值金额"
                    sortable>
                </el-table-column>
                <el-table-column
                    prop="paydate"
                    label="充值时间"
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
  name: "Rechargerecord",
  data() {
    return {
      order: '',
      currentPage4: 4,
      searchForm: {},
      total: 0,
      size: 5,
      current: 1,
      //表格数据呈现
      tableData: [],

    }

  },
  mounted() {
    //页面加载完执行该方法
    this.getList();
  },
  methods: {
    //获取数据
    getList() {
      this.$http.get("/sys/rechargerecord/list", {
        params: {
          staid: this.searchForm.staid,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    },
    //操作
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleSizeChange(val) {

      this.size = val
      this.getList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getList()
    }


    //删除

  },
}
</script>

<style scoped>

</style>
