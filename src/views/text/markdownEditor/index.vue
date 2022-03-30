<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <div style="margin-bottom: 10px">
        <a-button type='primary' @click='getMarkdownText'>
          {{ $t('text.markdown-editor.markdownCopy') }}
        </a-button>
        <a-button type='primary' style='margin-left: 8px' @click='getHtml'>
          {{ $t('text.markdown-editor.htmlText') }}
        </a-button>
      </div>
      <div>
        <mavon-editor v-model="content" ref="md" @change="change" style="height: 600px"/>
      </div>
    </a-card>
<!--    <a-card style='margin-top: 20px' :bordered='false'>-->
<!--      <a-descriptions>-->
<!--        <a-descriptions-item label='Markdown' :span='3'>-->
<!--          {{ $t('text.markdown-editor.markdownDesc') }}-->
<!--          <a href='https://baike.baidu.com/item/markdown/3245829?fr=aladdin' target='_blank'>-->
<!--            {{ $t('menu.clickToView') }}-->
<!--          </a>-->
<!--        </a-descriptions-item>-->
<!--        <a-descriptions-item label='Github' :span='3'>-->
<!--          <a href='https://github.com/hinesboy/mavonEditor' target='_blank'>-->
<!--            Mavon Editor-->
<!--          </a>-->
<!--        </a-descriptions-item>-->
<!--      </a-descriptions>-->
<!--    </a-card>-->
  </page-header-wrapper>
</template>

<script>
import {mavonEditor} from "mavon-editor";
import "mavon-editor/dist/css/index.css";

export default {
  name: 'markdownEditor',
  components: {
    mavonEditor
  },
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      content: "",
      html: ""
    }
  },
  computed: {},
  methods: {
    // 复制
    onCopy(text) {
      this.$copyText(text).then(() => {
        this.$message.success('已成功复制到剪切板')
      }, (e) => {
        this.$message.error('复制到剪切板失败！' + e.toString())
      })
    },
    // 所有操作都会被解析重新渲染
    change(value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render;
    },
    getMarkdownText() {
      this.onCopy(this.content)
    },
    getHtml() {
      console.log(this.html);
    }

  }
}
</script>

<style>
code {
  font-size: 100% !important;
}
mark {
  color: rgb(231, 12, 12);
  padding: 0 5px;
  margin: 0 4px;
  border-radius: 3px;
  white-space: nowrap;
  background-color: #f8f8f8;
}

.markdown-body table th {
  background-color: #f8f8f8;
}
d
.hljs {
  background: none !important;
}
</style>
