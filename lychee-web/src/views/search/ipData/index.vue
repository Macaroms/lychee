<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>IP归属地查询</a-divider>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='12' :md='12' :sm='24'>
              <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input
                  style='width: 100%'
                  v-model='ip'
                  placeholder='请输入IP地址'
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-space>
                <a-button type='primary' icon='search' @click='myIp'>查看我的IP</a-button>
                <a-button type='primary' icon='copy' @click='search'>查询</a-button>
                <a-button type='danger' icon='delete' @click='reset'>清空</a-button>
              </a-space>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
    <a-card v-show='show' style='margin-top: 20px' :bordered='false' :loading='loading'>
      <a-descriptions bordered title="查询结果">
        <a-descriptions-item label="IP">
          {{ result.query }}
        </a-descriptions-item>
        <a-descriptions-item label="地区" span='2'>
          {{ result.country }} - {{ result.regionName }} - {{ result.city }}
        </a-descriptions-item>
        <a-descriptions-item label="时区">
          {{ result.timezone }}
        </a-descriptions-item>
        <a-descriptions-item label="位置" span='2'>
          经度：{{ result.lon }}， 纬度：{{ result.lat }}
        </a-descriptions-item>
        <a-descriptions-item label="组织">
          {{ result.org }}
        </a-descriptions-item>
        <a-descriptions-item label="归属" span='2'>
          {{ result.as }}
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { getAction } from '@/api/httpManager.js'

export default {
  name: 'IpData',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      loading: false,
      ip: '',
      url: {
        ipData: '/api/search/ipSearch',
        originIp: '/api/text/originIp',
      },
      result: {
        query: '',
        country: '',
        countryCode: '',
        regionName: '',
        region: '',
        city: '',
        lon: '',
        lat: '',
        timezone: '',
        as: '',
        org: '',
      },
      show: false
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
      this.ip = ''
    },
    search() {
      if (!this.ip || this.ip.length === 0) {
        this.$message.warning('请输入IP地址')
        return
      }
      this.show = true
      this.loading = true
      getAction(this.url.ipData, { ip: this.ip }).then((res) => {
        if (res.code === 200) {
          this.result = res.data
        } else {
          this.show = false
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.loading = false
      })
    },
    myIp() {
      getAction(this.url.originIp ).then((res) => {
        if (res.code === 200) {
          this.ip = res.data
          this.search()
        } else {
          this.show = false
          this.$message.error(res.message)
        }
      })
    }

  }
}
</script>
