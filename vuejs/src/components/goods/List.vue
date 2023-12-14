<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
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
        <el-col :span="4">
          <el-button type="primary" @click="goAddPage">添加商品</el-button>
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
        <el-table-column label="创建时间" prop="add_time" width="140px">
          <template slot-scope="scope">{{
            (scope.row.add_time * 1000) | dataFormat
          }}</template>
        </el-table-column>
        <el-table-column label="操作" width="130px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="getGoodsById(scope.row.goods_id)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeById(scope.row.goods_id)"
            ></el-button>
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

    <!-- 修改商品的对话框 -->
    <el-dialog
      title="修改商品信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体 -->
      <el-form
        :model="editGoodsForm"
        ref="editGoodsFormRef"
        :rules="editGoodsFormRules"
        label-width="70px"
      >
        <el-form-item label="商品名称" prop="goods_name">
          <el-input v-model="editGoodsForm.goods_name"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="goods_price">
          <el-input
            v-model="editGoodsForm.goods_price"
            type="number"
          ></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="goods_number">
          <el-input
            v-model="editGoodsForm.goods_number"
            type="number"
          ></el-input>
        </el-form-item>
        <el-form-item label="重量" prop="goods_weight">
          <el-input
            v-model="editGoodsForm.goods_weight"
            type="number"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item label="介绍" prop="goods_introduce">
          <el-input v-model="editGoodsForm.goods_introduce"></el-input>
        </el-form-item>
        <el-form-item label="图片路径" prop="pics">
          <el-input v-model="editGoodsForm.pics" disabled></el-input>
        </el-form-item>
        <el-form-item label="商品参数" prop="attrs">
          <el-input v-model="editGoodsForm.attrs" disabled></el-input>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editGoods">确 定</el-button>
      </span>
    </el-dialog>
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
      editGoodsFormRules: {}
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
    // 通过Id删除商品
    async removeById(id) {
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
      const { data: res } = await this.$http.delete('goods/' + id)
      if (res.meta.status !== 204) {
        return this.$message.error('删除商品失败！')
      }
      this.$message.success('删除商品成功！')
      this.getGoodsList()
    },
    async getGoodsById(id) {
      // 先查询商品信息
      const { data: res } = await this.$http.get('goods/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('查询用户信息失败！')
      }
      this.editGoodsForm = res.data
      this.editDialogVisible = true
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editGoodsFormRef.resetFields()
    },
    editGoods() {
      // 提交请求前，表单预验证
      this.$refs.editGoodsFormRef.validate(async (valid) => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put(
          'goods/' + this.editGoodsForm.goods_id,
          {
            goods_name: this.editGoodsForm.goods_name,
            goods_price: Number(this.editGoodsForm.goods_price),
            goods_number: Number(this.editGoodsForm.goods_number),
            goods_weight: Number(this.editGoodsForm.goods_weight)
            // goods_introduce: this.editGoodsForm.goods_introduce,
            // pics: this.editGoodsForm.pics,
            // attrs: this.editGoodsForm.attrs
          }
        )
        if (res.meta.status !== 200) {
          this.$message.error('更新商品信息失败！')
          return
        }
        // 隐藏添加商品对话框
        this.editDialogVisible = false
        this.$message.success('更新商品信息成功！')
        this.getGoodsList()
      })
    },
    goAddPage() {
      this.$router.push('/goods/add')
    }
  }
}
</script>

<style lang="less" scoped></style>
