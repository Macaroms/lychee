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
import wx from 'jwxwork'
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
      flag: 0,
    }
  },
  created() {
    let selectorType = this.getParameterByName('selectorType')
    let key = this.getParameterByName('key')
    this.text = 'key: ' + key + ' selectorType: ' + selectorType
    this.agentConfig()
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
      // const JSAPI_TICKET = 'sM4AOVdWfPE4DxkXGEs8VIeo2zeBrY-Yr5NkFOCJj3RzkN6YgLbPNSOMd7WcalMVxYbRTU5jR_HkFJbXBNvqFA'
      // console.log('ww', ww)
      // this.ww = JSON.stringify(ww)
      //
      // ww.saveApprovalSelectedItems();
      //
      // ww.register({
      //   corpId: 'ww2728fd178710bdbe',
      //   jsApiList: ['saveApprovalSelectedItems'],
      //   getConfigSignature() {
      //     this.sign = ww.getSignature(JSAPI_TICKET)
      //     this.flag = 1
      //     return ww.getSignature(JSAPI_TICKET)
      //   },
      //   onConfigComplete(res) {
      //     this.configRes = res
      //     this.flag = 2
      //   },
      //   onAgentConfigFail(res) {
      //     console.log('onAgentConfigFail', res)
      //   },
      //   onConfigFail(res) {
      //     console.log('onConfigFail', res)
      //   }
      // })


      // ww.register({
      //   corpId: 'ww2728fd178710bdbe',       // 必填，当前用户企业所属企业ID
      //   jsApiList: ['getExternalContact'], // 必填，需要使用的JSAPI列表
      //   getConfigSignature                 // 必填，根据url生成企业签名的回调函数
      // })
      //
      // async function getConfigSignature(url) {
      //   // 根据 url 生成企业签名
      //   // 生成方法参考 https://developer.work.weixin.qq.com/document/path/90539
      //   return { timestamp, nonceStr, signature }
      // }

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

      wx.agentConfig({
        corpid: 'ww2728fd178710bdbe', // 必填，企业微信的corpid，必须与当前登录的企业一致
        agentid: '1000002', // 必填，企业微信的应用id （e.g. 1000247）
        timestamp: timestamp, // 必填，生成签名的时间戳
        nonceStr: noncestr, // 必填，生成签名的随机串
        signature: signature,// 必填，签名，见附录-JS-SDK使用权限签名算法
        jsApiList: ['selectExternalContact'], //必填，传入需要使用的接口名称
        success: function(res) {
          this.configRes = res
          this.flag = 2
        },
        fail: function(res) {
          this.configRes = res
          this.flag = 3
          if (res.errMsg.indexOf('function not exist') > -1){
            alert('版本过低请升级')
          }
        }
      });


    }

  }
}
</script>
