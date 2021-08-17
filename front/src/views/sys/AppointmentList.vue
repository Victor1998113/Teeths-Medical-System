<template>
  <div>
    <div>
      <!--    新增预约信息弹出层-->
      <el-dialog title="新增预约信息" :visible.sync="dialogFormVisible" width="700px" >
        <el-form :model="form"  :rules="appFormRules"  ref="appFormRef" >
          <el-form-item prop="username" label="预约人姓名" :label-width="formLabelWidth">
            <el-input v-model="form.username" autocomplete="off" ></el-input>
          </el-form-item>
          <el-form-item label="预约人电话" :label-width="formLabelWidth">
            <el-input v-model="form.phone" autocomplete="off" ></el-input>
          </el-form-item>

          <el-form-item prop="gender" label="性别" :label-width="formLabelWidth">
            <el-radio v-model="form.gender" label="1" >男</el-radio>
            <el-radio v-model="form.gender" label="2">女</el-radio>
          </el-form-item>

          <el-form-item prop="age" label="年龄" :label-width="formLabelWidth">
            <el-input v-model="form.age" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item prop="datetime" label="预约医生和时间" :label-width="formLabelWidth">
            <el-row>
              <el-col :span="8">
                <div class="grid-content bg-purple">
                  <el-input v-model="form.name" autocomplete="off" placeholder="医生姓名" :disabled="true"></el-input>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="grid-content bg-purple">
                  <el-input v-model="form.datetime" autocomplete="off" placeholder="时间段" :disabled="true"></el-input>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="grid-content bg-purple-light">
                  <el-button  @click="selectDoctorTime">选择预约的医生和时间</el-button>
                </div>
              </el-col>
            </el-row>


            <el-dialog
                width="35%"
                title="选择预约的医生和时间"
                :visible.sync="innerVisible"
                append-to-body>

              <el-row>
                <el-col :span="12">

                  <div class="grid-content bg-purple-light" style="text-align: center">
                    <el-select v-model="form.doctorid"  @change="composing" placeholder="请选择要预约的医生">
                      <el-option v-for="dc in doctor"
                                 :key="dc.doctorid"
                                 :label="dc.name"
                                 :value="dc.doctorid"

                      >
                      </el-option>
                    </el-select>
                  </div>

                </el-col>

                <el-col :span="12">
                  <div class="grid-content bg-purple-light" style="text-align: center">


                    <el-select v-model="form.atid" :no-data-text="no_data_text" @change="composingTime" placeholder="请选择要预约的时间段">
                         <el-option v-for="com in composingList"
                                    :key="com.atid"
                                    :label="com.datetime"
                                    :value="com.atid"
                         ></el-option>
                    </el-select>

                  </div>
                </el-col>
              </el-row>



              <div slot="footer" class="dialog-footer">
                <el-button @click="innerVisible = false">取 消</el-button>
                <el-button type="primary" @click="innerVisible = false">确认</el-button>
              </div>


            </el-dialog>

          </el-form-item>



          <el-form-item label="病情" prop="petient" :label-width="formLabelWidth">

            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入患者的基本情况"
                v-model="form.petient">
            </el-input>
          </el-form-item>

        </el-form>


        <div slot="footer" class="dialog-footer" >
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveApp('appFormRef')">确 定</el-button>
        </div>

      </el-dialog>


      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">

            <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
              <el-row>
                <el-col :xs="24">
                  <el-card header="预约详情">

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
                      <el-col :span="16">
                        <div class="grid-content bg-purple"  style="text-align: left">
                          <el-button class="el-icon-circle-plus-outline" type="primary"  @click="dialogFormVisible = true"> 新增预约信息</el-button>

                        </div>
                      </el-col>

                    </el-row>
                  </el-card>
                </el-col>
                <el-col :xs="24">



                  <el-table
                      :data="tableData"
                      style="width: 100%">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                          <el-form-item label="预约人姓名:">
                            <span>{{ props.row.username }}</span>
                          </el-form-item>
                          <el-form-item label="预约人电话:">
                            <span>{{ props.row.phone }}</span>
                          </el-form-item>
                          <el-form-item label="性别:">
                            <span>{{ props.row.gender }}</span>
                          </el-form-item>
                          <el-form-item label="年龄:">
                            <span>{{ props.row.age }}</span>
                          </el-form-item>
                          <el-form-item label="预约的时间:">
                            <span>{{ props.row.datetime }}</span>
                          </el-form-item>
                          <el-form-item label="预约的医生:">
                            <span>{{ props.row.name }}</span>
                          </el-form-item>
                          <el-form-item label="病情:">
                            <span>{{ props.row.petient }}</span>
                          </el-form-item>
                        </el-form>
                      </template>
                    </el-table-column>
                    <el-table-column
                        label="预约编号"
                        prop="appointmentid">
                    </el-table-column>
                    <el-table-column
                        label="预约人姓名"
                        prop="username">
                    </el-table-column>
                    <el-table-column
                        label="预约人电话"
                        prop="phone">
                    </el-table-column>
                    <el-table-column
                        label="预约的时间"
                        prop="datetime"
                        sortable>
                    </el-table-column>
                    <el-table-column label="操作">
                      <template slot-scope="scope">
                        <el-button
                            size="mini" type="primary" plain
                            @click="see(scope.row.appointmentid)">就诊</el-button>
                        <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.row.appointmentid)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>




                  <div class="block" style="background-color: white">


                    <el-pagination class="el-pagination"
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
  </div>
