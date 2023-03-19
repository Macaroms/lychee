<template>
  <a-spin :spinning='confirmLoading'>
    <a-form :form='form'>
      <a-form-item :labelCol='labelCol' :wrapperCol='wrapperCol'>
        <a-textarea
          readonly
          v-model='xmlText'
          :rows='15'
        />
      </a-form-item>
      <span>提取结果长度：{{ size }}</span>
    </a-form>
  </a-spin>
</template>

<script>
const fields = ['title', 'startAt', 'owner', 'description']
import {postAction} from '@/api/httpManager.js'

export default {
  name: 'TaskForm',
  props: {
    record: {
      type: Object,
      default: {
        text: '',
        xmlPath: ''
      }
    }
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      form: this.$form.createForm(this),
      confirmLoading: false,
      url: {
        pickTextByPath: '/api/text/pickTextByPath',
      },
      xmlText: '',
      size: 0
    }
  },
  mounted() {
    this.pickTextByPath(this.record.xmlPath, this.record.text)
  },
  methods: {
    onOk() {
      this.xmlText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    },
    onCancel() {
      this.xmlText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    },
    pickTextByPath(xmlPath, src) {
      this.confirmLoading = true
      postAction(
        this.url.pickTextByPath,
        {
          type: "xml",
          src: src,
          path: xmlPath
        }
      ).then((res) => {
        if (res.success) {
          this.size = res.data.size
          this.formatText(res.data.text)
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    formatText(xmlText){
      if(this.size > 0){
        let display = ''
        let jsonArr = JSON.parse(xmlText)
        for(let a of jsonArr){
          display += a + '\n'
        }
        this.xmlText = display
      } else {
        this.xmlText = xmlText
      }
    }
  }
}
</script>
