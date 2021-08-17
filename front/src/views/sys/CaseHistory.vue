<template>
  <div>

    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="病历列表">

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
                        <el-button icon="el-icon-search" type="primary" @click="getcateList()" plain>搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple-light" style="text-align: left">
                        <el-button class="el-icon-document" type="primary" @click="aKeyExport"> 一键导出</el-button>
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
                      prop="caid"
                      label="病历编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="patientsid"
                      label="患者编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      label="姓名"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="age"
                      label="年龄"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="gender"
                      label="性别"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      width="200"
                      prop="clinictime"
                      label="就诊时间"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      width="200"
                      prop="operationName"
                      label="手术"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      width="300"
                      prop="petient"
                      label="病情"
                      sortable>
                  </el-table-column>

                  <el-table-column label="操作">
                    <template slot-scope="scope">

                        <el-button size="mini" type="success" plain
                                   slot="reference" @click="getcateById(scope.row.patientsid)">打印病历单
                        </el-button>

                    </template>
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
  name: "CaseHistory",
  data() {
    return {
      input: '',
      currentPage4: 1,

      //表格数据呈现
      tableData: [],
      size: 5,
      current: 1,
      total: 0
    }


  },
  mounted() {
    this.getcateList();

  },

  methods: {

    getcateList() {
      this.$http.get("/casehistory/caseList", {
        params: {
          name: this.input,
          size: this.size,
          current: this.current
        }
      }).then((res) => {
        console.log(res)
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.total = res.data.data.total
        this.current = res.data.data.current
      })

    },

    //单个打印
    getcateById(id) {
      this.$http.get("/casehistory/caseById?Id=" + id).then((res) => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
      });
    },

    //一键导出
    aKeyExport(){

      this.$http.post("/casehistory/aKeyExport").then(res =>{
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
      })

    },

    //分页
    handleSizeChange(val) {
      this.size = val
      this.getcateList();
      // console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.current = val
      this.getcateList();
      // console.log(`当前页: ${val}`);
    }


    //删除

  },
}
</script>

<style scoped>

</style>