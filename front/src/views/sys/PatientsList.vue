<template>
  <div>


    <!--   添加患者弹出层-->
    <el-dialog  :before-close="handleClose" title="提示" :visible.sync="dialogTableVisible" >

      <el-form :model="form" :rules="editFormRules"  ref="editForm" >
        <el-form-item label="患者名字" prop="name" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="患者年龄" prop="age" :label-width="formLabelWidth">
          <el-input v-model="form.age" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="患者性别" prop="gender" :label-width="formLabelWidth">
          <el-radio-group v-model="form.gender">
            <el-radio  :label="nan">男</el-radio>
            <el-radio  :label="nv">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item v-if="timeState" label="就诊时间" prop="clinictime" :label-width="formLabelWidth">
          <div class="block">
            <el-date-picker
                v-model="form.clinictime"
                @blur="clinictimeDoctor"
                type="datetime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>


        <el-form-item v-if="timeState" label="就诊医生" :label-width="formLabelWidth">
          <el-select v-model="form.doctorid" @change="schedulingTime" placeholder="请选择要医生">
            <el-option v-for="dc in doctorList"
                       :key="dc.doctorid"
                       :label="dc.name"
                       :value="dc.doctorid"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item v-if="timeState" prop="atid" label="排班时间" :label-width="formLabelWidth">
          <el-select v-model="form.atid" @change="schedulingTime" placeholder="排班时间">
            <el-option v-for="st in stimeList"
                       :key="st.atid"
                       :label="st.datetime"
                       :value="st.atid"
            >
            </el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="基本病情" prop="petient" :label-width="formLabelWidth">

          <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入患者的基本情况"
              v-model="form.petient">
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>

    <!--    手术-->
    <el-dialog title="手术" :visible.sync="surgery" width="60%">
      <el-row>
        <el-col :span="8">
          <div class="grid-content bg-purple">
            <div class="grid-content bg-purple">
              <el-input
                  placeholder="请输入手术名称"
                  v-model="surgeryInput"
                  clearable>
              </el-input>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content bg-purple-light">
