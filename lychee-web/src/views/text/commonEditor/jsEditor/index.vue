<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>

        <a-row class='form-row' :gutter='24'>
          <a-col :lg='12' :md='12' :sm='12'>
            <a-divider orientation='left'>JavaScript</a-divider>
            <common-editor
              :value="jsContent"
              language="javascript"
              @input="changeTextarea"
              style="height: 60vh"
              ref='common_editor'
            ></common-editor>
          </a-col>
          <a-col :lg='12' :md='12' :sm='12'>
            <a-divider orientation='left'>Params</a-divider>
            <a-form-model
              ref="dynamicValidateForm"
              :model="dynamicValidateForm"
              v-bind="formItemLayoutWithOutLabel"
            >
              <a-form-model-item
                :label="$t('editor.method-name')"
                v-bind="formItemLayout"
                prop="methodName"
                :rules="{required: true, message: 'methodName can not be null', trigger: 'blur'}"
              >
                <a-input style="width: 70%" v-model="dynamicValidateForm.methodName" :placeholder="$t('editor.method-name')" />
              </a-form-model-item>
              <a-form-model-item
                v-for="(domain, index) in dynamicValidateForm.domains"
                :key="domain.key"
                v-bind="formItemLayout"
                :label="'参数' + (index + 1)"
                :prop="'domains.' + index + '.value'"
                :rules="{required: true, message: 'domain can not be null', trigger: 'blur'}"
              >
                <a-input
                  v-model="domain.value"
                  placeholder="please input domain"
                  style="width: 45%; margin-right: 8px"
                />
                <a-select v-model="domain.type" style="width: 24%; margin-right: 8px">
                  <a-select-option v-for="type in types" :key="type.value">
                    {{ type.name }}
                  </a-select-option>
                </a-select>
                <a-icon
                  class="dynamic-delete-button"
                  type="minus-circle-o"
                  @click="removeDomain(domain)"
                />
              </a-form-model-item>
              <a-form-model-item v-bind="formItemLayoutWithOutLabel">
                <a-button type="dashed" style="width: 60%" @click="addDomain">
                  <a-icon type="plus" />
                  添加参数
                </a-button>
              </a-form-model-item>
              <a-form-model-item v-bind="formItemLayoutWithOutLabel">
                <a-button type="primary" html-type="submit" @click="submitForm()">
                  <a-icon type="caret-right" />
                  {{ $t('editor.execute') }}
                </a-button>
                <a-button style="margin-left: 10px" type='danger' @click="reset()">
                  <a-icon type="undo" />
                  {{ $t('editor.reset') }}
                </a-button>
              </a-form-model-item>
            </a-form-model>
          </a-col>
        </a-row>
      </a-spin>
    </a-card>
    <a-modal v-model="visible" title="执行结果" width="800px">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">
          关闭
        </a-button>
      </template>
      <a-form-item
      >
        <a-textarea
          style="height: 400px;background-color: #0c1021;color: #8da6ce"
          read-only
          v-model='result'
        />
      </a-form-item>
    </a-modal>
  </page-header-wrapper>
</template>

<script>
import { postAction } from '@/api/httpManager.js'
import CommonEditor from '.././components/CommonEditor'

export default {
  name: 'jsEditor',
  components: { CommonEditor },
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
        execJsScript: '/api/text/execJsScript'
      },
      result: '',
      jsContent: 'function main(key, val){\n' +
        '    var name = this.getName(key);\n' +
        '    var num = this.getNum(val);\n' +
        '    return name + \':\' + num;\n' +
        '}\n' +
        '\n' +
        'function getName(key){\n' +
        '  return \'jiang\' + key;\n' +
        '}\n' +
        '\n' +
        'function getNum(num){\n' +
        '  return num + 1;\n' +
        '}',
      formItemLayout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 4 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 20 }
        }
      },
      formItemLayoutWithOutLabel: {
        wrapperCol: {
          xs: { span: 24, offset: 0 },
          sm: { span: 20, offset: 4 }
        }
      },
      dynamicValidateForm: {
        methodName: 'main',
        domains: [
          {
            value: 'wei',
            type: 'text',
            key: 1
          },
          {
            value: '1',
            type: 'number',
            key: 2
          }
        ]
      },
      types: [
        {
          name: '文本',
          value: 'text'
        },
        {
          name: '数字',
          value: 'number'
        }
      ],
      visible: false,
    }
  },
  methods: {
    reset() {
      this.confirmLoading = true
      this.$refs.common_editor.setCodeContent('')
      this.jsContent = ''
      this.result = ''
      this.dynamicValidateForm.methodName = ''
      this.dynamicValidateForm.domains= []
      this.confirmLoading = false
    },
    changeTextarea(val) {
      this.jsContent = val
    },
    submitForm() {
      this.$refs['dynamicValidateForm'].validate(valid => {
        if (valid) {
          this.confirmLoading = true
          postAction(
            this.url.execJsScript,
            {
              code: this.jsContent,
              method: this.dynamicValidateForm.methodName,
              args: this.dynamicValidateForm.domains
            }
          ).then((res) => {
            if (res.code === 200) {
              this.result = res.data
              this.showModal()
            } else {
              this.$message.error(res.message)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        } else {
          return false
        }
      })
    },
    removeDomain(item) {
      let index = this.dynamicValidateForm.domains.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1)
      }
    },
    addDomain() {
      this.dynamicValidateForm.domains.push({
        value: '',
        type: 'text',
        key: Date.now()
      })
    },
    showModal() {
      this.visible = true;
    },
    handleCancel(e) {
      this.visible = false;
      this.result = ''
    },
  }
}
</script>
