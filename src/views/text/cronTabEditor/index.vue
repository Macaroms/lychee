<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>Cron表达式</a-divider>
<!--      <a-button @click="test">啊啊</a-button>-->
      <a-spin :spinning='confirmLoading'>
        <a-form :form="form">
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
              >
                <a-cron ref="innerVueCron" v-decorator="['cronExpression', {'initialValue':'0 0 0 2 * ?',rules:
          [{ message: '请输入cron表达式!' }]}]" @change="setCorn"></a-cron>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-divider orientation='left'>对照表</a-divider>
      <!--      <a-table-->
      <!--        :columns="columns"-->
      <!--        :data-source="data"-->
      <!--        rowKey="zero"-->
      <!--        :pagination="false"-->
      <!--        bordered-->
      <!--      >-->
      <!--        <template slot="name" slot-scope="text">-->
      <!--          <a>{{ text }}</a>-->
      <!--        </template>-->
      <!--      </a-table>-->
    </a-card>
    <!--    <a-card style='margin-top: 20px' :bordered='false'>-->
    <!--      <a-descriptions>-->
    <!--        <a-descriptions-item label='JavaScript' :span='3'>-->
    <!--          {{ $t('text.radix-convert.desc') }} : let result = parseInt(num + '', m).toString(n); ( 0 <= m,n <= 36 )-->
    <!--        </a-descriptions-item>-->
    <!--      </a-descriptions>-->
    <!--    </a-card>-->
  </page-header-wrapper>
</template>

<script>
import ACron from "@/views/text/cronTabEditor/components";

export default {
  name: 'cronTabEditor',
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
      cron: ''
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
    // 复制
    onCopy() {
      this.$copyText(this.rmbStr).then(() => {
        this.$message.success('已成功复制到剪切板')
      }).catch(() => {
        this.$message.error('未检测到内容')
      })
    },
    setCorn(data) {
      if (data !== undefined) {
        this.$nextTick(() => {
          this.form.setFieldsValue({cronExpression: data})
        })
      }
    },
    test(){
      console.log(this.$refs.innerVueCron.getCron())
    }
  }
}
</script>
