<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form' style='height: 400px'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>JavaScript</a-divider>
              <common-editor
                :value="jsContent.jsText"
                language="javascript"
                @input="changeTextarea"
                style="height: 80vh"
              ></common-editor>
            </a-col>
            <a-col :lg='2' :md='2' :sm='2' style='text-align: center;margin-top: 200px'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='exec' type='primary'>
                  <a-icon type="caret-right"/>执行
                </a-button>
              </a-form-item>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='reset' type='danger'>
                  <a-icon type="undo" />清空
                </a-button>
              </a-form-item>
            </a-col>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>Result</a-divider>
              <a-form-item
                :labelCol='labelCol'
                :wrapperCol='wrapperCol'
              >
                <a-textarea
                  :rows='30'
                  read-only
                  v-model='result'
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
import CommonEditor from '.././components/CommonEditor'

export default {
  name: 'jsEditor',
  components: { CommonEditor },
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
      result: '',
      jsContent: {
        jsText: 'var name = "jiangwei"\nconsole.log(name)'
      }
    }
  },
  methods: {
    reset() {
      console.log("asdasda")
      this.jsContent.jsText = ''
      this.result = ''
    },
    exec() {
    },
    getTargetText(source, target, text) {
      // this.confirmLoading = true
      // postAction(
      //   this.url.propsConvert,
      //   {
      //     source: source,
      //     target: target,
      //     text: text
      //   }
      // ).then((res) => {
      //   if (res.code === 200) {
      //     let text = res.data
      //     if(target === 'prop'){
      //       this.properties = text
      //     } else if(target === 'yaml'){
      //       this.yaml = text
      //     }
      //   } else {
      //     this.$message.error(res.message)
      //   }
      // }).finally(() => {
      //   this.confirmLoading = false
      // })
    },
    changeTextarea(val) {
      this.jsContent.jsText = val
    }
  }
}
</script>