<!--            this.newSurgery.surgeryPatientsid = patientsid-->
<!--            this.newSurgery.surgeryDoctorid = doctorid-->
            <el-button @click="selectSurgery" icon="el-icon-search">搜索</el-button>
          </div>
        </el-col>
      </el-row>
      <el-table :data="surgeryData" ref="multipleTableSurgery" @selection-change="handleSelectionChange" height="300">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column property="opsid" label="手术编号" ></el-table-column>
        <el-table-column property="opsname" label="手术名称" ></el-table-column>
        <el-table-column property="opsmoney" label="手术价格"></el-table-column>
        <el-table-column property="opsname" label="手术名称" ></el-table-column>
      </el-table>

      <div style="font-size: 18px" >总金额：<span style=" font-size: 20px;color: #ff3434">{{surgerySumMoney}}</span> 元</div>
      <div slot="footer" class="dialog-footer" >
        <el-button @click="surgery = false">取 消</el-button>
        <el-button type="primary"  @click="affirmSurgery">确 定</el-button>
      </div>
    </el-dialog>

    <!--    开药外层-->
    <el-dialog title="开药" :visible.sync="dialogTable" width="60%">
      <el-row>
        <el-col :span="8">
          <div class="grid-content bg-purple">
            <div class="grid-content bg-purple">
              <el-input
                  placeholder="请输入药品名称"
                  v-model="prescribeInput"
                  clearable>
              </el-input>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content bg-purple-light">
            <el-button @click="selectPrescribe" icon="el-icon-search">搜索</el-button>
          </div>
        </el-col>
      </el-row>
      <el-table @selection-change="drugHandleSelectionChange" :data="gridData" height="300">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column property="drugid" label="药品编号" ></el-table-column>
        <el-table-column property="name" label="药品名称" ></el-table-column>
        <el-table-column property="type" label="药品类别" ></el-table-column>
        <el-table-column property="price" label="药品价格"></el-table-column>
        <el-table-column property="count" label="药品数量"></el-table-column>
      </el-table>


      <div style="font-size: 18px" >总金额：<span style=" font-size: 20px;color: #ff3434">{{drugSumMoney}}</span> 元</div>
      <div slot="footer" class="dialog-footer" >
        <el-button @click="dialogTable = false">取 消</el-button>
        <el-button type="primary"  @click="determineThePrescription">确 定</el-button>
      </div>
    </el-dialog>

    <!--    结算-->
    <el-dialog title="结算" :visible.sync="settleAccounts" width="60%">

      <el-table :data="settleAccountsData" height="300">
        <el-table-column property="id" label="编号" ></el-table-column>
        <el-table-column property="clinictime" width="200px" label="创建时间" ></el-table-column>
        <el-table-column property="count" label="数量"></el-table-column>
        <el-table-column property="doctorName" label="医生名称"></el-table-column>
        <el-table-column property="opsname" label="手术名称"></el-table-column>
        <el-table-column property="drugName" width="200px" label="药品名称"></el-table-column>
        <el-table-column property="pname" label="患者名称"></el-table-column>
        <el-table-column property="cost" label="单价"></el-table-column>
      </el-table>

      <div style="font-size: 18px" >总金额：<span style=" font-size: 20px;color: #ff3434">{{medical.payamount}}</span> 元</div>


      <div slot="footer" class="dialog-footer" >
        <el-button @click="settleAccounts = false">取 消</el-button>
        <el-button type="primary"  @click="determineTheSettlement">确 定</el-button>
      </div>
    </el-dialog>

    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="患者列表">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入患者名字"
                              v-model="input"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button @click="getPatientsList" icon="el-icon-search" type="primary" plain>搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple" style="text-align: left">
                        <el-button class="el-icon-circle-plus-outline" type="primary" @click="add"> 添加患者</el-button>
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

                  <el-table-column type="expand">
                    <template slot-scope="props">
                      <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="患者姓名:">
                          <span>{{ props.row.name }}</span>
                        </el-form-item>
                        <el-form-item label="患者电话:">
                          <span>{{ props.row.phone }}</span>
                        </el-form-item>
                        <el-form-item label="性别:">
                          <span>{{ props.row.gender }}</span>
                        </el-form-item>
                        <el-form-item label="年龄:">
                          <span>{{ props.row.age }}</span>
                        </el-form-item>
                        <el-form-item label="就诊时间:">
                          <span>{{ props.row.clinictime }}</span>
                        </el-form-item>
                        <el-form-item label="就诊医生:">
                          <span>{{ props.row.doctorName }}</span>
                        </el-form-item>
                        <el-form-item label="病情:">
                          <span>{{ props.row.petient }}</span>
                        </el-form-item>
                      </el-form>
                    </template>
                  </el-table-column>

                  <el-table-column
                      prop="patientsid"
                      label="编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      label="患者姓名"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="age"
                      label="年龄"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="doctorName"
                      label="就诊医生"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="pstate"
                      label="状态"
                      sortable>
                    <template slot-scope="scope">
                      <el-tag size="small" v-if="scope.row.pstate === 0">正在治疗</el-tag>
                      <el-tag size="small" v-if="scope.row.pstate === 1" type="success">治疗完成</el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column
                      width="200"
                      prop="clinictime"
                      label="就诊日期"
                      sortable>
                  </el-table-column>


                  <el-table-column label="操作" width="380">
                    <template slot-scope="scope">
                      <div v-if="scope.row.pstate==0">
                      <el-button size="mini" type="info" plain
                                 @click="editHandle(scope.row.patientsid)">编辑</el-button>

                      <el-button size="mini" type="success" plain
                                 @click="statesurgery(scope.row.patientsid,scope.row.doctorid)">手术</el-button>



                      <el-button size="mini" type="primary" plain
                                 @click="prescribe(scope.row.patientsid,scope.row.doctorid)">开药</el-button>

                      <el-button size="mini" type="warning" plain
                                 @click="closeAnAccount(scope.row.patientsid)">结算</el-button>

                      <el-button
                          size="mini"
                          type="danger"
                          @click="mydelete(scope.row.patientsid,scope.row.atid)">删除</el-button>
                      </div>
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



</template>

<script>
import Input_num from '@/components/Input_num'

