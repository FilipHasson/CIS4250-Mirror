import Vue from 'vue'
import App from './app'

import '@/scripts/helpers/config'
import '@/scripts/helpers/fonts'
import '@/scripts/helpers/storage'
import store from '@/scripts/vue/store'
import router from '@/scripts/vue/router'

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
