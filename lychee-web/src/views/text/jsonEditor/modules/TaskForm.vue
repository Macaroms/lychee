<template>
  <a-spin :spinning='confirmLoading'>
    <a-form :form='form'>
      <a-form-item :labelCol='labelCol' :wrapperCol='wrapperCol'>
        <a-textarea
          readonly
          v-model='jsonText'
          :rows='15'
        />
      </a-form-item>
      <span>提取结果长度：{{ record.size }}</span>
    </a-form>
  </a-spin>
</template>

<script>
const fields = ['title', 'startAt', 'owner', 'description']

export default {
  name: 'TaskForm',
  props: {
    record: {
      type: Object,
      default: {
        length: 0,
        text: ''
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
      jsonText: '',
      size: 0
    }
  },
  mounted() {
    this.size = this.record.size
    let jsonObj = JSON.parse(this.record.text)
    this.jsonText = JSON.stringify(jsonObj, null, 4)
  },
  methods: {
    onOk() {
      this.jsonText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    },
    onCancel() {
      this.jsonText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    }
  }
}
</script>
