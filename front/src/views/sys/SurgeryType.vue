<template>
  <div>
    <!--    添加手术类型弹出层-->
    <el-dialog title="新增手术类型" :visible.sync="dialogFormVisible">
      <el-form :model="operationtype" ref="form" :rules="stuRules">
        <el-form-item label="手术名称" :label-width="formLabelWidth" prop="opsname">
          <el-input v-model="operationtype.opsname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手术金额" :label-width="formLabelWidth" prop="opsmoney">
          <el-input v-model="operationtype.opsmoney" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="qx">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <!--   编辑职位弹出层-->
    <el-dialog title="编辑手术类型" :visible.sync="dialogTableVisible">
      <el-form :model="operationtypeup" ref="form" :rules="stuRules">
        <el-form-item label="职位名称" :label-width="formLabelWidth" prop="opsname">
          <el-input v-model="operationtypeup.opsname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手术金额" :label-width="formLabelWidth" prop="opsmoney">
          <el-input v-model="operationtypeup.opsmoney" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="qxbj">取 消</el-button>
        <el-button type="primary" @click="updataById(),dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>


    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="手术类型">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入手术类型"
                              v-model="searchForm.opsname"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button icon="el-icon-search" @click="getList" type="primary" plain>搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple-light" style="text-align: left">
                        <el-button class="el-icon-circle-plus-outline" type="primary" @click="dialogFormVisible = true"> 新增手术类型</el-button>
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
                      prop="opsid"
                      label="类型编号"
                      sortable
                  >
                  </el-table-column>
                  <el-table-column
                      prop="opsname"
                      label="手术名称"
                      sortable
                  >
                  </el-table-column>
                  <el-table-column
                      prop="opsmoney"
                      label="手术金额"
                      sortable
                  >
                  </el-table-column>
                  <el-table-column label="操作">
                    <template slot-scope="scope">
                      <el-button
                          size="mini" type="info" plain
                          @click="updata(scope.row),dialogTableVisible = true">编辑
                      </el-button>
                      <el-button
                          size="mini"
                          type="danger"
                          @click="open(scope.$index, scope.row)">删除
                      </el-button>
                    </template>

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
  name: "SurgeryType",
  data() {
    return {
      input: '',
      currentPage4: 4,
      //表格数据呈现
      tableData: [],
      searchForm: {},
      total: 0,
      size: 5,
      current: 1,
      //编辑医生弹出层
      dialogTableVisible: false,
      //添加医生弹出层
      dialogFormVisible: false,
      operationtype: {
        opsname: '',
        opsmoney: ''
      },
      operationtypeup: {},
      //验证规则
      stuRules: {
        opsname: [{
          message: "请输入你的手术名称",
          trigger: 'blur',
          required: true
        },
          {
            min: 1,
            message: "至少大于1个字符",
            trigger: 'blur'
          }
        ],
        opsmoney: [{
          message: "请输入金额",
          trigger: 'blur',
          required: true
        }]
      },
      formLabelWidth: '100px'
    }
  },
  mounted() {
    //页面加载完执行该方法
    this.getList();
  },
  methods: {
    formatter(row, column) {
      return row.address;
    },
    //取消添加
    qx() {
      this.dialogFormVisible = false;
      this.operationtype = {brand_right: 0}
    },
    //取消编辑
    qxbj() {
      this.dialogTableVisible = false;
      this.operationtypeup = {brand_right: 0}
    },
    //添加
    save(form) {
      this.$refs.form.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("/sys/operationtype/save", this.operationtype);
        this.$message.success("添加成功")
        //清空数据
        this.operationtype = {brand_right: 0}
        this.getList();
        this.dialogFormVisible = false;
      })
      // this.$http.post("/sys/operationtype/save", this.operationtype).then((res) => {
      //
      //   this.$message.success("添加成功")
      //   this.getList();
      //     if (res.data.code !== 200) return this.$message.error("添加失败");
      // })
    },
    //删除
    open(index, row) {
      this.$confirm('此操作将永久删除该手术类型，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
// type字段表明消息类型，可以为success，error，info和warning，无效的设置将会被忽略
            type: 'warning'
          }
      ).then(() => {
//如果点击确定按钮，需要调用删除方法进行删除
            this.$http({
              url: '/sys/operationtype/delete?id=' + row.opsid,
              method: 'delete'
            }).then((res) => {
              if (res.data.code == 200) {
                this.$message({
                  type: 'success',
                  message: '册除成功!'
                });
              }
              this.getList();
            })
          }
      ).catch(() => {
        this.$message({
          type: 'info ',
          message: '已取消册除'
        });
      })
    },
    //获取单行数据赋值
    updata(row) {
      console.log("row:" + row);
      this.operationtypeup = row;
    },
    //修改
    updataById() {
      this.$http.put("/sys/operationtype/updata", this.operationtypeup).then((res) => {
        this.$message.success("修改成功")
        this.operationtypeup = {brand_right: 0}
        this.dialogTableVisible = false;
        this.getList();
      })
    },
    //获取数据
    getList() {
      this.$http.get("/sys/operationtype/list", {
        params: {
          opsname: this.searchForm.opsname,
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


    //分页
    //   handleSizeChange(val) {
    //     console.log(`每页 ${val} 条`);
    //   },
    //   handleCurrentChange(val) {
    //     console.log(`当前页: ${val}`);
    //   }
    // },
    handleSizeChange(val) {

      this.size = val
      this.getList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getList()
    },

  }
}
</script>

<style scoped>

</style>
