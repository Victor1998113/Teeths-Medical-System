<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="开药记录">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入订单编号"
                              v-model.trim="name"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button icon="el-icon-search" type="primary" @click="getPharmacies()" plain>搜索</el-button>
                      </div>
                    </el-col>
                  </el-row>

                </el-card>
              </el-col>
              <el-col :xs="24">
                <el-table
                    :data="tableData"
                    style="width: 100%"
                    :default-sort="{prop: 'date', order: 'descending'}">
                  <el-table-column
                      prop="drugid"
                      label="药品编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="paName"
                      label="用药人"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="drName"
                      label="药品信息"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="doName"
                      label="用药医生"
                      sortable>
                  </el-table-column>
                </el-table>

                <div class="block" style="background-color: white">
                  <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="current"
                      :page-sizes="[5, 10, 20, 40]"
                      :page-size="size"
                      layout="total, sizes, prev, pager, next, jumper"
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
  name: "Dispensing",
  data() {
    return {
      order: '',
      currentPage4: 1,

      //表格数据呈现
      tableData: [],
      name: '',
      current: 1,
      size: 5,
      total: 0
    }

  },
  mounted() {
    this.getPharmacies();
  },

  methods: {

    getPharmacies() {
      this.$http.get("/sys/pharmacy/list", {
        params: {
          name: this.name,
          current: this.current,
          size: this.size
        }
      }).then((res) => {
        console.log(res)
        this.tableData = res.data.data.records
        this.current = res.data.data.current
        this.size = res.data.data.size
        this.total = res.data.data.total
      })
    },
    //分页
    handleSizeChange(val) {
      this.size = val
      this.getPharmacies()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getPharmacies()
    }


    //删除

  },
}
</script>

<style scoped>

</style>