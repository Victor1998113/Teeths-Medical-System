<template>
  <div>
    <!--    拿药弹出层-->
    <el-dialog title="拿药" :visible.sync="dialogFormVisible" width="60%">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="药品名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品数量" :label-width="formLabelWidth" prop="count">
          <el-input v-model="form.count" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="药品类型" :label-width="formLabelWidth" prop="type">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品单价" :label-width="formLabelWidth" prop="price">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <!--   编辑药品弹出层-->
    <el-dialog title="编辑药品" :visible.sync="dialogTableVisible">
      <el-form :model="drug" ref="drug" :rules="rules">
        <el-form-item label="药品名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="drug.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品数量" :label-width="formLabelWidth" prop="count">
          <el-input v-model="drug.count" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="药品类型" :label-width="formLabelWidth" prop="type">
          <el-input v-model="drug.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品单价" :label-width="formLabelWidth" prop="price">
          <el-input v-model="drug.price" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="update(),dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>


    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">

          <div id="magicalDragScene" class="mc-root mc-ui-absolute-pane">
            <el-row>
              <el-col :xs="24">
                <el-card header="药品列表">

                  <el-row>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                        <div class="grid-content bg-purple">
                          <el-input
                              placeholder="请输入药品名称"
                              v-model="input"
                              clearable>
                          </el-input>
                        </div>
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple-light">
                        <el-button type="primary" plain icon="el-icon-search" @click="getList()">搜索</el-button>
                      </div>
                    </el-col>
                    <el-col :span="16">
                      <div class="grid-content bg-purple" style="text-align: left">
                        <el-button class="el-icon-circle-plus-outline" type="primary" @click="dialogFormVisible = true">
                          添加药品
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
                      prop="drugid"
                      label="药品编号"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      label="药品名称"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="count"
                      label="拿药数量"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="type"
                      label="药品类型"
                      sortable>
                  </el-table-column>
                  <el-table-column
                      prop="price"
                      label="药品单价"
                      sortable>
                  </el-table-column>
                  <el-table-column label="操作">
                    <template slot-scope="scope">
                      <el-button
                          size="mini" type="info" plain
                          @click="selectById(scope.row)">编辑
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
import Input_num from '@/components/Input_num'

export default {
  data() {
    return {
      //计数器
      min_val: 0, // 设定最小值
      max_val: Infinity, // 设定最大值,Infinity表示无穷大
      val: 0, // 计数器初始值

      medicineInput: '',
      currentPage4: 1,

      //表格数据呈现
      tableData: [],
      //编辑患者弹出层
      dialogTableVisible: false,
      //添加患者弹出层
      dialogFormVisible: false,
      drug: {},
      form: {
        name: '',
        count: '',
        type: '',
        price: ''
      },
      formLabelWidth: '120px',
      input: '',
      current: 1,
      size: 5,
      total: 0,
      rules: {
        name: [{message: "药品名称必须填写", trigger: 'blur', required: true}],
        count: [{message: "库存数量必须填写", trigger: 'blur', required: true}],
        type: [{message: "药品类型必须填写", trigger: 'blur', required: true}],
        price: [{message: "药品价格必须填写", trigger: 'blur', required: true}]

      }
    }

  },

  components: {
    //计数器组件
    Input_num
  },
  mounted() {
    this.getList();
  }
  ,
  methods: {
    // 从子组件中接收input框中的数据，并更新父组件val值
    count_num(data) {
      this.val = data
    },


    formatter(row, column) {
      return row.address;
    },


    //操作
    handleEdit(index, row) {
      console.log(index, row);
    }
    ,
    selectById(row) {
      this.$http.get("/drug/selectById", {
        params: {
          drugid: row.drugid
        }
      }).then((res) => {
        this.dialogTableVisible = true
        this.drug = res.data.data
      })
    }
    ,
    update() {
      this.$refs.drug.validate(async valid => {
        if (!valid) return;
        this.$http.post("/drug/updataById", this.drug).then((res) => {
          this.dialogTableVisible = false;
          this.$message.success("修改成功");
          this.getList();
        })
      })
    },
    //模糊查询+分页
    getList() {
      this.$http.get("/drug/list", {
        params: {
          name: this.input,
          size: this.size,
          current: this.current
        }
      }).then((res) => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    }
    ,

    save() {
      this.$refs.form.validate(async valid => {
        if (!valid) return;
        this.$http.post("/drug/save", this.form).then((res) => {
          this.$message.success("添加成功");
          this.dialogFormVisible = false;
          this.getList();
        })
      })
    },
//分页
    handleSizeChange(val) {
      this.size = val
      this.getList();
      // console.log(`每页 ${val} 条`);
    }
    ,
    handleCurrentChange(val) {
      this.current = val
      this.getList();
      // console.log(`当前页: ${val}`);
    }


  }
  ,
  name: "DrugList"
}
</script>

<style scoped>

</style>