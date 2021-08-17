<template>
  <div>

    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="手术统计">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入"
                              v-model.trim="name"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button type="primary" plain icon="el-icon-search" @click="getOperation()">搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple" style="text-align: left">

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
                      prop="operationid"
                      label="手术编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="doName"
                      label="手术医生"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="paName"
                      label="手术病人"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="opName"
                      label="手术类型"
                      sortable>
                  </el-table-column>
                </el-table>

                <div class="block" style="background-color: white">
                  <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="current"
                      :page-sizes="[5, 10, 20, 30]"
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
  data() {
    return {
      input: '',
      currentPage4: 4,

      //表格数据呈现
      tableData: [],
      name: '',
      //编辑患者弹出层
      dialogTableVisible: false,
      //添加患者弹出层
      dialogFormVisible: false,
      form: {
        doctor: '',
        patient: '',
        type: ''
      },
      formLabelWidth: '120px',
      current: 1,
      size: 5,
      total:0
    }


  },
  created() {
    this.getOperation();
  },
  methods: {
    formatter(row, column) {
      return row.address;
    },

    getOperation() {
      this.$http.get("/sys/operation/list", {
        params: {
          name: this.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        console.log(res.data.data.records)
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    },


    //分页
    handleSizeChange(val) {
      this.size = val
      this.getOperation()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getOperation()
    },



    //删除

  }
  ,
  name: "SurgeryList"
}
</script>

<style scoped>

</style>