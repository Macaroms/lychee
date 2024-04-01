<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>Python</a-divider>
              <common-editor
                :value="jsContent"
                language="python"
                @input="changeTextarea"
                style="height: 80vh"
                ref='common_editor'
              ></common-editor>
            </a-col>
            <a-col :lg='2' :md='2' :sm='2' style='text-align: center;margin-top: 200px'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='exec' type='primary'>
                  <a-icon type="caret-right" />
                  {{ $t('editor.execute') }}
                </a-button>
              </a-form-item>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-button @click='reset' type='danger'>
                  <a-icon type="undo" />
                  {{ $t('editor.reset') }}
                </a-button>
              </a-form-item>
            </a-col>
            <a-col :lg='11' :md='11' :sm='11'>
              <a-divider orientation='left'>Result</a-divider>
                <a-row>
                  <a-col :lg='12' :md='12' :sm='12'>
                    <a-form-item >
                      <a-input style="width: 95%" v-model="methodName" :placeholder="$t('editor.method-name')" />
                    </a-form-item>
                  </a-col>
                  <a-col :lg='12' :md='12' :sm='12'>
                    <a-form-item >
                      <a-input style="width: 95%" v-model="methodArgs" :placeholder="$t('editor.args')" />
                    </a-form-item>
                  </a-col>
                </a-row>
              <a-row>
              <a-form-item
                :labelCol='labelCol'
                :wrapperCol='wrapperCol'
              >
                <a-textarea
                  style="height: calc(80vh - 130px);background-color: #0c1021;color: #8da6ce"
                  read-only
                  v-model='result'
                />
              </a-form-item>
              </a-row>
            </a-col>
          </a-row>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { postAction } from '@/api/httpManager.js'
import CommonEditor from '.././components/CommonEditor'

export default {
  name: 'shellEditor',
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
        execJsScript: '/api/text/execJsScript'
      },
      result: '',
      jsContent: 'def main(cmd):\n' +
        '  return cmd',
      methodName: 'main',
      methodArgs: 'wei, 2',
    }
  },
  methods: {
    reset() {
      this.confirmLoading = true
      this.$refs.common_editor.setCodeContent('')
      this.jsContent = ''
      this.result = ''
      this.confirmLoading = false
    },
    exec() {
      // this.confirmLoading = true
      // let arr = this.methodArgs.split(',')
      // let args = []
      // for(let a of arr){
      //   args.push(a.trim())
      // }
      // postAction(
      //   this.url.execJsScript,
      //   {
      //     code: this.jsContent,
      //     method: this.methodName,
      //     args: args
      //   }
      // ).then((res) => {
      //   if (res.code === 200) {
      //     this.result = res.data
      //   } else {
      //     this.$message.error(res.message)
      //   }
      // }).finally(() => {
      //   this.confirmLoading = false
      // })
    },
    changeTextarea(val) {
      this.jsContent = val
    }
  }
}
</script>
