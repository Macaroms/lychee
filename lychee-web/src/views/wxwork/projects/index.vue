<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-form-item label="选择项目11" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <a-select v-model="project" style="width: 120px">
              <a-select-option v-for="item in projectList" :key="item.key">
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
          <a-form-item label="configRes" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ JSON.stringify(configRes) }}</span>
          </a-form-item>
          <a-form-item label="sign" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ sign }}</span>
          </a-form-item>
          <a-form-item label="ww" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ ww }}</span>
          </a-form-item>
          <a-form-item label="flag" :labelCol='labelCol' :wrapperCol='wrapperCol'>
            <span>{{ flag }}</span>
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
// import wxwork from '../jweixin-1.0.0.js'
import * as ww from '@wecom/jssdk'
import sha1 from 'js-sha1'

export default {
  name: 'CharacterConversion',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      labelCol: {
        xs: { span: 12 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 12 },
        sm: { span: 12 }
      },
      url: {},
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
      configRes: '',
      ww: '',
      sign: '',
      flag: '0',
    }
  },
  created() {
    let selectorType = this.getParameterByName('selectorType')
    let key = this.getParameterByName('key')
    this.text = 'key: ' + key + ' selectorType: ' + selectorType
    // this.initWxAgentConfig()
      this.initWxConfig()
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
    initWxConfig(){
      console.log('ww', ww)
      this.ww = JSON.stringify(ww)
      ww.register({
        corpId: 'ww2728fd178710bdbe',
        agentId: 1000002,
        jsApiList: ['getExternalContact'],
        getConfigSignature() {
          return getConfigSignature()
        },
        onConfigSuccess: res => {
          this.flag = this.flag + 2
        },
        onConfigFail: res => {
          console.log('onConfigFail', res)
          this.flag = this.flag + 3
        },
        onConfigComplete: res => {
          this.flag = this.flag + 4
        },
        getAgentConfigSignature() {
          return getConfigSignature()
        },
        onAgentConfigSuccess: res => {
          this.flag = this.flag + 5
        },
        onAgentConfigFail: res => {
          this.flag = this.flag + 6
        },
        onAgentConfigComplete: res => {
          this.flag = this.flag + 7
        },
      })
      function getConfigSignature() {
        const jsapiTicket = 'sM4AOVdWfPE4DxkXGEs8VIeo2zeBrY-Yr5NkFOCJj3SN9_xu5z7hYtuT2ekS0rubp0b246rGR7kO4T8m6bctEg'
        const timestamp = Math.floor(Date.now() / 1000)
        const noncestr = 'jiangwei'
        const url = window.location.href
        console.log('jsapiTicket', jsapiTicket)
        console.log('noncestr', noncestr)
        console.log('timestamp', timestamp)
        console.log('url', url)
        const str = 'jsapi_ticket=' + jsapiTicket + '&noncestr=' + noncestr + '&timestamp=' + timestamp + '&url=' + url
        let sign = sha1(str)
        console.log('sign', sign)
        return sign
      }
      ww.selectExternalContact({
        success(res) {
          console.log('success', JSON.stringify(res))
        },
        fail(res) {
          console.log('fail', JSON.stringify(res))
        }
      })
    },
    initWxAgentConfig(){
      const jsapiTicket = 'sM4AOVdWfPE4DxkXGEs8VIeo2zeBrY-Yr5NkFOCJj3TQAD7GCP0nKnCPlHZxlU66V3lNDaWhfz0VerAYZwB3Vw'
      const timestamp = Math.floor(Date.now() / 1000)
      const noncestr = 'jiangwei'
      const url = window.location.href
      console.log('jsapiTicket', jsapiTicket)
      console.log('noncestr', noncestr)
      console.log('timestamp', timestamp)
      console.log('url', url)
      const str = 'jsapi_ticket=' + jsapiTicket + '&noncestr=' + noncestr + '&timestamp=' + timestamp + '&url=' + url
      let signature = sha1(str)
      console.log(signature)

      console.log('wxwork', wxwork)
      // wxwork.agentConfig({
      //   corpid: 'ww2728fd178710bdbe', // 必填，企业微信的corpid，必须与当前登录的企业一致
      //   agentid: '1000002', // 必填，企业微信的应用id （e.g. 1000247）
      //   timestamp: timestamp, // 必填，生成签名的时间戳
      //   nonceStr: noncestr, // 必填，生成签名的随机串
      //   signature: signature,// 必填，签名，见附录-JS-SDK使用权限签名算法
      //   jsApiList: ['selectExternalContact'], //必填，传入需要使用的接口名称
      //   success: function(res) {
      //     this.configRes = res
      //     this.flag = 2
      //     alert('2')
      //   },
      //   fail: function(res) {
      //     this.configRes = res
      //     this.flag = 3
      //     alert('3')
      //     if (res.errMsg.indexOf('function not exist') > -1){
      //       alert('版本过低请升级')
      //     }
      //   }
      // });


    }

  }
}
</script>
