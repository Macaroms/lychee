<template>
  <div class='clock'>
    <div class='hr'>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
      </div>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
        <div class='number'>3</div>
        <div class='number'>4</div>
        <div class='number'>5</div>
        <div class='number'>6</div>
        <div class='number'>7</div>
        <div class='number'>8</div>
        <div class='number'>9</div>
      </div>
    </div>
    <div class='min'>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
        <div class='number'>3</div>
        <div class='number'>4</div>
        <div class='number'>5</div>
      </div>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
        <div class='number'>3</div>
        <div class='number'>4</div>
        <div class='number'>5</div>
        <div class='number'>6</div>
        <div class='number'>7</div>
        <div class='number'>8</div>
        <div class='number'>9</div>
      </div>
    </div>
    <div class='sec'>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
        <div class='number'>3</div>
        <div class='number'>4</div>
        <div class='number'>5</div>
      </div>
      <div class='strip'>
        <div class='number'>0</div>
        <div class='number'>1</div>
        <div class='number'>2</div>
        <div class='number'>3</div>
        <div class='number'>4</div>
        <div class='number'>5</div>
        <div class='number'>6</div>
        <div class='number'>7</div>
        <div class='number'>8</div>
        <div class='number'>9</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import { getRoleList, getServiceList } from '@/api/manage'

const DataSet = require('@antv/data-set')

export default {
  name: 'Workplace',
  components: {
  },
  data() {
    return {
      strips: [],
      numberSize: '4' // in rem
    }
  },
  computed: {
    ...mapState({
      nickname: state => state.user.nickname,
      welcome: state => state.user.welcome
    }),
  },
  created() {
  },
  mounted() {
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer) // 在Vue实例销毁前，清除我们的定时器
    }
  },
  methods: {
    startClock() {
      this.strips = [...document.querySelectorAll('.strip')]
      console.log("startClock", this.strips)
      setInterval(() => {
        // get new time
        const time = new Date()
        // get h,m,s
        const hours = time.getHours()
        const min = time.getMinutes()
        const secs = time.getSeconds()
        // slide strips
        this.stripSlider(0, hours)
        this.stripSlider(2, min)
        this.stripSlider(4, secs)
        // highlight numbers
      }, 1000)
    },
    highlight(strip, d) {
      this.strips[strip]
        .querySelector(`.number:nth-of-type(${d + 1})`)
        .classList.add('pop')

      setTimeout(() => {
        this.strips[strip]
          .querySelector(`.number:nth-of-type(${d + 1})`)
          .classList.remove('pop')
      }, 950) // causes ticking
    },

    stripSlider(strip, number) {
      let d1 = Math.floor(number / 10)
      let d2 = number % 10
      this.strips[strip].style.transform = `translateY(${d1 * -this.numberSize}vmin)`
      this.highlight(strip, d1)
      this.strips[strip + 1].style.transform = `translateY(${d2 * -this.numberSize}vmin)`
      this.highlight(strip + 1, d2)
    }
  }
}
</script>

<style lang='less' scoped>

* {
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}

body {
  font-family: 'Roboto Mono', monospace;
  font-size: 4vmin;
  min-height: 100vh;
  display: grid;
  overflow: hidden;
  place-items: center;
  background: linear-gradient(-45deg, #c4d2ef, #dfe6f6);
}

.hr,
.min,
.sec {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 3vmin;
  grid-row: 1/2;
  align-items: start;
}

.number {
  user-select: none;
  width: 4vmin;
  height: 4vmin;
  display: grid;
  place-items: center;
  color: #f9fbfd;
  transition: all 500ms 100ms ease;
  border-radius: 50%;
}

.number.pop {
  color: #3e6ccb;
  font-weight: bold;
  transform: scale(1.5);
  background-color: #dfe6f6;
  box-shadow: -1vmin -1vmin 2vmin -0.5vmin #f9fbfd, 1vmin 1vmin 2vmin #a9bee8;
}

.strip {
  transition: transform 500ms ease-in-out;
  border-radius: 1.5vmin;
  background-color: #dfe6f6;
  box-shadow: -1vmin -1vmin 2vmin -0.5vmin #f9fbfd, 1vmin 1vmin 2vmin #a9bee8;
  // box-shadow: inset 0 0 40px 40px #DBA632; /* change to alter the effect*/
}

.clock {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 8vmin;
  height: 4vmin;
  position: relative;
  padding: 0 4vmin;
}


</style>
