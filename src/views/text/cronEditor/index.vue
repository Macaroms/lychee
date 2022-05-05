<template>
  <page-header-wrapper :title='false'>

    <a-card style='margin-top: 20px' :bordered='false'>
      <a-row :gutter='24'>
        <a-col :md='12' :sm='24'>
          <a-divider orientation='left'>Cron表达式</a-divider>
          <a-form :form="form">
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                >
                  <a-cron ref="innerVueCron"></a-cron>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
        <a-col :md='12' :sm='24'>
          <a-divider orientation='left'>查看最近执行时间</a-divider>
          <a-spin :spinning='confirmLoading'>
            <a-form layout="inline" :form="form">
              <a-form-item label="最近执行次数">
                <a-input-number v-model="times" :min="0" :max="50"/>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" @click="getNextExecTime">
                  查看
                </a-button>
              </a-form-item>
            </a-form>
            <br/>
            <a-list size="small" :data-source="cronData">
              <a-list-item slot="renderItem" slot-scope="item, index">
                {{ item }}
              </a-list-item>
            </a-list>
          </a-spin>
        </a-col>
      </a-row>
      <a-divider orientation='left'>各字段的含义</a-divider>
      <a-table
        :columns="columns"
        :data-source="data"
        rowKey="index"
        :pagination="false"
        bordered
      >
        <template slot="name" slot-scope="text">
          <a>{{ text }}</a>
        </template>
      </a-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import ACron from "@/views/text/cronEditor/components";
import {getAction} from '@/api/httpManager.js'

export default {
  name: 'cronEditor',
  components: {
    ACron
  },
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      labelCol: {
        xs: {span: 24},
        sm: {span: 5}
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 24}
      },
      url: {
        getNextExecTime: '/text/getNextExecTime'
      },
      columns: [
        {
          title: '位数',
          dataIndex: 'index',
          key: 'index',
          align: 'center'
        },
        {
          title: '含义',
          dataIndex: 'desc',
          key: 'desc',
          align: 'center'
        },
        {
          title: '允许值',
          dataIndex: 'value',
          key: 'value',
          align: 'center'
        },
        {
          title: '允许的特殊字符',
          dataIndex: 'characters',
          key: 'characters',
          align: 'center'
        }
      ],
      data: [
        {
          index: '1',
          desc: '秒（Seconds）',
          value: '0~59的整数',
          characters: ', - * / 四个字符'
        },
        {
          index: '2',
          desc: '分（Minutes）',
          value: '0~59的整数',
          characters: ', - * / 四个字符'
        },
        {
          index: '3',
          desc: '小时（Hours）',
          value: '0~23的整数',
          characters: ', - * / 四个字符'
        },
        {
          index: '4',
          desc: '日期（DayOfMonth）',
          value: '1~31的整数（但是你需要考虑你月的天数）',
          characters: ',- * ? / L W C 八个字符'
        },
        {
          index: '5',
          desc: '月份（Month）',
          value: '1~12的整数或者 JAN-DEC',
          characters: ', - * / 四个字符'
        },
        {
          index: '6',
          desc: '星期（DayOfWeek）',
          value: '0~6的整数或者 SUN-SAT （0=SUN）',
          characters: ', - * ? / L C # 八个字符'
        },
        {
          index: '7',
          desc: '年(可选，留空)（Year）',
          value: '1970~2099',
          characters: ', - * / 四个字符s'
        }
      ],
      cronData: [],
      times: 5
    }
  },
  computed: {},
  created() {
  },
  mounted() {
  },
  methods: {
    // 清空
    reset() {
      this.rmbNum = ''
      this.rmbStr = '零元整'
    },
    // setCorn(data) {
    //   if (data !== undefined) {
    //     this.$nextTick(() => {
    //       this.form.setFieldsValue({cronExpression: data})
    //     })
    //   }
    // },
    test() {
      console.log(this.$refs.innerVueCron.getCron())
    },
    getNextExecTime() {
      this.confirmLoading = true
      getAction(
        this.url.getNextExecTime,
        {
          cronExpression: this.$refs.innerVueCron.getCron(),
          numTimes: this.times
        }
      ).then((res) => {
        this.cronData = res.data
      }).finally(() => {
        this.confirmLoading = false
      })
    }
  }
}
</script>
