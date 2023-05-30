<template>
  <page-header-wrapper :title='false'>
    <a-spin :spinning='loading'>
      <a-row :gutter='24'>
        <a-col :md='24' :sm='24' :xl='12' style='margin-bottom: 20px'>
          <a-card :body-style="{padding: '24px 32px'}" :bordered='false' style='height: 230px'>
            <a-divider orientation='left'>垃圾分类查询</a-divider>
            <a-form :form='form'>
              <a-row class='form-row' :gutter='24'>
                <a-col :md='24' :xl='24'>
                  <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
                    <a-input
                      style='width: 100%'
                      v-model='rubbishName'
                      placeholder='请输入物品名称'
                    />
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row class='form-row' :gutter='24'>
                <a-col :lg='24' :md='12' :sm='24'>
                  <a-space>
                    <a-button type='primary' icon='copy' @click='search'>查询</a-button>
                    <a-button type='danger' icon='delete' @click='reset'>清空</a-button>
                  </a-space>
                </a-col>
              </a-row>
              <a-row class='form-row' :gutter='24' style='margin-top: 20px;color: #f5222d'>
                <a-col :lg='24' :md='12' :sm='24'>
                  <span v-if='type!==""'>{{ msg }}</span>
                </a-col>
              </a-row>
            </a-form>
          </a-card>
        </a-col>
        <a-col :md='24' :sm='24' :xl='12' style='margin-bottom: 20px'>
          <a-row :gutter='24'>
            <a-col :md='6' :sm='24'>
              <a-card :class="{'rubbishBox':true, 'rubbishChoose':type==='干垃圾'}">
                <img style='width: 100%' src='../../../images/dry.png' alt='干垃圾' />
              </a-card>
            </a-col>
            <a-col :md='6' :sm='24'>
              <a-card :class="{'rubbishBox':true, 'rubbishChoose':type==='湿垃圾'}">
                <img style='width: 100%' src='../../../images/wet.png' alt='湿垃圾' />
              </a-card>
            </a-col>
            <a-col :md='6' :sm='24'>
              <a-card :class="{'rubbishBox':true, 'rubbishChoose':type==='可回收垃圾'}">
                <img style='width: 100%' src='../../../images/recyclable.png' alt='可回收垃圾' />
              </a-card>
            </a-col>
            <a-col :md='6' :sm='24'>
              <a-card :class="{'rubbishBox':true, 'rubbishChoose':type==='有害垃圾'}">
                <img style='width: 100%' src='../../../images/hazardous.png' alt='有害垃圾' />
              </a-card>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
      <a-row v-if='result.data.recommendList.length>0' :gutter='24'>
        <a-col :md='24' :sm='24' :xl='24'>
          <a-divider orientation='left'>相关搜索</a-divider>
          <a-list
            rowKey="id"
            :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 6 }"
            :dataSource="result.data.recommendList"
            class="card-list"
          >
            <a-list-item slot="renderItem" slot-scope="item">
              <template>
                <a-card :hoverable="true">
                  <a-card-meta>
                    <a slot="title">{{ item.goodsName }}</a>
                    <div class="meta-content" slot="description">{{ item.goodsType }}</div>
                  </a-card-meta>
                </a-card>
              </template>
            </a-list-item>
          </a-list>
        </a-col>
      </a-row>
    </a-spin>
  </page-header-wrapper>
</template>
<script>
import { getAction } from '@/api/httpManager.js'

export default {
  name: 'rubbishClassify',
  data() {
    return {
      form: this.$form.createForm(this),
      loading: false,
      rubbishName: '',
      url: {
        rubbishClassify: '/api/search/rubbishClassify'
      },
      result: {
        data: {
          aim: {
            goodsName: '',
            goodsType: ''
          },
          recommendList: []
        }
      },
      type: '',
      msg: ''
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
      this.rubbishName = ''
      this.type = ''
      this.msg = ''
      this.result.data.recommendList.length = []
    },
    search() {
      if (!this.rubbishName || this.rubbishName.length === 0) {
        this.$message.warning('请输入物品名称')
        return
      }
      this.loading = true
      this.type = ''
      getAction(this.url.rubbishClassify, { name: this.rubbishName }).then((res) => {
        if (res.code === 200) {
          const data = res.data
          this.result = data
          if(data.data.aim){
            this.type = data.data.aim.goodsType
            this.msg = this.rubbishName + '是' + this.type
          } else {
            this.$message.warn('未查询到信息')
          }
        } else {
          this.$message.warn(res.message)
        }
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
<style>
.rubbishBox {
  border-radius: 25px
}

.rubbishChoose {
  border-width: 3px;
  border-color: #40a9ff;
  background-color: #fef1eb
}
</style>