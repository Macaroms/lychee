<template>
  <page-header-wrapper :title='false'>
    <a-card class='box-card' :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-form :form='form'>
        <a-row class='form-row' :gutter='24'>
          <a-col :lg='6' :md='6' :sm='6'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-select
                v-model='value_type'
                show-search
                placeholder='选择源语言'
                option-filter-prop='children'
                :filter-option='filterOption'
              >
                <a-select-option key='XML'>XML</a-select-option>
                <a-select-option key='HTML'>HTML</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg='18' :md='18' :sm='18'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-space>
                <a-button type='primary' @click='format'>格式化</a-button>
                <a-button type='primary' @click='compress'>压缩</a-button>
                <a-button type='primary' v-model='contentBackup' @click='copy_value'>复制</a-button>
                <a-button type='danger' @click='reset'>清空</a-button>
              </a-space>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <!-- 编辑器 -->
      <div ref='vue_editor' style='height: 50vh;width: 100%'></div>
      <a-form :form='form' style='margin-top: 20px'>
        <a-row class='form-row' :gutter='24'>
          <a-col :lg='8' :md='8' :sm='8'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-input style='width: 100%' v-model='xmlPath' placeholder='请输入xmlPath表达式' />
            </a-form-item>
          </a-col>
          <a-col :lg='4' :md='4' :sm='4'>
            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
              <a-button type='primary' @click='pickTextByPath'>
                获取结果
              </a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import ace from 'brace/index'
import 'brace/ext/emmet'
import 'brace/ext/language_tools'
import 'brace/mode/html'
import 'brace/mode/xml'
import 'brace/mode/javascript'
import 'brace/theme/xcode'
import 'brace/theme/terminal'
import 'brace/snippets/javascript'
import {
  string_to_xml_wrap,
  check_string_type,
  wrap_to_string,
  string_to_wrap
} from '@/source/js/data_format_utils'
import TaskForm from '@/views/text/xmlEditor/modules/TaskForm'

export default {
  name: 'xmlEditor',
  props: {
    theme: {
      type: String,
      default: 'xcode',
      required: false
    },
    options: Object,
    toolbar: {
      required: false,
      default: true,
      type: Boolean
    },
    disable: {
      required: false,
      type: Boolean,
      default: false
    },
    type: {
      required: false,
      type: String
    }
  },
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
      editor: null,
      contentBackup: '',
      value_type: null,
      internalChange: false,
      value: '' +
        '<name>jiangwei</name>\n' +
        '<sex>man</sex>\n' +
        '<config>\n' +
        '    <region>Beijing</region>\n' +
        '    <version>v1.0</version>\n' +
        '</config>\n',
      xmlPath: ''
    }
  },
  mounted() {
    this.initView()
  },
  watch: {
    theme(v) {
      this.editor.setTheme('ace/theme/' + v)
    },
    options(v) {
      this.editor.setOptions(v)
    },
    height() {
      this.$nextTick(function() {
        this.editor.resize()
      })
    },
    width() {
      this.$nextTick(function() {
        this.editor.resize()
      })
    },
    value(v) {
      if (this.editor && !this.internalChange) {
        v = v ? v : ''
        typeof v === 'object' && (v = JSON.stringify(v))
        this.contentBackup = string_to_wrap(v)
        this.value_type = this.type || check_string_type(this.contentBackup)
        this.editor.session.setValue(this.contentBackup)
      }
    },
    value_type(nv) {
      // if(!this.editor){
      //   setTimeout(() => {  this.value_type(nv) }, 100)
      // }
      switch (nv) {
        case 'XML': {
          this.contentBackup = string_to_xml_wrap(this.contentBackup)
          this.editor.getSession().setMode('ace/mode/' + nv.toLowerCase())
          this.editor.session.setValue(this.contentBackup)
          break
        }
        case 'HTML': {
          this.contentBackup = string_to_xml_wrap(this.contentBackup)
          this.editor.getSession().setMode('ace/mode/' + nv.toLowerCase())
          this.editor.session.setValue(this.contentBackup)
          break
        }
        // 新增类别
        case 'javascript': {
          this.editor.getSession().setMode('ace/mode/' + nv.toLowerCase())
          this.editor.session.setValue(this.contentBackup)
          break
        }
      }
    },
    disable(v) {
      if (this.editor) {
        this.editor.setReadOnly(v)
        v
          ? this.$refs.vue_editor.classList.add('ace_content_disable')
          : this.$refs.vue_editor.classList.remove('ace_content_disable')
      }
    }
  },
  methods: {
    // 清空
    reset() {
      this.contentBackup = ''
      this.value = ''
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    // 单位校验
    px(n) {
      if (/^\d*$/.test(n)) {
        return n + 'px'
      }
      return n
    },
    // 格式化
    format() {
      this.contentBackup = string_to_wrap(this.contentBackup, this.value_type)
      this.editor.session.setValue(this.contentBackup)
    },
    // 数据转字符串
    compress() {
      this.contentBackup = wrap_to_string(this.contentBackup, this.value_type)
      this.editor.session.setValue(this.contentBackup)
    },
    copy_value() {
      this.$copyText(this.contentBackup).then(
        () => {
          this.$message.success('已经复制到粘贴板！')
        },
        () => {
          this.$message.error('复制失败！')
        }
      )
    },
    onChange() {
      let error = false
      let v
      try {
        v = this.editor.getValue()
        error = false
      } catch (err) {
        error = true
      }
      if (error) {
        this.$emit('error')
      } else {
        if (this.editor) {
          this.internalChange = true
          this.contentBackup = v
          this.$emit('input', v)
          this.$nextTick(() => {
            this.internalChange = false
          })
        }
      }
    },
    // 编辑器
    initView() {
      this.contentBackup = this.value ? this.value : ''
      this.value_type = check_string_type(this.value)
      let vm = this
      let lang = this.lang || 'text'
      let theme = this.theme && this.theme !== 'xcode' ? this.theme : 'xcode'
      let editor_div = this.$refs.vue_editor
      let editor = (vm.editor = ace.edit(editor_div))
      this.$emit('init', editor)
      editor.$blockScrolling = Infinity
      editor.setOption('enableEmmet', false)
      editor.getSession().setMode('ace/mode/' + lang)
      editor.setTheme('ace/theme/' + theme)
      editor.getSession().setUseWrapMode(true)
      editor.setShowPrintMargin(false)
      editor.setValue(this.contentBackup)
      editor.on('change', vm.onChange)
      if (vm.options) editor.setOptions(vm.options)
      if (vm.disable) {
        editor.setReadOnly(true)
      }
      // 启用提示
      editor.setOption({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
      })
    },
    pickTextByPath() {
      let text = wrap_to_string(this.contentBackup, this.value_type)
      this.$dialog(TaskForm,
        // component props
        {
          record: {
            text: text,
            xmlPath: this.xmlPath
          },
          on: {
            ok() {
            },
            cancel() {
            },
            close() {
            }
          }
        },
        // modal props
        {
          title: 'XmlPath提取结果',
          width: 700,
          centered: true,
          maskClosable: false
        })
    }
  },
  beforeDestroy() {
    this.editor.destroy()
    this.editor.container.remove()
  }
}
</script>

<style>
.box-card {
  margin: 20px;
}

.btn-hover {
  padding-left: 6px;
  padding-right: 6px;
}

.btn-hover:hover {
  background: #e0e0e0 !important;
}

.ace-xcode .ace_gutter {
  border-right: none !important;
  background: #fafafa !important;
}

.ace_content_disable {
  background: #fafafa !important;
}
</style>
