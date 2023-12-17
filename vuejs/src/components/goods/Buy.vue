<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品平台</el-breadcrumb-item>
      <el-breadcrumb-item>商品购买</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.query"
            clearable
            @clear="getGoodsList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getGoodsList"
            ></el-button>
          </el-input>
        </el-col>
      </el-row>
      <!-- 表格数据 -->
      <el-table :data="goodsList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="商品名称" prop="goods_name"></el-table-column>
        <el-table-column
          label="商品价格(元)"
          prop="goods_price"
          width="100px"
        ></el-table-column>
        <el-table-column
          label="商品重量"
          prop="goods_weight"
          width="70px"
        ></el-table-column>
        <el-table-column
          label="商品数量"
          prop="goods_number"
          width="70px"
        ></el-table-column>
        <el-table-column label="购买数量" width="160px">
          <template slot-scope="scope">
            <el-input-number
              v-model="buyNumberForm[scope.row.goods_id]"
              :min="1"
              :max="scope.row.goods_number"
              size="mini"
              default-value="1"
              @change="handleInputChange(scope.row)"
            ></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button
              type="warning"
              icon="el-icon-shopping-cart-2"
              @click="addOrderFormByScopeRow(scope.row)"
              >添加到购物车</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      // 商品列表
      goodsList: [],
      // 购买数量
      buyNumberForm: {},
      // 商品总数
      total: 0,
      editGoodsForm: {},
      editDialogVisible: false,
      // 编辑商品的表单校验规则
      editGoodsFormRules: {},
      // 添加商品订单列表
      addOrderForm: {}
    }
  },
  created() {
    this.getGoodsList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    async getGoodsList() {
      const { data: res } = await this.$http.get('goods', {
        params: this.queryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品列表失败！')
      }
      this.goodsList = res.data.goods
      // 添加默认的购买数为一
      // console.log(this.buyNumberForm)
      // 清空buy number的form
      this.buyNumberForm = {}
      // 初始化buynumber
      this.goodsList.forEach((list) => {
        // $set 方法会确保 buyNumberForm 对象中的 list.goods_id 属性是响应式的。这对于Vue.js的数据绑定非常重要，
        // 因为直接添加的属性可能不会触发视图的更新，而 $set 方法可以解决这个问题。
        // this.buyNumberForm.[list.goods_id] = 1  不对
        this.$set(this.buyNumberForm, list.goods_id, 1)
      })
      // console.log(this.buyNumberForm)

      this.total = res.data.total
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      this.getGoodsList()
    },
    handleCurrentChange(newSize) {
      this.queryInfo.pagenum = newSize
      this.getGoodsList()
    },
    handleInputChange(scoperow) {
      // console.log(this.buyNumberForm)
      // console.log(scoperow)
    },
    async addOrderFormByScopeRow(scoperow) {
      this.addOrderForm.goods_id = scoperow.goods_id
      this.addOrderForm.buy_number = this.buyNumberForm[scoperow.goods_id]
      // this.addOrderForm.buy_number = scoperow.buy_number
      this.addOrderForm.goods_price = scoperow.goods_price

      console.log(this.addOrderForm)

      // 添加购物车
      const { data: res } = await this.$http.post(
        'orders/goods',
        this.addOrderForm
      )

      console.log(res)
      if (res.meta.status !== 200) {
        return this.$message.error('加入购物车失败！')
      }
      // 将返回的订单号
      this.$message.success('加入购物车成功！')
      this.getGoodsList()
    },
    buyGoodsById(id) {
      // 购买商品
      const { data: res } = this.$http.get('goods/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('查询用户信息失败！')
      }
      this.editGoodsForm = res.data
      this.editDialogVisible = true
    }
  }
}
</script>

<style lang="less" scoped></style>
