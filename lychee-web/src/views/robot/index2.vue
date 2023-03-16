<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>{{ $t('text.radix-convert.radixConvert') }}</a-divider>
      <a-spin :spinning='confirmLoading'>
        <lemon-imui ref="IMUI" />
      </a-spin>
    </a-card>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-divider orientation='left'>{{ $t('text.radix-convert.selfRadixConvert') }}</a-divider>
      <a-form layout="inline" >
        <a-form-item :label="$t('text.radix-convert.beforeConversionRadix')">
          <a-input-number
            v-model="beforeConvertRadix"
            :min="0"
            :max="36"
          />
        </a-form-item>
        <a-form-item :label="$t('text.radix-convert.convertValue')">
          <a-input v-model="convertValue" :placeholder="$t('sys.placeholder')" />
        </a-form-item>
        <a-form-item :label="$t('text.radix-convert.afterConversionRadix')">
          <a-input-number
            v-model="afterConvertRadix"
            :min="0"
            :max="36"
          />
        </a-form-item>
        <a-form-item >
          <a-button type="primary" @click="selfConvert" >
            {{ $t('text.convert') }}
          </a-button>
        </a-form-item>
        <a-form-item >
          <a-button type="danger" @click="selfReset" >
            {{ $t('text.character-conversion.reset') }}
          </a-button>
        </a-form-item>
        <a-form-item :label="$t('text.result')">
          <a-input v-model="convertResult" read-only/>
        </a-form-item>
      </a-form>
    </a-card>
<!--    <a-card style='margin-top: 20px' :bordered='false'>-->
<!--      <a-descriptions>-->
<!--        <a-descriptions-item label='JavaScript' :span='3'>-->
<!--          {{ $t('text.radix-convert.desc') }} : let result = parseInt(num + '', m).toString(n); ( 0 <= m,n <= 36 )-->
<!--        </a-descriptions-item>-->
<!--      </a-descriptions>-->
<!--    </a-card>-->
  </page-header-wrapper>
</template>

<script>

export default {
  name: 'radixConvert',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      radix2: '',
      radix4: '',
      radix8: '',
      radix10: '',
      radix16: '',
      radix32: '',
      beforeConvertRadix: '',
      afterConvertRadix: '',
      convertValue: '',
      convertResult: '',
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
      this.radix2 = ''
      this.radix4 = ''
      this.radix8 = ''
      this.radix10 = ''
      this.radix16 = ''
      this.radix32 = ''
    },
    handleChange(dom, radix) {
      let num = dom.target.value
      if(num.length === 0){
        this.reset()
        return
      }
      this.radix2 = this.radixConvert(num, radix, 2)
      this.radix4 = this.radixConvert(num, radix, 4)
      this.radix8 = this.radixConvert(num, radix, 8)
      this.radix10 = this.radixConvert(num, radix, 10)
      this.radix16 = this.radixConvert(num, radix, 16)
      this.radix32 = this.radixConvert(num, radix, 32)
    },
    selfConvert(){
      this.convertResult = this.radixConvert(this.convertValue, this.beforeConvertRadix, this.afterConvertRadix)
    },
    selfReset(){
      this.convertValue = ''
      this.convertResult = ''
      this.beforeConvertRadix = ''
      this.afterConvertRadix = ''
    },
    // m进制的数字num转为n进制
    radixConvert(num, m, n) {
      let s = num + '';
      return parseInt(s, m).toString(n);
    }

  }
}
</script>
