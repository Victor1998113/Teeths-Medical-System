<template>
<div>
  <el-row>
    <el-col :span="24">
      <el-row>
        <el-col :xs="24">
          <el-card header="职位管理">
            <el-form :inline="true">
              <el-form-item>
                <el-row>
                  <el-col :span="16">
                    <div class="grid-content bg-purple">
                      <el-input
                          v-model="searchForm.name"
                          placeholder="名称"
                          clearable>
                      </el-input>
                    </div>
                  </el-col>
                  <el-col :span="8">
                    <div class="grid-content bg-purple-light">
                        <el-button icon="el-icon-search" type="primary" plain @click="getRoleList">搜索</el-button>
                    </div>
                  </el-col>
                </el-row>
              </el-form-item>

              <el-form-item>
                <el-button class="el-icon-circle-plus-outline" type="primary" v-if="hasAuth('sys:position:save')" @click="dialogVisible = true"> 新增</el-button>
              </el-form-item>
              <el-form-item>
                <el-popconfirm title="这是确定批量删除吗？"  v-if="hasAuth('sys:position:delete')"  @confirm="delHandle(null)">
                  <el-button class="el-icon-circle-close" type="danger" slot="reference" :disabled="delBtlStatu"> 批量删除</el-button>
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

          <el-table-column
              type="selection"
              width="55">
          </el-table-column>

          <el-table-column
              prop="job"
              label="名称"
              width="120">
          </el-table-column>
          <el-table-column
              prop="posicode"
              label="唯一编码"
              show-overflow-tooltip>
          </el-table-column>


          <el-table-column
              prop="pstate"
              label="状态">
            <template slot-scope="scope">
              <el-tag size="small" v-if="scope.row.pstate === 1" type="success">正常</el-tag>
              <el-tag size="small" v-else-if="scope.row.pstate === 0" type="danger">禁用</el-tag>
            </template>

          </el-table-column>
          <el-table-column
              prop="icon"
              label="操作">

            <template slot-scope="scope">
              <el-button  size="mini" type="success" plain
                          v-if="hasAuth('sys:position:authority')" @click="permHandle(scope.row.positionid)">分配权限</el-button>
              <el-divider direction="vertical"></el-divider>

              <el-button size="mini" type="info" plain
                         v-if="hasAuth('sys:position:update')" @click="editHandle(scope.row.positionid)">编辑</el-button>
              <el-divider direction="vertical"></el-divider>

              <el-popconfirm title="这是一段内容确定删除吗？" v-if="hasAuth('sys:position:delete')" @confirm="delHandle(scope.row.positionid)">
                <el-button size="mini" type="danger" slot="reference">删除</el-button>
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

  <!--新增对话框-->
  <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="600px"
      :before-close="handleClose">

    <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

      <el-form-item label="职位名称" prop="name" label-width="100px">
        <el-input v-model="editForm.job" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="唯一编码" prop="code" label-width="100px">
        <el-input v-model="editForm.posicode" autocomplete="off"></el-input>
      </el-form-item>


      <el-form-item label="状态" prop="statu" label-width="100px">
        <el-radio-group v-model="editForm.pstate">
          <el-radio :label=0>禁用</el-radio>
          <el-radio :label=1>正常</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
        <el-button @click="resetForm('editForm')">重置</el-button>
      </el-form-item>
    </el-form>

  </el-dialog>

  <el-dialog
      title="分配权限"
      :visible.sync="permDialogVisible"
      width="600px">

    <el-form :model="permForm">

      <el-tree
          :data="permTreeData"
          show-checkbox
          ref="permTree"
          :default-expand-all=true
          node-key="authorityid"
          :check-strictly=true
          :props="defaultProps">
      </el-tree>

    </el-form>

    <span slot="footer" class="dialog-footer">
			    <el-button @click="permDialogVisible = false">取 消</el-button>
			    <el-button type="primary" @click="submitPermFormHandle('permForm')">确 定</el-button>
			</span>

  </el-dialog>

</div>
</template>

<script>
export default {
  name: "Position",
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,

      total: 0,
      size: 5,
      current: 1,

      dialogVisible: false,
      editForm: {

      },

      tableData: [],

      editFormRules: {
        job: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ],
        posicode: [
          {required: true, message: '请输入唯一编码', trigger: 'blur'}
        ],
        pstate: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },

      multipleSelection: [],

      permDialogVisible: false,
      permForm: {},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      permTreeData: []
    }
  },
  created() {
    this.getRoleList()

    this.$http.get('/sys/menu/list').then(res => {
      this.permTreeData = res.data.data
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
    handleSelectionChange(val) {
      console.log("勾选")
      console.log(val)
      this.multipleSelection = val;

      this.delBtlStatu = val.length == 0
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getRoleList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getRoleList()
    },

    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
        this.dialogVisible = false
        this.editForm = {}
      }

    },
    handleClose() {
      this.resetForm('editForm')
    },

    getRoleList() {
      this.$http.get("/sys/role/list", {
        params: {
          name: this.searchForm.name,
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
          this.$http.post('/sys/role/' + (this.editForm.positionid?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success'
                });
                this.getRoleList()
                this.dialogVisible = false
                this.resetForm(formName)
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$http.get('/sys/role/info/' + id).then(res => {
        this.editForm = res.data.data
       this.dialogVisible = true
      })
    },
    delHandle(id) {

      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.positionid)
        })
      }

      this.$http.post("/sys/role/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
        this.getRoleList()
      })
    },
    permHandle(id) {
      this.permDialogVisible = true

      this.$http.get("/sys/role/info/" + id).then(res => {
        this.$refs.permTree.setCheckedKeys(res.data.data.menuIds)
        console.log(res.data.data.menuIds);
        this.permForm = res.data.data
      })
    },

    submitPermFormHandle(formName) {
      var menuIds = this.$refs.permTree.getCheckedKeys()

      console.log(menuIds)

      this.$http.post('/sys/role/perm/' + this.permForm.positionid, menuIds).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
        this.getRoleList()
        this.permDialogVisible = false
        this.resetForm(formName)
      })
    }
  }
}
</script>

<style scoped>
/*.el-pagination {
  margin-top: 10px;
}*/

</style>