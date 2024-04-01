// eslint-disable-next-line
import { UserLayout, BasicLayout, BlankLayout } from '@/layouts'
import { bxAnaalyse } from '@/core/icons'

const RouteView = {
  name: 'RouteView',
  render: h => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.lychee-tools' },
    redirect: '/home',
    children: [
      // dashboard
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/dashboard/Home'),
        meta: { title: 'menu.home', icon: 'home', permission: ['home'] },
      },
      // {
      //   path: '/dashboard',
      //   name: 'dashboard',
      //   redirect: '/dashboard/workplace',
      //   component: RouteView,
      //   meta: { title: 'menu.dashboard', keepAlive: true, icon: bxAnaalyse, permission: ['dashboard'] },
      //   children: [
      //     // {
      //     //   path: '/dashboard/workplace',
      //     //   name: 'Workplace',
      //     //   component: () => import('@/views/dashboard/Workplace'),
      //     //   meta: { title: 'menu.dashboard.workplace', keepAlive: true, permission: ['dashboard'] }
      //     // },
      //     // {
      //     //   path: '/dashboard/analysis/:pageNo([1-9]\\d*)?',
      //     //   name: 'Analysis',
      //     //   component: () => import('@/views/dashboard/Analysis'),
      //     //   meta: { title: 'menu.dashboard.analysis', keepAlive: false, permission: ['dashboard'] }
      //     // },
      //     // // 外部链接
      //     // {
      //     //   path: 'https://www.baidu.com/',
      //     //   name: 'Monitor',
      //     //   meta: { title: 'menu.dashboard.monitor', target: '_blank' }
      //     // },
      //     {
      //       path: '/dashboard/machine',
      //       name: 'Machine',
      //       component: () => import('@/views/dashboard/Machine'),
      //       meta: { title: 'menu.dashboard.machine', keepAlive: true, permission: ['dashboard'] }
      //     }
      //   ]
      // },
      {
        path: '/text',
        redirect: '/text/json-editor',
        component: RouteView,
        meta: { title: 'menu.text', icon: 'file-sync', permission: ['text'] },
        children: [
          {
            path: '/text/xml-editor',
            name: 'xmlEditor',
            component: () => import('@/views/text/xmlEditor'),
            meta: { title: 'menu.text.xml-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/json-editor',
            name: 'jsonEditor',
            component: () => import('@/views/text/jsonEditor'),
            meta: { title: 'menu.text.json-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/js-editor',
            name: 'jsEditor',
            component: () => import('@/views/text/commonEditor/jsEditor'),
            meta: { title: 'menu.text.js-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/py-editor',
            name: 'pyEditor',
            component: () => import('@/views/text/commonEditor/pyEditor'),
            meta: { title: 'menu.text.py-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/markdown-editor',
            name: 'markdownEditor',
            component: () => import('@/views/text/markdownEditor'),
            meta: { title: 'menu.text.markdown-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/cron-editor',
            name: 'cronEditor',
            component: () => import('@/views/text/cronEditor'),
            meta: { title: 'menu.text.cron-editor', keepAlive: true, permission: ['text'] }
          },
          {
            path: '/text/regex',
            name: 'regex',
            component: () => import('@/views/text/regex'),
            meta: { title: 'menu.text.regex', keepAlive: true, permission: ['text'] }
          },
        ]
      },
      {
        path: '/convert',
        redirect: '/text/character-conversion',
        component: RouteView,
        meta: { title: 'menu.convert', icon: 'swap', permission: ['convert'] },
        children: [
          {
            path: '/text/character-conversion',
            name: 'CharacterConversion',
            component: () => import('@/views/text/characterConversion'),
            meta: { title: 'menu.text.character-conversion', keepAlive: true, permission: ['convert'] }
          },
          {
            path: '/text/props-convert',
            name: 'propsConvert',
            component: () => import('@/views/convert/propsConvert'),
            meta: { title: 'menu.convert.props-convert', keepAlive: true, permission: ['convert'] }
          },
          {
            path: '/text/radix-convert',
            name: 'radixConvert',
            component: () => import('@/views/text/radixConvert'),
            meta: { title: 'menu.text.radix-convert', keepAlive: true, permission: ['convert'] }
          },
          {
            path: '/convert/rmb-convert',
            name: 'rmbConvert',
            component: () => import('@/views/convert/rmbConvert'),
            meta: { title: 'menu.convert.rmb-convert', keepAlive: true, permission: ['convert'] }
          },
          {
            path: '/convert/en-code',
            name: 'enCode',
            component: () => import('@/views/convert/enCode'),
            meta: { title: 'menu.convert.en-code', keepAlive: true, permission: ['convert'] }
          },
          {
            path: '/text/color-picker',
            name: 'colorPicker',
            component: () => import('@/views/text/colorPicker'),
            meta: { title: 'menu.text.color-picker', keepAlive: true, permission: ['convert'] }
          },
        ]
      },
      {
        path: '/search',
        redirect: '/search/ipData',
        component: RouteView,
        meta: { title: 'menu.search', icon: 'search', permission: ['search'] },
        children: [
          {
            path: '/search/history',
            name: 'history',
            component: () => import('@/views/search/history'),
            meta: { title: 'menu.search.history', keepAlive: true, permission: ['search'] }
          },
          {
            path: '/search/ipData',
            name: 'ipData',
            component: () => import('@/views/search/ipData'),
            meta: { title: 'menu.search.ipData', keepAlive: true, permission: ['search'] }
          },
          {
            path: '/search/rubbishClassify',
            name: 'rubbishClassify',
            component: () => import('@/views/search/rubbishClassify'),
            meta: { title: 'menu.search.rubbish-classify', keepAlive: true, permission: ['search'] }
          },
        ]
      },
      {
        path: '/third',
        redirect: '/translate',
        component: RouteView,
        meta: { title: 'menu.third', icon: 'border-outer', permission: ['third'] },
        children: [
          {
            path: '/translate',
            name: 'translate',
            component: () => import('@/views/translate'),
            meta: { title: 'menu.translate', keepAlive: true, permission: ['third'] },
          },
          {
            path: '/map',
            name: 'map',
            component: () => import('@/views/map'),
            meta: { title: 'menu.map', keepAlive: true, permission: ['third'] },
          },
        ]
      },
      {
        path: '/more',
        name: 'more',
        component: () => import('@/views/more'),
        meta: { title: 'menu.more', icon: 'bars' },
      },
      // forms
      // {
      //   path: '/form',
      //   redirect: '/form/base-form',
      //   component: RouteView,
      //   meta: { title: 'menu.form', icon: 'form', permission: ['form'] },
      //   children: [
      //     {
      //       path: '/form/base-form',
      //       name: 'BaseForm',
      //       component: () => import('@/views/form/basicForm'),
      //       meta: { title: 'menu.form.basic-form', keepAlive: true, permission: ['form'] }
      //     },
      //     {
      //       path: '/form/step-form',
      //       name: 'StepForm',
      //       component: () => import('@/views/form/stepForm/StepForm'),
      //       meta: { title: 'menu.form.step-form', keepAlive: true, permission: ['form'] }
      //     },
      //     {
      //       path: '/form/advanced-form',
      //       name: 'AdvanceForm',
      //       component: () => import('@/views/form/advancedForm/AdvancedForm'),
      //       meta: { title: 'menu.form.advanced-form', keepAlive: true, permission: ['form'] }
      //     }
      //   ]
      // },
      // // list
      // {
      //   path: '/list',
      //   name: 'list',
      //   component: RouteView,
      //   redirect: '/list/table-list',
      //   meta: { title: 'menu.list', icon: 'table', permission: ['table'] },
      //   children: [
      //     {
      //       path: '/list/table-list/:pageNo([1-9]\\d*)?',
      //       name: 'TableListWrapper',
      //       hideChildrenInMenu: true, // 强制显示 MenuItem 而不是 SubMenu
      //       component: () => import('@/views/list/TableList'),
      //       meta: { title: 'menu.list.table-list', keepAlive: true, permission: ['table'] }
      //     },
      //     {
      //       path: '/list/basic-list',
      //       name: 'BasicList',
      //       component: () => import('@/views/list/BasicList'),
      //       meta: { title: 'menu.list.basic-list', keepAlive: true, permission: ['table'] }
      //     },
      //     {
      //       path: '/list/card',
      //       name: 'CardList',
      //       component: () => import('@/views/list/CardList'),
      //       meta: { title: 'menu.list.card-list', keepAlive: true, permission: ['table'] }
      //     },
      //     {
      //       path: '/list/search',
      //       name: 'SearchList',
      //       component: () => import('@/views/list/search/SearchLayout'),
      //       redirect: '/list/search/article',
      //       meta: { title: 'menu.list.search-list', keepAlive: true, permission: ['table'] },
      //       children: [
      //         {
      //           path: '/list/search/article',
      //           name: 'SearchArticles',
      //           component: () => import('../views/list/search/Article'),
      //           meta: { title: 'menu.list.search-list.articles', permission: ['table'] }
      //         },
      //         {
      //           path: '/list/search/project',
      //           name: 'SearchProjects',
      //           component: () => import('../views/list/search/Projects'),
      //           meta: { title: 'menu.list.search-list.projects', permission: ['table'] }
      //         },
      //         {
      //           path: '/list/search/application',
      //           name: 'SearchApplications',
      //           component: () => import('../views/list/search/Applications'),
      //           meta: { title: 'menu.list.search-list.applications', permission: ['table'] }
      //         }
      //       ]
      //     }
      //   ]
      // },
      //
      // // profile
      // {
      //   path: '/profile',
      //   name: 'profile',
      //   component: RouteView,
      //   redirect: '/profile/basic',
      //   meta: { title: 'menu.profile', icon: 'profile', permission: ['profile'] },
      //   children: [
      //     {
      //       path: '/profile/basic',
      //       name: 'ProfileBasic',
      //       component: () => import('@/views/profile/basic'),
      //       meta: { title: 'menu.profile.basic', permission: ['profile'] }
      //     },
      //     {
      //       path: '/profile/advanced',
      //       name: 'ProfileAdvanced',
      //       component: () => import('@/views/profile/advanced/Advanced'),
      //       meta: { title: 'menu.profile.advanced', permission: ['profile'] }
      //     }
      //   ]
      // },
      //
      // // result
      // {
      //   path: '/result',
      //   name: 'result',
      //   component: RouteView,
      //   redirect: '/result/success',
      //   meta: { title: 'menu.result', icon: 'check-circle-o', permission: ['result'] },
      //   children: [
      //     {
      //       path: '/result/success',
      //       name: 'ResultSuccess',
      //       component: () => import(/* webpackChunkName: "result" */ '@/views/result/Success'),
      //       meta: { title: 'menu.result.success', keepAlive: false, hiddenHeaderContent: true, permission: ['result'] }
      //     },
      //     {
      //       path: '/result/fail',
      //       name: 'ResultFail',
      //       component: () => import(/* webpackChunkName: "result" */ '@/views/result/Error'),
      //       meta: { title: 'menu.result.fail', keepAlive: false, hiddenHeaderContent: true, permission: ['result'] }
      //     }
      //   ]
      // },
      //
      // // Exception
      // {
      //   path: '/exception',
      //   name: 'exception',
      //   component: RouteView,
      //   redirect: '/exception/403',
      //   meta: { title: 'menu.exception', icon: 'warning', permission: ['exception'] },
      //   children: [
      //     {
      //       path: '/exception/403',
      //       name: 'Exception403',
      //       component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
      //       meta: { title: 'menu.exception.not-permission', permission: ['exception'] }
      //     },
      //     {
      //       path: '/exception/404',
      //       name: 'Exception404',
      //       component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
      //       meta: { title: 'menu.exception.not-find', permission: ['exception'] }
      //     },
      //     {
      //       path: '/exception/500',
      //       name: 'Exception500',
      //       component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
      //       meta: { title: 'menu.exception.server-error', permission: ['exception'] }
      //     }
      //   ]
      // },
      //
      // // account
      // {
      //   path: '/account',
      //   component: RouteView,
      //   redirect: '/account/center',
      //   name: 'account',
      //   meta: { title: 'menu.account', icon: 'user', keepAlive: true, permission: ['user'] },
      //   children: [
      //     {
      //       path: '/account/center',
      //       name: 'center',
      //       component: () => import('@/views/account/center'),
      //       meta: { title: 'menu.account.center', keepAlive: true, permission: ['user'] }
      //     },
      //     {
      //       path: '/account/settings',
      //       name: 'settings',
      //       component: () => import('@/views/account/settings/Index'),
      //       meta: { title: 'menu.account.settings', hideHeader: true, permission: ['user'] },
      //       redirect: '/account/settings/basic',
      //       hideChildrenInMenu: true,
      //       children: [
      //         {
      //           path: '/account/settings/basic',
      //           name: 'BasicSettings',
      //           component: () => import('@/views/account/settings/BasicSetting'),
      //           meta: { title: 'account.settings.menuMap.basic', hidden: true, permission: ['user'] }
      //         },
      //         {
      //           path: '/account/settings/security',
      //           name: 'SecuritySettings',
      //           component: () => import('@/views/account/settings/Security'),
      //           meta: {
      //             title: 'account.settings.menuMap.security',
      //             hidden: true,
      //             keepAlive: true,
      //             permission: ['user']
      //           }
      //         },
      //         {
      //           path: '/account/settings/custom',
      //           name: 'CustomSettings',
      //           component: () => import('@/views/account/settings/Custom'),
      //           meta: { title: 'account.settings.menuMap.custom', hidden: true, keepAlive: true, permission: ['user'] }
      //         },
      //         {
      //           path: '/account/settings/binding',
      //           name: 'BindingSettings',
      //           component: () => import('@/views/account/settings/Binding'),
      //           meta: { title: 'account.settings.menuMap.binding', hidden: true, keepAlive: true, permission: ['user'] }
      //         },
      //         {
      //           path: '/account/settings/notification',
      //           name: 'NotificationSettings',
      //           component: () => import('@/views/account/settings/Notification'),
      //           meta: {
      //             title: 'account.settings.menuMap.notification',
      //             hidden: true,
      //             keepAlive: true,
      //             permission: ['user']
      //           }
      //         }
      //       ]
      //     }
      //   ]
      // },
      //
      // // other
      // {
      //   path: '/other',
      //   name: 'otherPage',
      //   component: RouteView,
      //   meta: { title: '其他组件', icon: 'slack', permission: [ 'dashboard' ] },
      //   redirect: '/other/icon-selector',
      //   children: [
      //     {
      //       path: '/other/icon-selector',
      //       name: 'TestIconSelect',
      //       component: () => import('@/views/other/IconSelectorView'),
      //       meta: { title: 'IconSelector', icon: 'tool', keepAlive: true, permission: [ 'dashboard' ] }
      //     },
      //     {
      //       path: '/other/list',
      //       component: RouteView,
      //       meta: { title: '业务布局', icon: 'layout', permission: [ 'support' ] },
      //       redirect: '/other/list/tree-list',
      //       children: [
      //         {
      //           path: '/other/list/tree-list',
      //           name: 'TreeList',
      //           component: () => import('@/views/other/TreeList'),
      //           meta: { title: '树目录表格', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/edit-table',
      //           name: 'EditList',
      //           component: () => import('@/views/other/TableInnerEditList'),
      //           meta: { title: '内联编辑表格', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/user-list',
      //           name: 'UserList',
      //           component: () => import('@/views/other/UserList'),
      //           meta: { title: '用户列表', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/role-list',
      //           name: 'RoleList',
      //           component: () => import('@/views/other/RoleList'),
      //           meta: { title: '角色列表', keepAlive: true }
      //         },
      //         // {
      //         //   path: '/other/list/system-role',
      //         //   name: 'SystemRole',
      //         //   component: () => import('@/views/role/RoleList'),
      //         //   meta: { title: '角色列表2', keepAlive: true }
      //         // },
      //         {
      //           path: '/other/list/permission-list',
      //           name: 'PermissionList',
      //           component: () => import('@/views/other/PermissionList'),
      //           meta: { title: '权限列表', keepAlive: true }
      //         }
      //       ]
      //     }
      //   ]
      // }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },
  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  },
  {
    path: '/wxwork1',
    component: () => import(/* webpackChunkName: "user" */ '@/views/wxwork/projects/index')
  },
]
