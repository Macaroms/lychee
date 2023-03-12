<template>
  <a-form :form="form">
    <a-form-item
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
    >
      <a-textarea
        readonly
        v-model="jsonText"
        :auto-size="{ minRows: 10, maxRows: 50 }"
      />
    </a-form-item>
  </a-form>
</template>

<script>
const fields = ['title', 'startAt', 'owner', 'description']

export default {
  name: 'TaskForm',
  props: {
    record: {
      type: String,
      default: "{}"
    }
  },
  data () {
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
      jsonText: ''
    }
  },
  mounted () {
    let jsonObj = JSON.parse(this.record)
    this.jsonText = JSON.stringify(jsonObj, null, 4)
  },
  methods: {
    onOk () {
      this.jsonText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    },
    onCancel () {
      this.jsonText = ''
      return new Promise(resolve => {
        resolve(true)
      })
    },
  }
}
</script>
