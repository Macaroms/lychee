<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>IP归属地查询</a-divider>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='12' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input
                  style="width: 100%"
                  v-model="ip"
                  placeholder="请输入IP地址"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-button type="primary" icon="copy" @click="search">查询</a-button>
              <a-button type="danger" icon="delete" style="margin-left: 8px" @click="reset">清空</a-button>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
    <a-card v-show="show" style='margin-top: 20px' :bordered='false' :loading="loading">
      <a-descriptions title="查询结果">
        <a-descriptions-item label="IP">
          {{result.text}}
        </a-descriptions-item>
        <a-descriptions-item label="地区">
          {{result.region}}
        </a-descriptions-item>
        <a-descriptions-item label="地区编码">
          {{result.areaCode}}
        </a-descriptions-item>
        <a-descriptions-item label="类型">
          {{result.type}}
        </a-descriptions-item>
        <a-descriptions-item label="拥有者">
          {{result.owner}}
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import {getAction} from '@/api/httpManager.js'

export default {
  name: 'jsonEditor',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      loading: false,
      ip: '',
      url: {
        ipData:'/api/text/ipData',
      },
      result: {},
      show: false
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
      this.ip = ''
    },
    search(){
      if (!this.ip || this.ip.length === 0){
        this.$message.warning("请输入IP地址")
        return
      }
      this.show = true
      this.loading = true
      getAction(this.url.ipData, {ip: this.ip}).then((res) => {
        if(res.code === 200){
          this.result = res.data
        }else {
          this.show = false
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.loading = false
      })
    }

  }
}
</script>
