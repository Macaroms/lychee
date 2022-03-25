<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
<!--      Copyright © 2020 小诺开源技术 All rights reserved. Snowy 1.8-->
      <a href="https://www.baidu.com" target="_blank">Copyright © 2022 Lychee All rights reserved.</a>
    </template>
<!--    <template v-slot:copyright>-->
<!--      <a href="https://github.com/vueComponent" target="_blank">vueComponent</a>-->
<!--    </template>-->
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  }
}
</script>

<style>
.ant-pro-global-footer {
  margin: 0 0 24px 0;
  padding: 0 16px;
  text-align: center;
}
</style>
