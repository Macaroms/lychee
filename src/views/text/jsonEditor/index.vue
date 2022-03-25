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
          <br />
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <div style='text-align: right'>
                  <a-button @click='onCopy'>
                    {{ $t('text.character-conversion.copy') }}
                  </a-button>
                  <a-button @click='reset' style='margin-left: 8px' type='danger'>
                    {{ $t('text.character-conversion.reset') }}
                  </a-button>
                </div>
              </a-col>
            </a-row>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-card>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-descriptions>
        <a-descriptions-item label='JSON' :span='3'>
          {{ $t('text.json-editor.jsonDesc') }}
          <a href='https://baike.baidu.com/item/JSON/2462549?fr=aladdin' target='_blank'>
            {{ $t('menu.clickToView')}}
          </a>
        </a-descriptions-item>
        <a-descriptions-item label='Github' :span='3'>
          <a href='https://github.com/josdejong/jsoneditorhttps://github.com/josdejong/jsoneditor' target='_blank'>
            JSON Editor
          </a>
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import 'jsoneditor/dist/jsoneditor.min.css'
import jsoneditor from 'jsoneditor'

export default {
  name: 'jsonEditor',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      contentStyleObj: {
        height: ''
      },
      codeEditor: null,
      treeEditor: null,
      syncData: true


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
      'Object': { 'a': 'b', 'c': 'd' },
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
    }

  }
}
</script>
