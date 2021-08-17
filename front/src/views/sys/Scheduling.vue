<template>
  <div>
    <!--    添加弹出层-->
    <el-dialog title="医生排班" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="stuRules" ref="form">
        <el-form-item label="医生姓名" :label-width="formLabelWidth" prop="doctorid">
          <el-select v-model="form.doctorid" placeholder="请选择要医生">
            <el-option v-for="item in doctor" :key="item.doctorid" :label="item.name"
                       :value="item.doctorid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期时间" :label-width="formLabelWidth" prop="datetime">
          <div class="block">
            <el-date-picker
                v-model="form.datetime"
                type="datetime"
                format="yyyy-MM-dd hh:mm:ss"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <!--   编辑患者弹出层-->
    <el-dialog title="编辑排班信息" :visible.sync="dialogTableVisible">

      <el-form :model="doctorup" :rules="stuRules" ref="doctorup">
        <el-form-item label="医生姓名" :label-width="formLabelWidth" prop="doctorid">
          <el-select v-model="doctorup.doctorid" placeholder="请选择要医生">
            <el-option v-for="item in doctor" :key="item.doctorid" :label="item.name"
                       :value="item.doctorid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期时间" :label-width="formLabelWidth" prop="datetime">
          <div class="block">
            <el-date-picker
                v-model="doctorup.datetime"
                type="datetime"
                format="yyyy-MM-dd hh:mm:ss"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>


      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateById()">确 定</el-button>
      </div>
    </el-dialog>


    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="医生排班">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入"
                              v-model="name"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button type="primary" plain icon="el-icon-search" @click="getList">搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple" style="text-align: left">
                        <el-button class="el-icon-circle-plus-outline" type="primary"
                                   @click="getDoctorList(),dialogFormVisible = true"> 新增排班
                        </el-button>
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
                      prop="name"
                      label="医生姓名"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="datetime"
                      label="日期时间"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="appstate"
                      label="是否预定"
                      sortable>
                    <template slot-scope="scope">
                      <el-tag size="small" v-if="scope.row.appstate === 0" type="success">未预定</el-tag>
                      <el-tag size="small" v-else-if="scope.row.appstate === 1" type="info" >已预定</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" width="350">
                    <template slot-scope="scope">
                      <div  v-if="scope.row.appstate === 0">
                        <el-button size="mini" type="info" plain
                                   @click="update(scope.row)">编辑
                        </el-button>
                        <el-button
                            size="mini"
                            type="danger"
                            @click="open(scope.$index, scope.row)">删除
                        </el-button>
                      </div>

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
  name: "Scheduling",
  data() {
    return {
      input: '',
      tableData: [],
      doctor: [],
      searchForm: {},
      total: 0,
      size: 5,
      current: 1,
      name: '',
      //验证规则
      stuRules: {
        doctorid: [{
          message: "请输入医生名称",
          trigger: 'blur',
          required: true
        }
        ],
        datetime: [{
          message: "请输入日期",
          trigger: 'change',
          required: true
        }]
      },
      //添加时间
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },

      //编辑患者弹出层
      dialogTableVisible: false,
      //添加患者弹出层
      dialogFormVisible: false,
      form: {},
      doctorup: {},
      formLabelWidth: '120px'
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
    //获取单行数据赋值
    update(row) {
      console.log("row:" + row);
      this.doctorup = row;
      this.getDoctorList();
      this.dialogTableVisible = true;
    },
    //修改
    updateById() {
      console.log(this.doctorup)
      this.$refs.doctorup.validate(async valid => {
        if (!valid) return;
        this.$http.put("/sys/scheduling/updata", this.doctorup).then((res) => {
          this.$message.success("修改成功")
          this.dialogTableVisible = false;
          this.getList();
        })
      })
    },
    //添加
    save(form) {
      this.$refs.form.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("/sys/scheduling/save", this.form);
        this.$message.success("添加成功")
        //清空数据
        this.form = {brand_right: 0}
        this.getList();
        this.dialogFormVisible = false;
      })
      console.log(this.form)
    },
    //查询所有的医生
    getDoctorList() {
      this.$http.get('/sys/scheduling/doctorList').then(res => {
        this.doctor = res.data.data
      })
    },
    //删除
    open(index, row) {
      this.$confirm('此操作将永久删除该药品，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
// type字段表明消息类型，可以为success，error，info和warning，无效的设置将会被忽略
            type: 'warning'
          }
      ).then(() => {
//如果点击确定按钮，需要调用删除方法进行删除
            this.$http({
              url: '/sys/scheduling/delete?atid=' + row.atid,
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
    //获取数据
    getList() {
      this.$http.get("/sys/scheduling/list", {
        params: {
          name: this.name,
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
  }
}
</script>

<style scoped>

</style>
