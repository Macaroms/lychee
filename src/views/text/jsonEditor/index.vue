<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='12' :md='12' :sm='24'>
              <div id='codeEditor' :style='contentStyleObj'></div>
            </a-col>
            <a-col :lg='12' :md='12' :sm='24'>
              <div id='treeEditor' :style='contentStyleObj'></div>
            </a-col>
          </a-row>
          <br/>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='8' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input style="width: 100%" v-model="jsonPath" placeholder="请输入jsonPath表达式"/>
              </a-form-item>
            </a-col>
            <a-col :lg='4' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <div style='text-align: right'>
                  <a-button type="primary" @click='pickTextByPath'>
                    获取结果
                  </a-button>
                </div>
              </a-form-item>
            </a-col>
            <a-col :lg='12' :md='12' :sm='24'>
              <div style='text-align: right'>
                <a-space>
                  <a-button @click='onCopy'>
                    {{ $t('text.character-conversion.copy') }}
                  </a-button>
                  <a-button @click='reset' type='danger'>
                    {{ $t('text.character-conversion.reset') }}
                  </a-button>
                </a-space>
              </div>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import 'jsoneditor/dist/jsoneditor.min.css'
import jsoneditor from 'jsoneditor'
import {postAction} from '@/api/httpManager.js'
import TaskForm from './modules/TaskForm'

export default {
  name: 'jsonEditor',
  components: {
    TaskForm,
  },
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      contentStyleObj: {
        height: ''
      },
      codeEditor: null,
      treeEditor: null,
      syncData: true,
      url: {
        pickTextByPath: '/api/text/pickTextByPath',
      },
      jsonPath: ''
    }
  },
  computed: {},
  created() {
    window.addEventListener('resize', this.getHeight)
    this.getHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.getHeight)
  },
  mounted() {
    let treeEditorElement = document.getElementById('treeEditor')
    let codeEditorElement = document.getElementById('codeEditor')
    let json = {
      'Array': [1, 2, 3],
      'Boolean': true,
      'Null': null,
      'Number': 123,
      'Object': {'a': 'b', 'c': 'd'},
      'String': 'Hello World'
    }
    let treeOptions = {
      mode: 'tree',
      modes: ['code', 'tree'],
      onError: (err) => {
        this.$message.error(err.toString())
      },
      onChange: () => {
        if (this.syncData) {
          this.convertRight()
        }
      }
    }
    let codeOptions = {
      mode: 'code',
      modes: ['code', 'tree'],
      onError: (err) => {
        this.$message.error(err.toString())
      },
      onChange: () => {
        if (this.syncData) {
          this.convertLeft()
        }
      }
    }
    this.treeEditor = new jsoneditor(treeEditorElement, treeOptions, json)
    this.codeEditor = new jsoneditor(codeEditorElement, codeOptions, json)
  },
  methods: {
    // 复制
    onCopy() {
      try {
        let result = this.codeEditor.getText()
        JSON.parse(result)
        this.$copyText(result).then(() => {
          this.$message.success('已成功复制到剪切板')
        }, (e) => {
          this.$message.error('复制到剪切板失败！' + e.toString())
        })
      } catch (e) {
        this.$message.error('JSON数据格式不正确')
      }
    },
    // 清空
    reset() {
      this.codeEditor.update({})
      this.treeEditor.update({})
      this.$message.success('清除成功')
    },
    convertRight() {
      this.codeEditor.update(this.treeEditor.get())
    },
    convertLeft() {
      this.treeEditor.update(this.codeEditor.get())
    },
    getHeight() {
      this.contentStyleObj.height = window.innerHeight - 300 + 'px'
    },
    pickTextByPath() {
      this.confirmLoading = true
      postAction(
        this.url.pickTextByPath,
        {
          type: "json",
          src: this.codeEditor.getText(),
          path: this.jsonPath
        }
      ).then((res) => {
        if (res.success) {
          this.$dialog(TaskForm,
            // component props
            {
              record: res.data,
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
              title: 'JsonPath提取结果',
              width: 700,
              centered: true,
              maskClosable: false
            })
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
  }
}
</script>
