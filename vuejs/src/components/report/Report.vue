<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据统计</el-breadcrumb-item>
      <el-breadcrumb-item>数据报表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <div style="display: flex; justify-content: center; align-items: center">
        <!-- 2.为Echarts准备一个Dom -->
        <div id="main" style="width: 750px; height: 400px"></div>
      </div>
    </el-card>
  </div>
</template>

<script>
// 1.导入echarts
import echarts from 'echarts'
// import _ from 'lodash'

export default {
  data() {
    return {}
  },
  created() {},
  // 此时,页面上的元素,已经被渲染完毕了
  async mounted() {
    // 3.基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'))
    const { data: res } = await this.$http.get('reports')
    if (res.meta.status !== 200) return this.$message('获取折线图数据失败!')
    console.log(res.data)
    var xData = []
    var yData = []
    res.data.forEach((element) => {
      xData.push(element.goods_name)
      yData.push(element.hot_number)
    })

    // 随机颜色数组
    var colorList = [] // 设置颜色的数组
    // 生成随机颜色并添加到color配置中
    for (let i = 0; i < xData.length; i++) {
      colorList.push(this.getRandomColor())
    }
    // console.log(colorList)

    // 4.准备数据项和配置项
    // 指定图表的配置项和数据
    var option = {
      title: {
        text: '商品的热度统计(前15热度)'
      },
      tooltip: {},
      legend: {
        data: ['商品名称']
      },
      xAxis: {
        // type: '商品名称',
        name: '商品名称',
        data: xData,
        axisLabel: {
          show: false // 将 x 轴标签隐藏
        }
      },
      yAxis: {},
      series: [
        {
          name: '热度',
          type: 'bar',
          data: yData,
          itemStyle: {
            normal: {
              color: (params) => {
                return colorList[params.dataIndex]
              }
            }
          }
        }
      ]
    }

    // 数据合并
    // const result = _.merge(res.data, this.options)
    // 5.展示数据
    myChart.setOption(option)
  },
  methods: {
    // 生成随机颜色的函数
    getRandomColor() {
      return '#' + Math.floor(Math.random() * 16777215).toString(16)
    }
  }
}
</script>

<style lang="less" scoped></style>
