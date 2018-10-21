import Vue from 'vue'
import App from '@/app.vue'

import router from '@/router'
import store from '@/store'
import '@/fonts'

Vue.config.productionTip = false

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
