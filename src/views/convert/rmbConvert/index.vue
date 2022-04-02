<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>人民币数字转大写</a-divider>
      <a-spin :spinning='confirmLoading'>
        <a-form :form='form'>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-form-item label="输入人民币金额" :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input-number
                  :formatter="value => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                  :parser="value => value.replace(/￥\s?|(,*)/g, '')"
                  @change="onChange"
                  style="width: 100%"
                  v-model="rmbNum"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-form-item label="输入人民币金额" :wrapper-col='{ span: 24, offset: 0 }'>
                <a-input
                  style="width: 100%"
                  v-model="rmbStr"
                  read-only
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class='form-row' :gutter='24'>
            <a-col :lg='24' :md='12' :sm='24'>
              <a-button type="primary" icon="copy" @click="onCopy">复制</a-button>
              <a-button type="danger" icon="delete" style="margin-left: 8px" @click="reset">清空</a-button>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-card>
    <a-card style='margin-top: 20px' :bordered='false'>
      <a-divider orientation='left'>对照表</a-divider>
      <a-table
        :columns="columns"
        :data-source="data"
        rowKey="zero"
        :pagination="false"
        bordered
      >
        <template slot="name" slot-scope="text">
          <a>{{ text }}</a>
        </template>
      </a-table>
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
  name: 'jsonEditor',
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      columns: [
        {
          title: '0',
          dataIndex: 'zero',
          key: 'zero',
          align: 'center'
        },
        {
          title: '1',
          dataIndex: 'one',
          key: 'one',
          align: 'center'
        },
        {
          title: '2',
          dataIndex: 'two',
          key: 'two',
          align: 'center'
        },
        {
          title: '3',
          dataIndex: 'three',
          key: 'three',
          align: 'center'
        },
        {
          title: '4',
          dataIndex: 'four',
          key: 'four',
          align: 'center'
        },
        {
          title: '5',
          dataIndex: 'five',
          key: 'five',
          align: 'center'
        },
        {
          title: '6',
          dataIndex: 'six',
          key: 'six',
          align: 'center'
        },
        {
          title: '7',
          dataIndex: 'seven',
          key: 'seven',
          align: 'center'
        },
        {
          title: '8',
          dataIndex: 'eight',
          key: 'eight',
          align: 'center'
        },
        {
          title: '9',
          dataIndex: 'nine',
          key: 'nine',
          align: 'center'
        },
        {
          title: '十',
          dataIndex: 'ten',
          key: 'ten',
          align: 'center'
        },
        {
          title: '百',
          dataIndex: 'hundred',
          key: 'hundred',
          align: 'center'
        },
        {
          title: '千',
          dataIndex: 'thousand',
          key: 'thousand',
          align: 'center'
        }
      ],
      data: [
        {
          zero: '零',
          one: '壹',
          two:'贰',
          three:'叁',
          four:'肆',
          five:'伍',
          six:'陆',
          seven:'柒',
          eight:'捌',
          nine:'玖',
          ten:'拾',
          hundred:'佰',
          thousand:'仟',
        }
      ],
      rmbNum: 1000,
      rmbStr: "壹仟元整"
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
      this.rmbNum = ''
      this.rmbStr = '零元整'
    },
    onChange(value) {
      this.rmbStr = this.rmbConvert(value)
    },
    rmbConvert(n) {
      let fraction = ['角', '分'];
      let digit = [
        '零', '壹', '贰', '叁', '肆',
        '伍', '陆', '柒', '捌', '玖'
      ];
      let unit = [
        ['元', '万', '亿'],
        ['', '拾', '佰', '仟']
      ];
      let IsNum = Number(n);
      if (!isNaN(IsNum)) {
        let head = n < 0 ? '欠' : '';
        n = Math.abs(n);
        let s = '';
        for (let i = 0; i < fraction.length; i++) {
          s += (digit[Math.floor(n * 100 / 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
        }
        s = s || '整';
        n = Math.floor(n);
        for (let i = 0; i < unit[0].length && n > 0; i++) {
          let p = '';
          for (let j = 0; j < unit[1].length && n > 0; j++) {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
          }
          s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
        }
        return head + s.replace(/(零.)*零元/, '元')
          .replace(/(零.)+/g, '零')
          .replace(/^整$/, '零元整');
      } else {
        return "";
      }
    },
    // 复制
    onCopy() {
      this.$copyText(this.rmbStr).then(() => {
        this.$message.success('已成功复制到剪切板')
      }).catch(() => {
        this.$message.error('未检测到内容')
      })
    },

  }
}
</script>
