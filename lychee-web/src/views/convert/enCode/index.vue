<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-tabs default-active-key='base64' @change='callback'>
          <a-tab-pane key='base64' tab='base64'>
          </a-tab-pane>
          <a-tab-pane key='md5' tab='md5'>
          </a-tab-pane>
          <a-tab-pane key='url' tab='url'>
          </a-tab-pane>
          <a-tab-pane key='unicode' tab='unicode'>
          </a-tab-pane>
        </a-tabs>
        <a-form :form='form'>
          <a-form-item
            :labelCol='labelCol'
            :wrapperCol='wrapperCol'
          >
            <a-textarea
              :rows='10'
              :placeholder="$t('sys.placeholder')"
              v-model='context'
              allow-clear
            />
          </a-form-item>
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <a-space v-if="codeType==='base64'">
              <a-button @click='base64Encode' type='primary'>
                编码
              </a-button>
              <a-button @click='base64Decode' type='primary'>
                解码
              </a-button>
            </a-space>
            <a-space v-if="codeType==='md5'">
              <a-button @click='md5Encode' type='primary'>
                编码(32位)
              </a-button>
              <a-button @click='md5Decode' type='primary'>
                解码
              </a-button>
            </a-space>
            <a-space v-if="codeType==='url'">
              <a-button @click='uriCoder(0)' type='primary'>
                编码
              </a-button>
              <a-button @click='uriCoder(1)' type='primary'>
                解码
              </a-button>
              <a-select
                show-search
                placeholder='选择编码格式'
                option-filter-prop='children'
                v-model='enc'
                style='width: 100px'
              >
                <a-select-option v-for='item in encList' :key='item'>
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-space>
            <a-space v-if="codeType==='unicode'">
              <a-button @click='uniCoder(0)' type='primary'>
                编码
              </a-button>
              <a-button @click='uniCoder(1)' type='primary'>
                解码
              </a-button>
            </a-space>
              <a-space style='margin-left: 8px'>
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
              :rows='10'
              v-model='result'
              readonly='true'
            />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { postAction } from '@/api/httpManager.js'

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
        base64Encode: '/api/text/base64Encode',
        base64Decode: '/api/text/base64Decode',
        md5Encode: '/api/text/md5Encode',
        urlCoder: '/api/text/urlCoder',
        unicode: '/api/text/unicode'
      },
      encList: ['UTF-8', 'UTF-16', 'UTF-16BE', 'UTF-16LE', 'GBK'],
      enc: 'UTF-8'
    }
  },
  methods: {
    callback(key) {
      this.result = ''
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
    base64Encode() {
      this.confirmLoading = true
      postAction(
        this.url.base64Encode,
        {
          src: this.context
        }
      ).then((res) => {
        if (res.success) {
          this.result = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    base64Decode() {
      this.confirmLoading = true
      postAction(
        this.url.base64Decode,
        {
          src: this.context
        }
      ).then((res) => {
        if (res.success) {
          this.result = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    md5Encode() {
      this.confirmLoading = true
      postAction(
        this.url.md5Encode,
        {
          src: this.context
        }
      ).then((res) => {
        if (res.success) {
          this.result = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    md5Decode() {
      this.$message.warning('不会解')
    },
    uriCoder(type) {
      this.confirmLoading = true
      postAction(
        this.url.urlCoder,
        {
          src: this.context,
          type: type,
          enc: this.enc
        }
      ).then((res) => {
        if (res.success) {
          this.result = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    uniCoder(type) {
      this.confirmLoading = true
      postAction(
        this.url.unicode,
        {
          src: this.context,
          type: type
        }
      ).then((res) => {
        if (res.success) {
          this.result = res.data
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
