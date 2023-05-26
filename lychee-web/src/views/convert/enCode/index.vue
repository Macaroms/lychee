<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-tabs default-active-key="base64" @change="callback">
          <a-tab-pane key="base64" tab="base64">
          </a-tab-pane>
          <a-tab-pane key="md5" tab="md5">
          </a-tab-pane>
          <a-tab-pane key="url" tab="URL">
          </a-tab-pane>
        </a-tabs>
        <a-form :form='form'>
          <a-form-item
            :labelCol='labelCol'
            :wrapperCol='wrapperCol'
          >
            <a-textarea
              :rows='8'
              :placeholder="$t('sys.placeholder')"
              v-model='context'
              allow-clear
            />
          </a-form-item>
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <a-space>
              <a-button v-if="codeType==='base64'" @click='base64Encode' type="primary">
                编码
              </a-button>
              <a-button v-if="codeType==='base64'" @click='base64Decode' type="primary">
                解码
              </a-button>
              <a-button v-if="codeType==='md5'" @click='md5Encode' type="primary">
                编码(32位)
              </a-button>
              <a-button v-if="codeType==='md5'" @click='md5Decode' type="primary">
                解码
              </a-button>
              <a-button v-if="codeType==='url'" @click='uriCoder(0)' type="primary">
                编码
              </a-button>
              <a-button v-if="codeType==='url'" @click='uriCoder(1)' type="primary">
                解码
              </a-button>
              <a-button @click='onCopy'>
                {{ $t('text.character-conversion.copy') }}
              </a-button>
              <a-button @click='reset' type='danger'>
                {{ $t('text.character-conversion.reset') }}
              </a-button>
            </a-space>
          </a-form-item>
          <a-form-item
            :labelCol='labelCol'
            :wrapperCol='wrapperCol'
          >
            <a-textarea
              :rows='8'
              v-model='result'
              readonly="true"
            />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import {postAction} from '@/api/httpManager.js'

export default {
  name: 'CharacterConversion',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      context: '',
      result: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      codeType: 'base64',
      url: {
        base64Encode:'/api/text/base64Encode',
        base64Decode:'/api/text/base64Decode',
        md5Encode:'/api/text/md5Encode',
        urlCoder:'/api/text/urlCoder',
      }
    }
  },
  methods: {
    callback(key) {
      this.reset()
      this.codeType = key
    },
    // 复制
    onCopy() {
      this.$copyText(this.result).then(() => {
        this.$message.success('已成功复制到剪切板')
      }).catch(() => {
        this.$message.error('未检测到内容')
      })
    },
    // 清空
    reset() {
      this.context = ''
      this.result = ''
    },
    base64Encode(){
      this.confirmLoading = true
      postAction(
        this.url.base64Encode,
        {
          src: this.context,
        }
      ).then((res) => {
        if(res.success){
          this.result = res.data
        }else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    base64Decode(){
      this.confirmLoading = true
      postAction(
        this.url.base64Decode,
        {
          src: this.context,
        }
      ).then((res) => {
        if(res.success){
          this.result = res.data
        }else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    md5Encode(){
      this.confirmLoading = true
      postAction(
        this.url.md5Encode,
        {
          src: this.context,
        }
      ).then((res) => {
        if(res.success){
          this.result = res.data
        }else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    md5Decode(){
      this.$message.warning("不会解")
    },
    uriCoder(type){
      this.confirmLoading = true
      postAction(
        this.url.urlCoder,
        {
          src: this.context,
          type: type
        }
      ).then((res) => {
        if(res.success){
          this.result = res.data
        }else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
  }
}
</script>
