<template>
  <page-header-wrapper>
    <template v-slot:content>
      <div class='page-header-content'>
        <div class='avatar'>
          <a-avatar size='large' :src="require('../../images/user.jpg')" />
        </div>
        <div class='content'>
          <div class='content-title'>
            {{ timeFix }}，现在是{{ today }} {{ time }}<span class='welcome-text'>，{{ passed }}</span>
          </div>
          <div>
            <a style='color: #909090' target='_blank' href='https://gitee.com/jiangwei97/lychee-web'>
              Gitee : https://gitee.com/jiangwei97/lychee
            </a>
          </div>
        </div>
      </div>
    </template>
    <a-row :gutter='24'>
      <a-col :bordered='false' :lg='8' :md='8' :sm='8'>
        <a-card class='clock-card'>
          <clock ref='clock'></clock>
        </a-card>
      </a-col>
      <a-col :bordered='false' :lg='8' :md='8' :sm='8'>
        <div id='he-plugin-standard'></div>
      </a-col>
    </a-row>
    <a-card :bordered='false' style='text-align: center;margin-top: 20px' :loading='loading'>
      <a-row class='form-row' :gutter='24'>
        <a-col :lg='2' :md='2' :sm='24'>
          <div style='text-align: left'>
            <a-button type='link' @click='poetry'>
              换一句
              <a-icon type='reload' />
            </a-button>
          </div>
        </a-col>
        <a-col :lg='20' :md='20' :sm='24'>
          <a-form-item :wrapper-col='{ span: 24, offset: 0 }'>
            <strong style='font-size: xx-large'>{{ poetryData.content }}</strong><br />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class='form-row' :gutter='24'>
        <a-col :lg='24' :md='24' :sm='24'>
          <div style='text-align: center;margin-right: 30px;color: #909090'>
            <a-space size='large'>
              <span>出自：{{ poetryData.origin }}</span>
              <span>作者：{{ poetryData.author }}</span>
              <span>类型：{{ poetryData.category }}</span>
            </a-space>
          </div>
        </a-col>
      </a-row>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { getAction } from '@/api/httpManager.js'
import { timeFix } from '@/utils/util'
import { mapState } from 'vuex'
import { PageHeaderWrapper } from '@ant-design-vue/pro-layout'
import { Radar } from '@/components'
import Clock from '@/views/dashboard/components/Clock'

