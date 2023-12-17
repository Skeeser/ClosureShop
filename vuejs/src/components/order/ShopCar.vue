<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>购物车</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.query"
            clearable
            @clear="getOrderList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getOrderList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="createOrderDialogVisible = true"
            >生成订单</el-button
          >
        </el-col>
      </el-row>

      <!-- 订单列表 -->
      <el-table :data="orderList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="商品名称" prop="goods_name"></el-table-column>
        <el-table-column label="商品单价" prop="goods_price"></el-table-column>
        <el-table-column label="购买数量" prop="buy_number"></el-table-column>
        <el-table-column label="总价格" prop="all_price"></el-table-column>
        <el-table-column label="操作" width="130px">
          <template slot-scope="scope">
            <!-- <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="editGoodsById(scope.row.buycar_id)"
            ></el-button> -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeGoodsById(scope.row.buycar_id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <!-- <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination> -->
    </el-card>

    <!-- 添加订单的对话框 -->
    <el-dialog
      title="添加订单"
      :visible.sync="createOrderDialogVisible"
      width="50%"
      @open="createOrderDialogOpened"
      @close="createOrderDialogClosed"
    >
      <!-- 内容主体 -->
      <el-form
        :model="createOrdersForm"
        ref="createOrdersFormRef"
        :rules="addOrdersFormRules"
        label-width="100px"
      >
        <el-form-item label="支付金额" prop="username">
          <el-input
            v-model="createOrdersForm.order_price"
            type="text"
            style="width: 200px"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="支付方式" prop="password">
          <el-select v-model="createOrdersForm.order_pay" placeholder="请选择">
            <el-option
              v-for="item in pay_options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="学号" prop="email">
          <el-input v-model="createOrdersForm.email"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="mobile">
          <el-input v-model="createOrdersForm.mobile"></el-input>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="createOrderDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createOrder">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import cityData from './citydata.js'

export default {
  data() {
    return {
      // 订单列表查询参数
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      total: 0,
      // 是否显示生成订单的窗口
      createOrderDialogVisible: false,
      // 订单列表
      orderList: [],
      createOrdersForm: {
        order_price: 0,
        order_pay: ''
      },
      // 修改地址对话框
      addressDialogVisible: false,
      // 验证规则
      addOrdersFormRules: {},
      addressForm: {
        address1: [],
        address2: ''
      },
      cityData,
      pay_options: [
        {
          value: '1',
          label: '支付宝'
        },
        {
          value: '2',
          label: '微信'
        },
        {
          value: '3',
          label: '银行卡'
        }
      ]
    }
  },
  created() {
    this.getOrderList()
  },
  methods: {
    async getOrderList() {
      const { data: res } = await this.$http.get('orders/goods', {
        params: this.queryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取购物车表失败！')
      }
      console.log(res)
      // this.total = res.data.total
      this.orderList = res.data
    },
    // 分页
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      this.getOrderList()
    },
    handleCurrentChange(newSize) {
      this.queryInfo.pagenum = newSize
      this.getOrderList()
    },
    showEditDialog() {
      this.addressDialogVisible = true
    },
    addressDialogClosed() {
      this.$refs.addressFormRef.resetFields()
    },

    async showProgressDialog() {
      // 供测试的物流单号：1106975712662
      const { data: res } = await this.$http.get('/kuaidi/1106975712662')
      if (res.meta.status !== 200) {
        return this.$message.error('获取物流进度失败!')
      }
      this.progressInfo = res.data
      this.progressDialogVisible = true
    },

    createOrderDialogOpened() {
      // this.getOrderList()

      this.createOrdersForm.order_price = 0
      // 结算金额
      this.orderList.forEach((item) => {
        this.createOrdersForm.order_price += item.all_price
      })
    },
    // 结算购物车
    createOrderDialogClosed() {
      this.$refs.createOrdersFormRef.resetFields()
    },
    // 生成新的订单
    async createOrder() {
      // post请求
      const { data: res } = await this.$http.post(
        'orders',
        this.createOrdersForm
      )
      if (res.meta.status !== 201) {
        this.$message.error('添加订单失败！')
        return
      }
      this.$message.success('添加订单成功！')
      // 隐藏添加订单框
      this.createOrderDialogVisible = false
      // 更新订单列表
      this.getOrderList()
    },
    // 删除购物车中的某个商品
    async removeGoodsById(id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该商品, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch((err) => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除！')
      }
      const { data: res } = await this.$http.delete('orders/goods/' + id)
      if (res.meta.status !== 204) {
        return this.$message.error('删除购物车商品失败！')
      }
      this.$message.success('删除购物车商品成功！')
      this.getOrderList()
    }
  }
}
</script>

<style lang="less" scoped>
.el-cascader {
  width: 100%;
}
</style>
