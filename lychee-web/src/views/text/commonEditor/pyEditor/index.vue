<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-row class='form-row' :gutter='24'>
          <a-col :lg='11' :md='11' :sm='11'>
            <a-divider orientation='left'>Python</a-divider>
            <common-editor
              :value="pyCode"
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
            <div class="common-editor" style="height: 80vh;">
              <a-textarea
                style="height: 90%; background-color: #0c1021;color: #8da6ce"
                read-only
                v-model='result'
              />
            </div>
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
        execPyScript: '/api/text/execPyScript'
      },
      result: '',
      pyCode: "def main(name):\n" +
        "  print('Hello from Python! ' + name)\n" +
        "  \n" +
        "main('jiangwei')",
    }
  },
  methods: {
    reset() {
      this.confirmLoading = true
      this.$refs.common_editor.setCodeContent('')
      this.pyCode = ''
      this.result = ''
      this.confirmLoading = false
    },
    exec() {
      this.confirmLoading = true
      postAction(
        this.url.execPyScript,
        {
          code: this.pyCode,
        }
      ).then((res) => {
        if (res.code === 200) {
          this.result = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    changeTextarea(val) {
      this.pyCode = val
    }
  }
}
</script>
<style lang="less">
.common-editor {
  width: 100%;
  height: 100%;
  .CodeMirror {
    // color: #ccc;
    direction: ltr;
    line-height: 20px;
    width: 100%;
    height: 90%;
    // background-color: #000;
  }

  .CodeMirror-hints {
    z-index: 9999 !important;
  }
  .custom-class .CodeMirror {
    width: 100%;
  }
}
.CodeMirror-hints {
  z-index: 1000;
}
</style>