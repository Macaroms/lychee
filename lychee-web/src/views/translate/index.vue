<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-form :form='form'>
        <a-row class='form-row' :gutter='24'>
          <a-col :lg='3' :md='3' :sm='24'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-select
                show-search
                placeholder='选择源语言'
                option-filter-prop='children'
                :filter-option='filterOption'
                v-model='from'
              >
                <a-select-option v-for='item in srcLanList' :key='item.value'>
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg='1' :md='1' :sm='24' style='text-align: center'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-icon type='swap' />
            </a-form-item>
          </a-col>
          <a-col :lg='3' :md='3' :sm='24'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-select
                show-search
                placeholder='选择译文语言'
                option-filter-prop='children'
                :filter-option='filterOption'
                v-model='to'
              >
                <a-select-option v-for='item in srcLanList' :key='item.value'>
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg='6' :md='6' :sm='24'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-space>
                <a-button @click='translate' type='primary'>
                  翻译
                </a-button>
                <a-button @click='reset' type='danger'>
                  清空
                </a-button>
              </a-space>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item :labelCol='labelCol' :wrapperCol='wrapperCol'>
          <a-textarea
            :rows='12'
            :placeholder="$t('sys.placeholder')"
            v-model='context'
            allow-clear
          />
        </a-form-item>
        <a-spin :spinning='confirmLoading'>
          <a-form-item :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <a-textarea
              :rows='12'
              v-model='result'
              readOnly
            />
          </a-form-item>
        </a-spin>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { postAction } from '@/api/httpManager.js'

export default {
  name: 'translate',
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
        translate: '/api/remote/translate'
      },
      srcLanList: [
        { name: '自动检测', value: 'auto' },
        { name: '中文', value: 'zh' },
        { name: '英语', value: 'en' },
        { name: '粤语', value: 'yue' },
        { name: '文言文', value: 'wyw' },
        { name: '日语', value: 'jp' },
        { name: '韩语', value: 'kor' },
        { name: '法语', value: 'fra' },
        { name: '西班牙语', value: 'spa' },
        { name: '泰语', value: 'th' },
        { name: '阿拉伯语', value: 'ara' },
        { name: '俄语', value: 'ru' },
        { name: '葡萄牙语', value: 'pt' },
        { name: '德语', value: 'de' },
        { name: '意大利语', value: 'it' },
        { name: '希腊语', value: 'el' },
        { name: '荷兰语', value: 'nl' },
        { name: '波兰语', value: 'pl' },
        { name: '保加利亚语', value: 'bul' },
        { name: '爱沙尼亚语', value: 'est' },
        { name: '丹麦语', value: 'dan' },
        { name: '芬兰语', value: 'fin' },
        { name: '捷克语', value: 'cs' },
        { name: '罗马尼亚语', value: 'rom' },
        { name: '斯洛文尼亚语', value: 'slo' },
        { name: '瑞典语', value: 'swe' },
        { name: '匈牙利语', value: 'hu' },
        { name: '繁体中文', value: 'cht' },
        { name: '越南语', value: 'vie' }
      ],
      from: 'auto',
      to: 'zh',
      context: '',
      result: ''
    }
  },
  methods: {
    // 清空
    reset() {
      this.context = ''
      this.result = ''
      this.from = 'auto'
      this.to = 'zh'
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    translate() {
      if (!this.context || this.context.length === 0) {
        this.$message.warning('未检测到内容')
        return
      }
      this.confirmLoading = true
      postAction(
        this.url.translate,
        {
          query: this.context,
          from: this.from,
          to: this.to === 'anto' ? 'zh' : this.to
        }
      ).then((res) => {
        if (res.code === 200) {
          this.result = res.data.dst
          this.from = res.data.from
          this.to = res.data.to
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