</template>

<script>
import qs from "qs";

export default {
  data() {
    return {

      input: '',//输入框
      total: 0,
      size: 5,
      current: 1,
      no_data_text:'',

      currentPage4: 4,//分页
      //单选按钮（男女）
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

      //表格数据呈现
      tableData: [],

      innerVisible: false,
      //编辑预约信息弹出层
      dialogTableVisible: false,
      //新增预约信息弹出层
      dialogFormVisible: false,
      form: {
        username: '',
        name:'',
        phone: '',
        gender: '',
        age: '',
        datetime: '',
        doctorid:'',
        atid:'',
        petient:'',
      },


      composingList:[],
      doctor: [],
      formLabelWidth: '120px',


      appFormRules:{
        username:[
          {message:"请输入预约人姓名",trigger:'blur',required:true},
        ],
        name:[
          {message:"请选择医生",trigger:'blur',required:true},
        ],
        phone:[
          {message:"请输入预约人手机",trigger:'blur',required:true},
          {min: 11,max:11, message: '长度为11位', trigger: 'blur'}
        ],
        gender:[
          {message:"请输入预约人性别",trigger:'blur',required:true},
        ],
        age:[
          {message:"请输入预约人年龄",trigger:'blur',required:true},
        ],
        petient:[
          {message:"请输入预约人病情",trigger:'blur',required:true},
        ],
        datetime:[
          {message:"请输入选择预约时间",trigger:'blur',required:true},
        ],


      }

    }


  },

  created() {
    this.getAppointmentList();
  },
  methods: {


    //添加
    saveApp(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {

          this.$http.post('/app/ment/save', this.form)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success'
                });
                this.getAppointmentList();
                this.dialogFormVisible = false
              })
        } else {

          return false;
        }
      });


    },

    //获取医生列表
    selectDoctorTime(){
      this.innerVisible = true
      this.$http.get("/app/ment/doctorList").then(res => {
        this.doctor = res.data.data;
      });

    },

    //获取当前医生的排版时间
    composing(){
      this.form.atid = ''
      this.form.datetime = ''
      this.$http.get("/app/ment/composingList/"+this.form.doctorid).then(res => {
          this.composingList = res.data.data;
      });

      this.$http.get('/sys/user/info/' + this.form.doctorid).then(res => {
        this.form.name = res.data.data.name
      })

    },

    //时间选择发生改变时
    composingTime(){
      this.$http.get("/app/ment/composingOne/"+this.form.atid).then(res => {
        this.form.datetime = res.data.data.datetime;
      });
    },

    getAppointmentList(){
        this.$http.get("/app/ment/list",{
          params: {
            username: this.input,
            current: this.current,
            size: this.size
          }
        }).then(res =>{
          this.tableData = res.data.data.records
          this.size = res.data.data.size
          this.current = res.data.data.current
          this.total = res.data.data.total
        });
    },

    search(){
      this.getAppointmentList();
    },

    formatter(row, column) {
      return row.address;
    },

    handleDelete(id) {

      this.$confirm('此操作将永久删除该条预约信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$http.post("/app/ment/delete/"+id).then(res =>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getAppointmentList()
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    see(id){
      this.$confirm('确定要就诊吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {

        this.$http.post("/app/ment/see/"+id).then(res =>{
          this.$message({
            type: 'success',
            message: '就诊成功!'
          });
          this.getAppointmentList()
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消就诊'
        });
      });
    },
    //分页
    handleSizeChange(val) {

      this.size = val
      this.getAppointmentList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getAppointmentList()
    },
  },
  name: "AppointmentList"
}
</script>

<style scoped>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

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