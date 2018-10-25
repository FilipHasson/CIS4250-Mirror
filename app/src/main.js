import Vue from 'vue'
import App from './app'

import store from '@/utils/store'
import router from '@/utils/router'
import '@/utils/config'
import '@/utils/fonts'
import '@/utils/persist'

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
