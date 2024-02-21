<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-form-item label="选择项目" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <a-select v-model="project" style="width: 120px">
              <a-select-option v-for="item in projectList" :key="item">
                {{ item.key }}
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="url参数" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ text }}</span>
          </a-form-item>
          <a-form-item label="msg" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ msg }}</span>
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
            <a-button type="primary" @click="saveApprovalSelectedItems">
              Submit
            </a-button>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { postAction } from '@/api/httpManager.js'
import wx from 'jwxwork'
import ww from 'jwecom'

export default {
  name: 'CharacterConversion',
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
      url: {},
      encList: ['UTF-8', 'UTF-16', 'UTF-16BE', 'UTF-16LE', 'GBK'],
      enc: 'UTF-8',
      projectList: [{
        key: 'item-1',
        value: '选项1'
      }, {
        key: 'item-2',
        value: '选项2'
      }],
      project: '',
      text: '',
      msg: '',
    }
  },
  created() {
    // wx.agentConfig({
    //   corpid: '', // 必填，企业微信的corpid，必须与当前登录的企业一致
    //   agentid: '', // 必填，企业微信的应用id （e.g. 1000247）
    //   timestamp: , // 必填，生成签名的时间戳
    //   nonceStr: '', // 必填，生成签名的随机串
    //   signature: '',// 必填，签名，见附录-JS-SDK使用权限签名算法
    //   jsApiList: ['selectExternalContact'], //必填，传入需要使用的接口名称
    //   success: function(res) {
    //     // 回调
    //   },
    //   fail: function(res) {
    //     if(res.errMsg.indexOf('function not exist') > -1){
    //       alert('版本过低请升级')
    //     }
    //   }
    // });
    this.agentConfig()
    let selectorType = this.getParameterByName('selectorType')
    let key = this.getParameterByName('key')
    this.text = 'key: ' + key + ' selectorType: ' + selectorType
  },
  methods: {
    getParameterByName(name) {
      const url = window.location.href
      name = name.replace(/[\[\]]/g, '\\$&')
      const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url)
      if (!results) return null
      if (!results[2]) return ''
      return decodeURIComponent(results[2].replace(/\+/g, ' '))
    },
    saveApprovalSelectedItems(){
      wx.invoke('saveApprovalSelectedItems', {
        "key": this.getParameterByName('key'),
        "selectedData": '[{key: "item-2", value: "选项2"}]'
      }, (res) => {
        this.msg = res
        if (res.err_msg === 'saveApprovalSelectedItems:ok') {
          // 保存成功
        }
      });
    },
    agentConfig(){
      // // jsapi_ticket=JSAPITICKET&noncestr=NONCESTR&timestamp=TIMESTAMP&url=URL
      // wx.agentConfig({
      //   corpid: 'ww2728fd178710bdbe', // 必填，企业微信的corpid，必须与当前登录的企业一致
      //   agentid: '1000002', // 必填，企业微信的应用id （e.g. 1000247）
      //   timestamp: 1706687007, // 必填，生成签名的时间戳
      //   nonceStr: 'lychee', // 必填，生成签名的随机串
      //   signature: '498e78f7023353c8a14e04d118819a79bad27651',// 必填，签名，见附录-JS-SDK使用权限签名算法
      //   jsApiList: ['selectExternalContact'], //必填，传入需要使用的接口名称
      //   success: function(res) {
      //   },
      //   fail: function(res) {
      //     if(res.errMsg.indexOf('function not exist') > -1){
      //       alert('版本过低请升级')
      //     }
      //   }
      // });
      const JSAPI_TICKET = 'sM4AOVdWfPE4DxkXGEs8VIeo2zeBrY-Yr5NkFOCJj3S6DVMEiy5QMRH3a0yPdqcHDqeRXE8JcHQZsy6E67PQHA'
      ww.register({
        corpId: 'ww2728fd178710bdbe',
        getConfigSignature() {
          return ww.getSignature(JSAPI_TICKET)
        },
        onConfigComplete(res) {
          alert(JSON.stringify(res))
        }
      })
    }

  }
}
</script>
