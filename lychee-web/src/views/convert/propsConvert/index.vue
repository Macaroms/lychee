<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form' style='height: 400px'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>Properties</a-divider>
              <a-form-item
                :labelCol='labelCol'
                :wrapperCol='wrapperCol'
              >
                <a-textarea
                  :rows='20'
                  placeholder='请输入Properties'
                  v-model='properties'
                  allow-clear
                />
              </a-form-item>
            </a-col>
            <a-col :lg='2' :md='2' :sm='2' style='text-align: center;margin-top: 200px'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='convert(0)' type='primary'>
                  转换&gt;&gt;
                </a-button>
              </a-form-item>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='convert(1)' type='primary'>
                  &lt;&lt;转换
                </a-button>
              </a-form-item>
            </a-col>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>Yaml</a-divider>
              <a-form-item
                :labelCol='labelCol'
                :wrapperCol='wrapperCol'
              >
                <a-textarea
                  :rows='20'
                  placeholder='请输入Yaml'
                  v-model='yaml'
                  allow-clear
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { postAction } from '@/api/httpManager.js'

export default {
  name: 'PropsConvert',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      url: {
        propsConvert: '/api/text/propsConvert'
      },
      properties: '',
      yaml: ''
    }
  },
  methods: {
    reset() {
      this.properties = ''
      this.yaml = ''
    },
    convert(flag) {
      let source
      let target
      let text
      if (flag === 0) {
        if(!this.properties || this.properties.length === 0){
          this.$message.warning('未检测到内容')
        }
        source = 'prop'
        target = 'yaml'
        text = this.properties
      } else if (flag === 1) {
        if(!this.yaml || this.yaml.length === 0){
          this.$message.warning('未检测到内容')
        }
        source = 'yaml'
        target = 'prop'
        text = this.yaml
      }
      this.getTargetText(source, target, text)
    },
    getTargetText(source, target, text) {
      this.confirmLoading = true
      postAction(
        this.url.propsConvert,
        {
          source: source,
          target: target,
          text: text
        }
      ).then((res) => {
        if (res.code === 200) {
          let text = res.data
          if(target === 'prop'){
            this.properties = text
          } else if(target === 'yaml'){
            this.yaml = text
          }
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    }
  }
}
</script>
