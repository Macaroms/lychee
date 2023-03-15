<template>
  <page-header-wrapper :title='false' :content="$t('text.character-conversion.description')">
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-divider orientation='left'>{{ $t('menu.text.character-conversion') }}</a-divider>
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
          <br />
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <a-button @click='onCopy' style='width: 70px'>
              {{ $t('text.character-conversion.copy') }}
            </a-button>
            <a-button @click='onCut' style='width: 70px;margin-left: 8px'>
              {{ $t('text.character-conversion.cut') }}
            </a-button>
            <a-button @click='reset' style='width: 70px;margin-left: 8px' type='danger'>
              {{ $t('text.character-conversion.reset') }}
            </a-button>
          </a-form-item>
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <a-space>
              <a-button type='primary' style='width: 148px;' @click='toUpperCase'>{{ $t('text.character-conversion.toUpperCase') }}</a-button>
              <a-button type='primary' style='width: 148px;' @click='toLowerCase'>{{ $t('text.character-conversion.toLowerCase') }}</a-button>
              <a-button type='primary' style='width: 148px;' @click='firstLetterUpper'>{{ $t('text.character-conversion.firstLetterUpper') }}</a-button>
              <a-button type='primary' style='width: 148px;'>{{ $t('text.character-conversion.firstLetterAcronym') }}</a-button>
            </a-space><br />
            <a-space>
              <a-button type='primary' style='width: 148px;' @click='underlineToHump'>{{ $t('text.character-conversion.underlineToHump') }}</a-button>
              <a-button type='primary' style='width: 148px;' @click='humpToUnderline'>{{ $t('text.character-conversion.humpToUnderline') }}</a-button>
              <a-button type='primary' style='width: 148px;' @click='spaceToUnderline'>{{ $t('text.character-conversion.spaceToUnderline') }}</a-button>
              <a-button type='primary' style='width: 148px;' @click='underlineToSpace'>{{ $t('text.character-conversion.underlineToSpace') }}</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>

<script>
export default {
  name: 'CharacterConversion',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      context: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      }
    }
  },
  methods: {
    // 全小写
    toLowerCase() {
      this.context = this.context.toLowerCase()
    },
    // 全大写
    toUpperCase() {
      this.context = this.context.toUpperCase()
    },
    // 下划线转驼峰
    underlineToHump() {
      this.toLowerCase()
      this.context = this.context.replace(/_([a-z])/g, res => res[1].toUpperCase())
    },
    // 驼峰转下划线
    humpToUnderline() {
      this.context = this.context.replace(/([A-Z])/g, (res) => {
        return '_' + res.toLowerCase()
      })
    },
    // 下划线转空格
    underlineToSpace() {
      this.context = this.context.split('_').join(' ')
    },
    // 空格转下划线
    spaceToUnderline() {
      this.context = this.context.split(' ').join('_')
    },
    // 单词首字母大写
    firstLetterUpper() {
      let convertToArray = this.context.split(/[,.?!;。？！ ]/)
      for (let i = 0; i < convertToArray.length; i++) {
        let char = convertToArray[i].charAt(0)
        convertToArray[i] = convertToArray[i].replace(char, function replace(char) {
          return char.toUpperCase()
        })
      }
      this.context = convertToArray.join(' ')
    },
    // 句子首字母大写
    sentenceUpper() {
      let convertToArray = this.context.split(/[.?!。？！]/)
      for (let i = 0; i < convertToArray.length; i++) {
        let char = convertToArray[i].charAt(0)
        convertToArray[i] = convertToArray[i].replace(char, function replace(char) {
          return char.toUpperCase()
        })
      }
      this.context = convertToArray.join(' ')
    },
    // 复制
    onCopy() {
      this.$copyText(this.context).then(() => {
        this.$message.success('已成功复制到剪切板')
      }).catch(() => {
        this.$message.error('未检测到内容')
      })
    },
    // 剪切
    onCut() {
      this.$copyText(this.context).then(() => {
        this.$message.success('已成功剪切到剪切板')
      }).catch(() => {
        this.$message.error('未检测到内容')
      }).finally(() => {
        this.context = ''
      })
    },
    // 清空
    reset() {
      this.context = ''
      this.$message.success('清除成功')
    }
  }
}
</script>
