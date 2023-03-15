<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='12' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input
                  addon-before="/"
                  :addon-after="flag && flag.length>0?'-' + flag:'/'"
                  style="width: 100%"
                  v-model="regex"
                  placeholder="请输入正则表达式"
                />
              </a-form-item>
            </a-col>
            <a-col :lg='7' :md='7' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChange"/>
              </a-form-item>
            </a-col>
            <a-col :lg='5' :md='5' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-space>
                  <a-button type="primary" icon="reload" @click="getRegex">刷新</a-button>
                  <a-button type="danger" icon="delete" @click="reset">清空</a-button>
                </a-space>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='24' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-textarea
                  :rows='6'
                  placeholder="请输入待匹配文本"
                  v-model='text'
                  allow-clear
                  @change="getRegex"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <a-divider orientation='left'>共找到{{ matchNum }}处匹配</a-divider>
        <a-row class='form-row' :gutter='24'>
          <a-card :bordered='false'>
            <span style="word-wrap:break-word" v-html='resultText'></span>
          </a-card>
        </a-row>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='12' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input
                  addon-before="替换文本"
                  style="width: 100%"
                  v-model="replaceKey"
                  placeholder="请输入替换文本"
                />
              </a-form-item>
            </a-col>
            <a-col :lg='12' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-space>
                  <a-button type="primary" icon="swap" @click="replaceText">替换</a-button>
                </a-space>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='24' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-textarea
                  :rows='6'
                  v-model='replaceValue'
                  readOnly
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
<!--        <a-row class='form-row' :gutter='24'>-->
<!--          <a-card :bordered='false'>-->
<!--            <span style="word-wrap:break-word" v-html='replaceValue'></span>-->
<!--          </a-card>-->
<!--        </a-row>-->
      </a-spin>
    </a-card>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-table
        :columns="columns"
        :data-source="datasource"
        rowKey="desc"
        :pagination="false"
        bordered
      >
        <template slot="name" slot-scope="text">
          <a>{{ text }}</a>
        </template>
      </a-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>

export default {
  name: 'Regex',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      regex: '',
      text: '',
      resultText: '',
      matchNum: 0,
      plainOptions: ['-g:全局搜索', '-i:忽略大小写', '-m:多行模式', '-s:包含换行符'],
      checkedList: ['-g:全局搜索'],
      flag: 'g',
      columns: [
        {
          title: '含义',
          dataIndex: 'desc',
          key: 'desc',
          align: 'center'
        },
        {
          title: '正则表达式',
          dataIndex: 'regex',
          key: 'regex',
          align: 'center'
        }
      ],
      datasource: [
        {
          desc: '数字、字母或下划线',
          regex: '^\\w+$',
        },
        {
          desc: '校验E-mail地址',
          regex: '[a-zA-Z\\d]\\w+@[a-zA-Z\\d]+\\.(cn|com|com.cn|net|gov)+',
        },
        {
          desc: '校验大陆手机号码',
          regex: '^((13\\d)|(15\\d)|(18[1-9]))\\d{8}$',
        },
        {
          desc: '校验中文',
          regex: '^[\\u4e00-\\u9fa5]{0,}$',
        },
        {
          desc: '校验身份证号码',
          regex: '^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$',
        },
        {
          desc: '校验IPV4地址',
          regex: '\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b',
        },
      ],
      replaceKey: '',
      replaceValue: ''
    }
  },
  computed: {},
  created() {
  },
  mounted() {
  },
  methods: {
    // 清空
    reset() {
      this.regex = ''
      this.text = ''
      this.resultText = ''
      this.matchNum = 0
      this.flag = 'g'
      this.checkedList = ['-g:全局搜索']
    },
    getRegex() {
      if (this.regex && this.regex.length > 0) {
        let result = this.wrapText(this.text, this.regex)
        let regex = new RegExp(this.regex, this.flag)
        let matchArr = this.text.match(regex)
        if (matchArr) {
          this.matchNum = this.text.match(regex).length
        }
        this.resultText = result.join('')
      }
    },
    splitText(text, regex) {
      const regexp = new RegExp(regex, this.flag)
      return text.replace(/\t/g, '').replace(regexp, '\t$&\t').split(/\t/)
    },
    wrapText(text, keywords) {
      return this.splitText(text, keywords).map((substr, i) => {
        return i % 2 === 0
          ? substr
          : `<span style='background-color: #cff5ff;box-shadow: 0 0 0 1px #09f;border-radius: 4px'>${substr}</span>`
      })
    },
    onChange(checkedList) {
      let flag = ''
      if (checkedList && checkedList.length > 0) {
        for (let a of checkedList) {
          flag += a[1]
        }
      }
      this.flag = flag
      this.getRegex()
    },
    replaceText(){
      let regex = new RegExp(this.regex, this.flag)
      this.replaceValue = this.text.replace(regex, this.replaceKey)
    }

  }
}
</script>