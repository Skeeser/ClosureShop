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
        <el-table-column label="购买数量" width="140px"
          ><el-input-number
            prop="buy_number"
            :min="1"
            :max="10"
          ></el-input-number>
        </el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button
              type="warning"
              icon="iconfont icon-shangpin"
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
      //   console.log(this.goodsList)
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
    addOrderFormByScopeRow(scoperow) {
      this.addOrderForm.goods_id = scoperow.goods_id
      this.addOrderForm.buy_number = scoperow.buy_number
      this.addOrderForm.goods_price = scoperow.goods_price

      // 添加购物车
      const { data: res } = this.$http.post('orders/goods', this.addOrderForm)
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
