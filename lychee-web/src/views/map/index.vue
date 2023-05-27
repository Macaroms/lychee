<template>
  <page-header-wrapper :title='false'>
    <a-card :bordered='false'>
<!--      <a-form :form='form'>-->
<!--        <a-row class='form-row' :gutter='24'>-->
<!--          <a-col :lg='3' :md='3' :sm='24'>-->
<!--            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>-->
<!--              <a-input-->
<!--                style="width: 100%"-->
<!--                v-model="location"-->
<!--              />-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :lg='3' :md='3' :sm='24'>-->
<!--            <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>-->
<!--              <a-input-->
<!--                style="width: 100%"-->
<!--                v-model="keyword"-->
<!--                placeholder="请输入关键字"-->
<!--              />-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--        </a-row>-->
<!--      </a-form>-->
      <baidu-map class="bm-view" :center='location' :scroll-wheel-zoom="true">
        <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
        <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_LEFT"></bm-map-type>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="true"></bm-overview-map>
        <bm-city-list anchor="BMAP_ANCHOR_TOP_RIGHT" ></bm-city-list>
<!--        <bm-local-search :keyword="keyword" :auto-viewport="true" :location="location"></bm-local-search>-->
      </baidu-map>
    </a-card>
  </page-header-wrapper>
</template>
<script>
export default {
  name: 'Baidu_Map',
  data() {
    return {
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      location: '北京',
      keyword: '',
    }
  },
  methods: {
  }
}
</script>
<style>
.bm-view {
  width: 100%;
  height: 680px;
}
.anchorBL{
  display: none;
}
</style>
