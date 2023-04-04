<template>
  <a-result status="403" sub-title="请登录以使用更多功能">
    <template #extra>
      <a-button type="primary" @click="login">
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
