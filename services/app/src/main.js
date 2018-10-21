import Vue from 'vue'
import App from './app'

import store from '@/utils/store'
import router from '@/utils/router'
import '@/utils/fonts'

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
