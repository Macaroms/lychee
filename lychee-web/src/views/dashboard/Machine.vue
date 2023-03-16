<template>
  <page-header-wrapper :title='false'>
    <div>
      <!-- 系统信息  Java信息-->
      <a-row :gutter='24'>
        <a-col :md='12' :sm='24'>
          <a-card :loading='loading' title='系统信息' style='margin-bottom: 20px' :bordered='false'>
            <table class='sysInfo_table'>
              <tr>
                <td class='sysInfo_td'>系统名称：</td>
                <td class='sysInfo_td'>{{ this.sysOsInfo.osName }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>系统架构：</td>
                <td class='sysInfo_td'>{{ this.sysOsInfo.osArch }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>系统版本：</td>
                <td class='sysInfo_td'>{{ this.sysOsInfo.osVersion }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>主机名称：</td>
                <td class='sysInfo_td'>{{ this.sysOsInfo.osHostName }}</td>
              </tr>
              <tr>
                <td>主机IP地址：</td>
                <td>{{ this.sysOsInfo.osHostAddress }}</td>
              </tr>
            </table>
          </a-card>
        </a-col>
        <a-col :md='12' :sm='24'>
          <a-card :loading='loading' title='Java信息' style='margin-bottom: 20px'>
            <table class='sysInfo_table'>
              <tr>
                <td class='sysInfo_td'>虚拟机名称：</td>
                <td class='sysInfo_td'>{{ this.sysJavaInfo.jvmName }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>虚拟机版本：</td>
                <td class='sysInfo_td'>{{ this.sysJavaInfo.jvmVersion }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>虚拟机供应商：</td>
                <td class='sysInfo_td'>{{ this.sysJavaInfo.jvmVendor }}</td>
              </tr>
              <tr>
                <td class='sysInfo_td'>java名称：</td>
                <td class='sysInfo_td'>{{ this.sysJavaInfo.javaName }}</td>
              </tr>
              <tr>
                <td>java版本：</td>
                <td>{{ this.sysJavaInfo.javaVersion }}</td>
              </tr>
            </table>
          </a-card>
        </a-col>
      </a-row>
      <a-card :loading='loading' title='JVM内存信息'>
        <table class='sysInfo_table'>
          <tr>
            <td class='sysInfo_td'>最大内存：</td>
            <td class='sysInfo_td'>{{ this.sysJvmMemInfo.jvmMaxMemory }}</td>
            <td class='sysInfo_td'>可用内存：</td>
            <td class='sysInfo_td'>{{ this.sysJvmMemInfo.jvmUsableMemory }}</td>
          </tr>
          <tr>
            <td class='sysInfo_td'>总内存：</td>
            <td class='sysInfo_td'>{{ this.sysJvmMemInfo.jvmTotalMemory }}</td>
            <td class='sysInfo_td'>已使用内存：</td>
            <td class='sysInfo_td'>{{ this.sysJvmMemInfo.jvmUsedMemory }}</td>
          </tr>
          <tr class='sysInfo_tr'>
            <td>空余内存：</td>
            <td>{{ this.sysJvmMemInfo.jvmFreeMemory }}</td>
            <td>使用率：</td>
            <td>{{ this.sysJvmMemInfo.jvmMemoryUsedRate }}</td>
          </tr>
        </table>
      </a-card>
    </div>
  </page-header-wrapper>
</template>

<script>
import moment from 'moment'
import {
  ChartCard,
  MiniArea,
  MiniBar,
  MiniProgress,
  RankList,
  Bar,
  Trend,
  NumberInfo,
  MiniSmoothArea
} from '@/components'
import { baseMixin } from '@/store/app-mixin'

// const barData = []
// const barData2 = []
// for (let i = 0; i < 12; i += 1) {
//   barData.push({
//     x: `${i + 1}月`,
//     y: Math.floor(Math.random() * 1000) + 200
//   })
//   barData2.push({
//     x: `${i + 1}月`,
//     y: Math.floor(Math.random() * 1000) + 200
//   })
// }
//
// const rankList = []
// for (let i = 0; i < 7; i++) {
//   rankList.push({
//     name: '白鹭岛 ' + (i + 1) + ' 号店',
//     total: 1234.56 - i * 100
//   })
// }
//
// const searchUserData = []
// for (let i = 0; i < 7; i++) {
//   searchUserData.push({
//     x: moment().add(i, 'days').format('YYYY-MM-DD'),
//     y: Math.ceil(Math.random() * 10)
//   })
// }
// const searchUserScale = [
//   {
//     dataKey: 'x',
//     alias: '时间'
//   },
//   {
//     dataKey: 'y',
//     alias: '用户数',
//     min: 0,
//     max: 10
//   }]
//
// const searchData = []
// for (let i = 0; i < 50; i += 1) {
//   searchData.push({
//     index: i + 1,
//     keyword: `搜索关键词-${i}`,
//     count: Math.floor(Math.random() * 1000),
//     range: Math.floor(Math.random() * 100),
//     status: Math.floor((Math.random() * 10) % 2)
//   })
// }
//
// const DataSet = require('@antv/data-set')
//
// const sourceData = [
//   { item: '家用电器', count: 32.2 },
//   { item: '食用酒水', count: 21 },
//   { item: '个护健康', count: 17 },
//   { item: '服饰箱包', count: 13 },
//   { item: '母婴产品', count: 9 },
//   { item: '其他', count: 7.8 }
// ]
//
// const pieScale = [{
//   dataKey: 'percent',
//   min: 0,
//   formatter: '.0%'
// }]
//
// const dv = new DataSet.View().source(sourceData)
// dv.transform({
//   type: 'percent',
//   field: 'count',
//   dimension: 'item',
//   as: 'percent'
// })
// const pieData = dv.rows

export default {
  data() {
    return {
      loading: true,
      sysOsInfo: [],
      sysJavaInfo: [],
      sysJvmMemInfo: []
    }
  },
  // 进页面加载
  created() {
    this.loadDataList()
  },
  methods: {
    // 加载数据方法
    loadDataList() {
      // sysMachineQuery().then((res) => {
      //   this.loading = false
      //   this.sysOsInfo = res.data.sysOsInfo
      //   this.sysJavaInfo = res.data.sysJavaInfo
      //   this.sysJvmMemInfo = res.data.sysJvmMemInfo
      // })
      let result = '{\n' +
        '\t"success": true,\n' +
        '\t"code": 200,\n' +
        '\t"message": "请求成功",\n' +
        '\t"data": {\n' +
        '\t\t"sysOsInfo": {\n' +
        '\t\t\t"osName": "Linux",\n' +
        '\t\t\t"osArch": "amd64",\n' +
        '\t\t\t"osVersion": "3.10.0-1160.31.1.el7.x86_64",\n' +
        '\t\t\t"osHostName": "VM-24-11-centos",\n' +
        '\t\t\t"osHostAddress": "127.0.0.1"\n' +
        '\t\t},\n' +
        '\t\t"sysJavaInfo": {\n' +
        '\t\t\t"jvmName": "Java HotSpot(TM) 64-Bit Server VM",\n' +
        '\t\t\t"jvmVersion": "25.121-b13",\n' +
        '\t\t\t"jvmVendor": "Oracle Corporation",\n' +
        '\t\t\t"javaName": "Java(TM) SE Runtime Environment",\n' +
        '\t\t\t"javaVersion": "1.8.0_121-b13"\n' +
        '\t\t},\n' +
        '\t\t"sysJvmMemInfo": {\n' +
        '\t\t\t"jvmMaxMemory": "843 MB",\n' +
        '\t\t\t"jvmUsableMemory": "503.13 MB",\n' +
        '\t\t\t"jvmTotalMemory": "457 MB",\n' +
        '\t\t\t"jvmUsedMemory": "339.87 MB",\n' +
        '\t\t\t"jvmFreeMemory": "117.13 MB",\n' +
        '\t\t\t"jvmMemoryUsedRate": "74.37%"\n' +
        '\t\t}\n' +
        '\t}\n' +
        '}'
      let res = JSON.parse(result)
      this.loading = false
      this.sysOsInfo = res.data.sysOsInfo
      this.sysJavaInfo = res.data.sysJavaInfo
      this.sysJvmMemInfo = res.data.sysJvmMemInfo
    }
  }

}
</script>

<style lang='less'>
.sysInfo_table {
  width: 100%;
  min-height: 45px;
  line-height: 45px;
  text-align: center;
}

.sysInfo_td {
  border-bottom: 1px solid #e8e8e8;
}
</style>