export default {
  name: 'Workplace',
  components: {
    PageHeaderWrapper,
    Radar,
    Clock
  },
  data() {
    return {
      timeFix: timeFix(),
      user: {},
      projects: [],
      loading: false,
      radarLoading: true,
      activities: [],
      teams: [],
      weekList: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
      today: '',
      passed: '',
      time: '',
      url: {
        poetry: '/api/remote/poetry'
      },
      poetryData: {
        content: '',
        author: '',
        origin: '',
        category: ''
      }
    }
  },
  computed: {
    ...mapState({
      nickname: state => state.user.nickname,
      welcome: state => state.user.welcome
    }),
    currentUser() {
      return {
        name: 'Lychee'
      }
    },
    userInfo() {
      return this.$store.getters.userInfo
    }
  },
  created() {
    this.user = this.userInfo
    let today = new Date()
    today.setTime(today.getTime())
    let year = today.getFullYear()
    this.today = year + '年' + (today.getMonth() + 1) + '月' +
      today.getDate() + '日' + ' ' + this.weekList[today.getDay()]
    let passedPercent = Number(
      this.passedPercent(year, today.getMonth() + 1, today.getDate()) * 100
    ).toFixed(2) + '%'
    this.passed = year + '年已经过去了' + passedPercent + '，要加油哦！'
    this.poetry()
    this.$nextTick(() => {
      this.startClock()
    })
    //和风天气插件调用
    window.WIDGET = {
      'CONFIG': {
        'layout': '1',
        'width': 570,
        'height': 250,
        'background': '1',
        'dataColor': 'FFFFFF',
        'language': 'zh',
        'borderRadius': '5',
        'key': '85d88bfe43e44406afc31cafd88063a0'
      }
    };
    (function(d) {
      let c = d.createElement('link')
      c.rel = 'stylesheet'
      let s = d.createElement('script')
      s.src = 'https://widget.qweather.net/standard/static/js/he-standard.js?v=1.4.0'
      let sn = d.getElementsByTagName('script')[0]
      sn.parentNode.insertBefore(c, sn)
      sn.parentNode.insertBefore(s, sn)
    })(document)
  },
  mounted() {
    let _this = this // 声明一个变量指向Vue实例this，保证作用域一致
    _this.time = this.getTime()
    this.timer = setInterval(() => {
      _this.time = this.getTime() // 修改数据date
    }, 1000)
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer) // 在Vue实例销毁前，清除我们的定时器
    }
  },
  methods: {
    startClock() {
      this.$refs.clock.startClock()
    },
    // 是否闰年
    isLeapYear(year) {
      return (year % 4 === 0 && year % 100 !== 0 || year % 400 === 0)
    },
    //判断天数
    passedPercent(year, month, day) {
      let days = day
      //天数没有规律,故放在一个数组中
      let monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
      for (let i = 0; i < month - 1; i++) {  //传进来的月份,对应的下标是-1的
        days += monthDays[i]
      }
      //如果是闰年,天数加一
      let isLeapYear = this.isLeapYear(year)
      if (isLeapYear && month > 2) {
        days++
      }
      let wholeYearDays = isLeapYear ? 366 : 365
      return days / wholeYearDays
    },
    getTime() {
      let time = new Date()
      let second = time.getSeconds()
      return time.getHours() + '时' + time.getMinutes() + '分' + (second.toString().length < 2 ? '0' + second : second) + '秒'
    },
    poetry() {
      this.loading = true
      getAction(this.url.poetry).then((res) => {
        if (res.code === 200) {
          this.poetryData = res.data
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
<style lang='less' scoped>
@import './Workplace.less';
@import '../../source/css/he-standard.css';
// @import '../../source/js/he-standard.js';

.project-list {
  .card-title {
    font-size: 0;

    a {
      color: rgba(0, 0, 0, 0.85);
      margin-left: 12px;
      line-height: 24px;
      height: 24px;
      display: inline-block;
      vertical-align: top;
      font-size: 14px;

      &:hover {
        color: #1890ff;
      }
    }
  }

  .card-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }

  .project-item {
    display: flex;
    margin-top: 8px;
    overflow: hidden;
    font-size: 12px;
    height: 20px;
    line-height: 20px;

    a {
      color: rgba(0, 0, 0, 0.45);
      display: inline-block;
      flex: 1 1 0;

      &:hover {
        color: #1890ff;
      }
    }

    .datetime {
      color: rgba(0, 0, 0, 0.25);
      flex: 0 0 auto;
      float: right;
    }
  }

  .ant-card-meta-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }
}

.item-group {
  padding: 20px 0 8px 24px;
  font-size: 0;

  a {
    color: rgba(0, 0, 0, 0.65);
    display: inline-block;
    font-size: 14px;
    margin-bottom: 13px;
    width: 25%;
  }
}

.members {
  a {
    display: block;
    margin: 12px 0;
    line-height: 24px;
    height: 24px;

    .member {
      font-size: 14px;
      color: rgba(0, 0, 0, 0.65);
      line-height: 24px;
      max-width: 100px;
      vertical-align: top;
      margin-left: 12px;
      transition: all 0.3s;
      display: inline-block;
    }

    &:hover {
      span {
        color: #1890ff;
      }
    }
  }
}

.mobile {
  .project-list {
    .project-card-grid {
      width: 100%;
    }
  }

  .more-info {
    border: 0;
    padding-top: 16px;
    margin: 16px 0 16px;
  }

  .headerContent .title .welcome-text {
    display: none;
  }
}

.clock-card {
  height: 250px;
  text-align: center;
  overflow: hidden;
  display: flex;
  align-items: center;
  backdrop-filter: blur(5px);

}

</style>
