<template>
  <div ref="acc">
    <!--    添加弹出层-->
    <el-dialog title="添加会员" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="stuRules">
        <el-form-item label="会员名字" :label-width="formLabelWidth" prop="mname">
          <el-input v-model="form.mname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员年龄" :label-width="formLabelWidth" prop="mage">
          <el-input v-model="form.mage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员性别" :label-width="formLabelWidth" prop="mgender">
          <!--                    <el-input v-model="form.mgender" autocomplete="off" ></el-input>-->
          <el-radio v-model="form.mgender" :label="nan">男</el-radio>
          <el-radio v-model="form.mgender" :label="nv">女</el-radio>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth" prop="mphone">
          <el-input v-model="form.mphone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="办卡时间" :label-width="formLabelWidth" prop="mdate">
          <!--          <el-input v-model="form.clinictime" autocomplete="off"></el-input>-->

          <div class="block">

            <el-date-picker
                v-model="form.mdate"
                type="datetime"
                format="yyyy-MM-dd hh:mm:ss"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="充值余额" :label-width="formLabelWidth" prop="mmoney">
          <el-input :disabled="true" v-model="form.mmoney" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <!--   充值弹出层-->
    <el-dialog title="充值会员" :visible.sync="dialogTableVisible">
      <el-form :model="payForm" ref="payForm">

        <el-form-item label="充值余额" :label-width="formLabelWidth" prop="mmoney">
          <el-input  v-model="payForm.mmoney" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary"  @click="zf">确 定</el-button>
      </div>
    </el-dialog>

    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="会员列表">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入会员名字"
                              v-model="searchForm.mname"
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
                        <el-button class="el-icon-circle-plus-outline" type="primary"  @click="dialogFormVisible = true"> 添加会员</el-button>
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
                      prop="memberid"
                      label="编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="mname"
                      label="会员姓名"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="mage"
                      label="会员年龄"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="mgender"
                      label="性别"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="mphone"
                      label="联系电话"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      width="200"
                      prop="mdate"
                      label="办卡日期"
                      sortable>
                  </el-table-column>

                  <el-table-column
                      prop="mmoney"
                      label="余额"
                      sortable>
                  </el-table-column>

                  <el-table-column label="操作">
                    <template slot-scope="scope">

                      <el-button type="primary" plain size="mini"
                                 @click="payId(scope.row.memberid),dialogTableVisible = true">充值余额
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
let _this = null;
export default {
  name: "Member",
  data() {
    return {
      input: '',
      currentPage4: 4,
      //单选按钮（男女）
      nan: '男',
      nv: '女',
      //点击充值
      payForm:{
        memberid:'',
        mname:'',
        mage:'',
        mgender:'',
        mphone:'',
        mdate:'',
        mmoney:0
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
      searchForm: {},
      total: 0,
      size: 5,
      current: 1,
      //验证规则
      stuRules: {
        mname: [{
          message: "请输入你的手术名称",
          trigger: 'blur',
          required: true
        }
        ],
        mage: [{
          message: "请输入你的年龄",
          trigger: 'blur',
          required: true
        }],
        mgender: [{
          message: "请输入你的性别",
          trigger: 'blur',
          required: true
        }]
        ,
        mphone: [{
          message: "请输入你的联系电话",
          trigger: 'blur',
          required: true
        }]
        ,
        mdate: [{
          message: "请输入你的办卡日期",
          trigger: 'blur',
          required: true
        }]

      },
      //编辑弹出层
      dialogTableVisible: false,
      //添加弹出层
      dialogFormVisible: false,
      form: {
        name: '',
        age: '',
        sex: '',
        date: '',
        phone: '',
        moeny: ''
      },
      formLabelWidth: '120px'
    }


  },
  mounted() {
    //页面加载完执行该方法
    _this = this;
    this.getList();
  },
  methods: {
    formatter(row, column) {
      return row.address;
    },
    //点击充值
    async payId(memberid) {
      const {
        data: res
      } = await this.$http.get("/sys/member/selectMemberId?memberid=" + memberid);
      this.payForm.mname = res.data.mname;
      this.payForm.mage = res.data.mage;
      this.payForm.mgender = res.data.mgender;
      this.payForm.mphone = res.data.mphone;
      this.payForm.mdate = res.data.mdate;
      this.payForm.mmoney = "";
      this.payForm.memberid = res.data.memberid;
      console.log("测试1212:" + res);
    },
    //充值 进入支付宝页面
    zf(){
      // _this = this;
      this.dialogTableVisible = false;
      this.$http.post("/sys/member/pay",this.payForm).then(res => {
        var myWindow = window.open('/index','width=200,height=100');
        myWindow.document.write(res.data.data);
      })
    },
    //添加
    save() {
      this.$refs.form.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("/sys/member/save", this.form);
        this.$message.success("添加成功")
        //清空数据
        this.form = {brand_right: 0}
        this.getList();
        this.dialogFormVisible = false;
      })
    },
    //获取数据
    getList() {
      this.$http.get("/sys/member/list", {
        params: {
          mname: this.searchForm.mname,
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

  }
}
</script>

<style scoped>

</style>
