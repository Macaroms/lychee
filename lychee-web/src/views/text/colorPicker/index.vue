<template>
  <page-header-wrapper :title='false'>
    <a-row :gutter='24'>
      <a-col :md='6' :sm='24'>
        <a-card style="height: 400px">
          <a-divider orientation='left'>点击选择颜色</a-divider>
          <colorPicker
            v-model="color"
            @change="handleChangeColor"
          />
        </a-card>
      </a-col>
      <a-col :md='8' :sm='24'>
        <a-card style="height: 400px">
          <a-form layout="inline">
            <a-divider orientation='left'>16进制颜色码</a-divider>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="值">
                  <span>{{ this.color }}</span>
                </a-form-item>
              </a-col>
            </a-row>
            <br/>
            <a-divider orientation='left'>RGB颜色值</a-divider>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="值">
                  <span>{{ this.rgbColor }}</span>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="R">
                  <a-input v-model="redVal" read-only/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="G">
                  <a-input v-model="greenVal" read-only/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="B">
                  <a-input v-model="blueVal" read-only/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-card>
      </a-col>
      <a-col :md='10' :sm='24'>
        <a-card style="height: 400px">
          <a-form>
            <a-divider orientation='left'>16进制/RGB 互转</a-divider>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="16进制">
                  <a-input v-model="convertColor" placeholder="#ffffff"/>
                </a-form-item>
              </a-col>
            </a-row>
            <br/>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='4' :md='12' :sm='24'></a-col>
              <a-col :lg='16' :md='12' :sm='24'>
                <a-form-item>
                  <div style="float: left">
                    <a-button icon="arrow-down" type="primary" shape="circle" @click="coloToRgb">
                    </a-button>
                  </div>
                  <div style="float: right">
                    <a-button icon="arrow-up" type="primary" shape="circle" @click="rgbToColor">
                    </a-button>
                  </div>
                </a-form-item>
              </a-col>
              <a-col :lg='4' :md='12' :sm='24'></a-col>
            </a-row>
            <a-row class='form-row' :gutter='24'>
              <a-col :lg='24' :md='12' :sm='24'>
                <a-form-item label="RGB">
                  <a-input v-model="convertRgb" placeholder="RGB(255,255,255)"/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-descriptions>
        <a-descriptions-item label='RGB 颜色值' :span='3'>
          RGB色彩模式是工业界的一种颜色标准，是通过对红(R)、绿(G)、蓝(B)三个颜色通道的变化以及它们相互之间的叠加来得到各式各样的颜色的，
          RGB即是代表红、绿、蓝三个通道的颜色，这个标准几乎包括了人类视力所能感知的所有颜色，是运用最广的颜色系统之一。
          <a href='https://baike.baidu.com/item/RGB/342517?fr=aladdin' target='_blank'>
            {{ $t('menu.clickToView') }}
          </a>
        </a-descriptions-item>
        <a-descriptions-item label='16进制颜色码' :span='3'>
          16进制颜色码就是在软件中设定颜色值的代码。
          <a
            href='https://baike.baidu.com/item/%E5%8D%81%E5%85%AD%E8%BF%9B%E5%88%B6%E9%A2%9C%E8%89%B2%E7%A0%81/10894232?fr=aladdin'
            target='_blank'>
            {{ $t('menu.clickToView') }}
          </a>
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
  </page-header-wrapper>
</template>

<script>
export default {
  name: 'color',
  data() {
    return {
      form: this.$form.createForm(this),
      color: '#000000',
      rgbColor: 'RGB(0,0,0)',
      redVal: '0',
      greenVal: '0',
      blueVal: '0',
      convertColor: '',
      convertRgb: '',

    }
  },
  computed: {},
  created() {
  },
  mounted() {
  },
  methods: {
    handleChangeColor(val) {
      let rgbColor = this.getRgbByColor(val)
      let rgbArr = rgbColor.match(/\((\S*)\)/)[1].split(',');
      this.redVal = rgbArr[0]
      this.greenVal = rgbArr[1]
      this.blueVal = rgbArr[2]
      this.color = val
      this.rgbColor = rgbColor
      this.onCopy(val)
    },
    // 复制
    onCopy(text) {
      this.$copyText(text).then(() => {
        this.$message.success(text + ' 已成功复制到剪切板')
      }, (e) => {
        this.$message.error('复制到剪切板失败！' + e.toString())
      })
    },
    getRgbByColor(color) {
      return color.colorRgb()
    },
    getColorByRgb(rgb) {
      return rgb.colorHex()
    },
    coloToRgb() {
      this.convertRgb = this.getRgbByColor(this.convertColor)
    },
    rgbToColor() {
      this.convertColor = this.getColorByRgb(this.convertRgb)
    }
  }
}
</script>
<style>
.m-colorPicker .colorBtn[data-v-29accc04] {
  width: 200px;
  height: 200px;
}
</style>
