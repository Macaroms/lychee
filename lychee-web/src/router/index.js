import Vue from 'vue'
import Router from 'vue-router'
import { constantRouterMap, asyncRouterMap } from '@/config/router.config'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap.concat(asyncRouterMap)
})

router.beforeEach((to, from, next) => {
  if(to.path.indexOf('WW_verify_S0tZA59MEWgInl15')>-1){
    let url= loadFile('/WW_verify_S0tZA59MEWgInl15.txt')
  }
  else{
    next()
  }
})

router.beforeEach((to, from, next) => {
  if(to.path.indexOf('7f3ac877670904443411f984818e967e.txt')>-1){
    let url= loadFile('/7f3ac877670904443411f984818e967e.txt')
  }
  else{
    next()
  }
})

export default router