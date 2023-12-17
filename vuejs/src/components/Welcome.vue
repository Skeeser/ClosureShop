<template>
  <div>
    <!-- 2.为Echarts准备一个Dom -->
    <el-row type="flex" justify="center" align="middle">
      <el-col style="width: 30%">
        <div id="main" style="width: 1000px; height: 600px">
          <img :src="ShowImg" style="width: 50%" alt />
        </div>
      </el-col>
      <el-col style="width: 60%">
        <div style="text-align: center">
          <h1 style="font-size: 45px">欢迎来到Closure电商平台</h1>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 1.导入echarts
// import echarts from 'echarts'
import ShowImg from '@/assets/images/closure.jpg'

export default {
  data() {
    return {
      ShowImg
    }
  },
  // 此时,页面上的元素,已经被渲染完毕了
  async mounted() {}
}
</script>

<style>
div {
  margin: 0;
  padding: 0;
}
</style>
