<template>
  <a-result status="403"  style='margin-top: 100px'>
    <template #extra>
      <span style='font-size: x-large'>登录使用更多功能</span>
      <br/>
      <a-button type="primary" style='margin-top: 80px' @click="login">
        登录
      </a-button>
      <a-button type="primary" @click="register">
        注册
      </a-button>
    </template>
  </a-result>
</template>

<script>
export default {
  name: 'more',
  methods: {
    login () {
      this.$router.push({ path: '/user/Login' })
    },
    register () {
      this.$router.push({ path: '/user/Register' })
    }
  }
}
</script>