export default {
  data() {
    return {
      input: '',
      nan:'男',
      nv:'女',
      total: 0,
      num: 1,
      size: 5,
      current: 1,
      surgerySumMoney:0,
      timeState:false,

      //计数器
      min_val: 1, // 设定最小值
      max_val: Infinity, // 设定最大值,Infinity表示无穷大
      val: 1 ,// 计数器初始值

      editFormRules: {
        name: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        gender: [
          {required: true, message: '请选择性别', trigger: 'blur'}
        ],

        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入用户电话', trigger: 'blur'},
          {min: 11, message: '长度为11位', trigger: 'blur'}
        ],

        clinictime: [
          {required: true, message: '请选择时间', trigger: 'blur'}
        ],
        petient: [
          {required: true, message: '请输入病情', trigger: 'blur'}
        ],
        doctorid:[
          {required: true, message: '请选择医生', trigger: 'blur'}
        ],
        atid:[
          {required: true, message: '请选择排版时间', trigger: 'blur'}
        ]

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
      value1: '',

      //表格数据呈现
      tableData: [],
      doctorList:[],
      stimeList:[],


      //手术弹出层数据
      surgeryData: [],


      //开药弹出层数据
      gridData: [],

      //内层数据明细
      orderDetails: [],
      //手术surgery
      surgery:false,
      //开药弹出层数据
      dialogTable: false,

      //结算
      settleAccounts: false,
      //结算二维码
      QRCode: false,

      //编辑患者弹出层
      dialogTableVisible: false,
      //添加弹出层
      dialogFormVisible: false,
      form: {
        name: '',
        age: '',
        gender: '',
        clinictime: '',
        atid:'',
        phone: '',
        doctorid: '',
        doctorName:'',
        petient: ''
      },
      formLabelWidth: '120px',
      saveUpdate:false,
      surgeryInput:'',
      multipleSelection: [],
      newSurgery:{
        ids:[],
        surgeryPatientsid:'',
        surgeryDoctorid:''
      },
      prescribeInput:'',
      drugSumMoney:0,

      drugMultipleSelection: [],
      drugNewSurgery:{
        ids:[],
        surgeryPatientsid:'',
        surgeryDoctorid:''
      },
      settleAccountsData:[],
      medical:{
        patientsid:'',
        count:0,
        paytime:'',
        payment:'支付宝',
        payamount:0,
        orderstate:'',
      },
      patientNumber:''
    }
  },

  //计数器
  components: {
    Input_num
  },

  created() {
    this.getPatientsList()
    this.getTheTypeOfMedicineAndSurgery()
  },
  methods: {


    //确定结算
    determineTheSettlement(){
      if(this.medical.payamount !=0){
        this.$http.post("/sys/medical/pay",this.medical).then(res => {
          console.log("状态值")
          console.log(res)
          if(res.data.data===""){
            this.$message({
              showClose: true,
              message: '会员扣费成功！',
              type: 'success'
            });
            this.getPatientsList()
          }else{
            var myWindow = window.open('/index','width=200,height=100');
            myWindow.document.write(res.data.data);
          }
        })
        this.getPatientsList()
        this.settleAccounts = false
      }else {
        this.$message({
          showClose: true,
          message: '费用不能等于0！',
          type: 'error'
        });
      }

    },

    //结算
    closeAnAccount(id){
      this.medical.payamount = 0
      this.medical.patientsid = id
      this.settleAccounts = true
      this.$http.get("/sys/medical/listById?id="+id).then(res => {
        this.settleAccountsData = res.data.data;
        this.settleAccountsData.forEach(row => {
          this.medical.payamount+=row.cost
        });
        this.medical.count = res.data.data.length
      });
    },

    //手术的选择框
    handleSelectionChange(val) {
      this.surgerySumMoney = 0
      this.multipleSelection = []
      this.multipleSelection = val;
      for (var i=0;i<this.multipleSelection.length;i++){
        this.surgerySumMoney += this.multipleSelection[i].opsmoney
      }
    },

    //药品的选择框
    drugHandleSelectionChange(val){
      this.drugSumMoney = 0
      this.drugMultipleSelection = []
      this.drugMultipleSelection = val
      for(var i=0;i<this.drugMultipleSelection.length;i++){
          this.drugSumMoney += this.drugMultipleSelection[i].price
      }
    },


    formatter(row, column) {
      return row.address;
    },


    //手术搜索
    selectSurgery(){
      this.getTheTypeOfMedicineAndSurgery()
    },

    //药品搜索
    selectPrescribe(){
      this.getTheTypeOfMedicineAndSurgery()
    },

    //确定开药
    determineThePrescription(){

      if(this.drugSumMoney==0){
        this.$message({
          showClose: true,
          message: '请选择药品',
          type: 'error'
        });
      }else{

        this.drugNewSurgery.ids = []

        this.drugMultipleSelection.forEach(row =>{
          this.drugNewSurgery.ids.push(row.drugid)
        })


        this.$http.post("/patients/prescribe",this.drugNewSurgery).then(res =>{

          this.$message({
            showClose: true,
            message: '恭喜你，操作成功',
            type: 'success'
          });

          this.dialogTable = false
          this.getTheTypeOfMedicineAndSurgery()
        });
      }
    },

    //确定进行手术
    affirmSurgery(){

      if(this.surgerySumMoney==0){
        this.$message({
          showClose: true,
          message: '请选择手术',
          type: 'error'
        });
      }else {
        this.newSurgery.ids = []
        this.multipleSelection.forEach(row => {
          this.newSurgery.ids.push(row.opsid)
        })
        this.$http.post("/patients/undergoSurgery",this.newSurgery).then(res =>{
          this.$message({
            showClose: true,
            message: '恭喜你，操作成功',
            type: 'success'
          });
        });
        this.getTheTypeOfMedicineAndSurgery()
        this.surgery = false
      }

    },



    //开药
    prescribe(patientsid,doctorid){
      this.getTheTypeOfMedicineAndSurgery()
      this.dialogTable = true
      this.drugNewSurgery.surgeryDoctorid = doctorid
      this.drugNewSurgery.surgeryPatientsid = patientsid
    },
    //手术
    statesurgery(patientsid,doctorid){
      this.getTheTypeOfMedicineAndSurgery()
      this.surgery = true
      this.newSurgery.surgeryPatientsid = patientsid
      this.newSurgery.surgeryDoctorid = doctorid
    },

    getTheTypeOfMedicineAndSurgery(){
      //获取所有手术类型
      this.$http.get("/patients/operationList",{params:{
          opsname:this.surgeryInput
        }}).then(res =>{
        this.surgeryData = res.data.data
      });

      //获取药品信息
      this.$http.get("/patients/drugList",{params:{name:this.prescribeInput}}).then(res => {
        this.gridData = res.data.data
      });

    },


    //根据医生Id和选择的时间给出医生的排班时间
    schedulingTime(){
      this.$http.get("/patients/scheduling", {
        params: {
          id: this.form.doctorid,
          clinictime: this.form.clinictime
        }
      }).then(res => {
        this.stimeList = res.data.data;
      })
    },

    //提交表单
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post('/patients/' + (this.saveUpdate?'save' : 'update'), this.form)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success'
                });
                this.form = {}
                this.getPatientsList()
                this.dialogTableVisible = false
              })
        } else {

          return false;
        }
      });
    },

    add(){
      this.timeState = true
      this.saveUpdate = true
      this.dialogTableVisible = true
    },

    //根据选择的时间给出医生排版时间
    clinictimeDoctor(){
      this.form.doctorid =''
      this.form.atid = ''

       this.$http.get('/patients/arrange/'+this.form.clinictime).then(res => {
          this.doctorList = res.data.data
       })
    },
    editHandle(id){
      this.timeState = false
      this.saveUpdate = false
      this.$http.get('/patients/info/' + id).then(res => {
        this.form = res.data.data
        this.dialogTableVisible = true
      })
    },

    handleClose(){
      this.form = {}
      this.dialogTableVisible = false
    },

    getPatientsList(){
      this.$http.get("/patients/list", {
        params: {
          username: this.input,
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
    mydelete(id,atid) {

      this.$confirm('此操作将永久删除该患者, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$http.post("/patients/delete/"+id+":"+atid).then(res =>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getPatientsList()
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },


    //分页
    handleSizeChange(val) {

      this.size = val
      this.getPatientsList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getPatientsList()
    },

    count_num(data) {
      this.val = data
    }

  },


  name: "PatientsList"
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
