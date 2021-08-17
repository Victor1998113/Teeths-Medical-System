<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-row>
          <el-col :xs="24">
            <el-card header="医生列表">
              <el-form :inline="true">
          <el-form-item>
            <el-row>
              <el-col :span="16">
                <div class="grid-content bg-purple">
                  <el-input
                      v-model="searchForm.username"
                      placeholder="用户名"
                      clearable>
                  </el-input>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="grid-content bg-purple-light">
                  <el-button icon="el-icon-search" type="primary" plain @click="getUserList">搜索</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item>
            <el-button class="el-icon-circle-plus-outline" type="primary" @click="add" v-if="hasAuth('sys:doctorList:save')"> 新增</el-button>
          </el-form-item>

          <el-form-item>
            <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)">
              <el-button class="el-icon-circle-close" type="danger" slot="reference" :disabled="delBtlStatu" v-if="hasAuth('sys:doctorList:delete')"> 批量删除</el-button>
            </el-popconfirm>
          </el-form-item>

        </el-form>
            </el-card>
          </el-col>
          <el-col :xs="24">
            <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          border
          stripe
          @selection-change="handleSelectionChange">


        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="医生名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="医生性别">
                <span>{{ props.row.gender }}</span>
              </el-form-item>

              <el-form-item label="手机号">
                <span>{{ props.row.phone}}</span>
              </el-form-item>

              <el-form-item label="身份证号">
                <span>{{ props.row.idcard}}</span>
              </el-form-item>

              <el-form-item label="登录账号">
                <span>{{ props.row.username}}</span>
              </el-form-item>

              <el-form-item label="最后登录时间">
                <span>{{ props.row.lastlogin}}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>


        <el-table-column
            type="selection">
        </el-table-column>

        <el-table-column
            prop="name"
            label="医生名称">
        </el-table-column>


        <el-table-column
            prop="idcard"
            label="身份证号">
        </el-table-column>

        <el-table-column
            prop="roles"
            label="角色名称">
          <template slot-scope="scope">
            <el-tag size="small" type="info" v-for="item in scope.row.roles">{{item.job}}</el-tag>
          </template>

        </el-table-column>

        <el-table-column
            prop="statu"
            label="状态">
          <template slot-scope="scope">
            <el-tag size="small" v-if="scope.row.statu === 1" type="success">正常</el-tag>
            <el-tag size="small" v-else-if="scope.row.statu === 0" type="danger">禁用</el-tag>
          </template>

        </el-table-column>


        <el-table-column
            prop="icon"
            width="260"
            label="操作">

          <template slot-scope="scope">
            <el-button size="mini" type="success" plain
                       v-if="hasAuth('sys:doctorList:roles')" @click="roleHandle(scope.row.doctorid)">分配角色</el-button>
            <el-divider direction="vertical"></el-divider>



            <el-button size="mini" type="info" plain
                       v-if="hasAuth('sys:doctorList:update')" @click="editHandle(scope.row.doctorid)">编辑</el-button>
            <el-divider direction="vertical"></el-divider>

            <el-popconfirm title="这是一段内容确定删除吗？" v-if="hasAuth('sys:doctorList:delete')" @confirm="delHandle(scope.row.doctorid)">
              <el-button  size="mini" type="danger" slot="reference">删除</el-button>
            </el-popconfirm>

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
      </el-col>
    </el-row>
<!--    新增对话框-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="用户名" prop="username" label-width="100px">
          <el-input v-model="editForm.username" autocomplete="off"></el-input>
          <el-alert
              title="初始密码为888888"
              :closable="false"
              type="info"
              style="line-height: 12px;"
          ></el-alert>
        </el-form-item>

        <el-form-item v-show="passwordState" label="密码"  prop="password" label-width="100px">
          <el-input  v-model="editForm.password" type="password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="医生姓名"  prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="医生性别"  prop="gender" label-width="100px">
          <el-radio-group v-model="editForm.gender">
            <el-radio  :label="nan">男</el-radio>
            <el-radio  :label="nv">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="身份证号码"  prop="idcard" label-width="100px">
          <el-input v-model="editForm.idcard" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="手机号"  prop="phone" label-width="100px">
          <el-input v-model="editForm.phone" autocomplete="off"></el-input>
        </el-form-item>



        <el-form-item label="状态"  prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>

<!--     分配权限对话框-->
    <el-dialog title="分配角色" :visible.sync="roleDialogFormVisible" width="600px">

      <el-form :model="roleForm">
        <el-tree
            :data="roleTreeData"
            show-checkbox
            ref="roleTree"
            :check-strictly=checkStrictly
            node-key="positionid"
            :props="defaultProps">
        </el-tree>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="submitRoleHandle('roleForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DoctorList",
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,
      nan:'男',
      nv:'女',

      total: 0,
      size: 5,
      current: 1,

      dialogVisible: false,
      editForm: {

      },

      tableData: [],

      editFormRules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        gender: [
          {required: true, message: '请选择性别', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入用户电话', trigger: 'blur'},
          {min: 11,max:11, message: '长度为11位', trigger: 'blur'}
        ],
        idcard: [
          {required: true, message: '请输入用户身份证号', trigger: 'blur'},
          {min: 15,max:18, message: '长度为15位到18位', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],

      },

      multipleSelection: [],

      roleDialogFormVisible: false,
      defaultProps: {
        children: 'children',
        label: 'job'
      },
      roleForm: {},
      roleTreeData:  [],
      treeCheckedKeys: [],
      checkStrictly: true,
      passwordState:false

    }
  },
  created() {
    this.getUserList()

    this.$http.get("/sys/role/list").then(res => {
      console.log(res.data.data.records);
      console.log("==========操作信息");
      this.roleTreeData = res.data.data.records
    })
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    add(){
      this.dialogVisible = true
      this.passwordState = false
    },
    handleSelectionChange(val) {

      this.multipleSelection = val;

      this.delBtlStatu = val.length == 0
    },

    handleSizeChange(val) {

      this.size = val
      this.getUserList()
    },
    handleCurrentChange(val) {

      this.current = val
      this.getUserList()
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },

    getUserList() {
      this.$http.get("/sys/user/list", {
        params: {
          username: this.searchForm.username,
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

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post('/sys/user/' + (this.editForm.doctorid?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success'
                });
                this.getUserList()
                this.dialogVisible = false
              })
        } else {

          return false;
        }
      });
    },
    editHandle(id) {


      this.$http.get('/sys/user/info/' + id).then(res => {
        this.editForm = res.data.data

        this.editForm.password = "";
        this.dialogVisible = true
        this.passwordState = true
      })
    },
    delHandle(id) {

      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.doctorid)
        })
      }


      this.$http.post("/sys/user/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
        this.getUserList()
      })
    },

    roleHandle (id) {
      this.roleDialogFormVisible = true

      this.$http.get('/sys/user/info/' + id).then(res => {
        this.roleForm = res.data.data

        console.log(res.data.data)

        let roleIds = []

        res.data.data.roles.forEach(row => {
          roleIds.push(row.positionid)
        });


        this.$refs.roleTree.setCheckedKeys(roleIds)
      })
    },
    submitRoleHandle(formName) {
      var roleIds = this.$refs.roleTree.getCheckedKeys()

      console.log(roleIds)

      this.$http.post('/sys/user/role/' + this.roleForm.doctorid, roleIds).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
        this.getUserList()
        this.roleDialogFormVisible = false
      })
    }
  }
}
</script>

<style scoped>



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