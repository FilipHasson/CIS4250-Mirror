// Vue.js Libraries
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

// Helper Scripts
import '@/scripts/helpers/config'
import '@/scripts/helpers/fonts'
import '@/scripts/helpers/log'
import '@/scripts/helpers/storage'
import { routes, before, after } from '@/scripts/helpers/routes'

// Vuex Store
import actions from '@/scripts/vuex/actions'
import getters from '@/scripts/vuex/getters'
import mutations from '@/scripts/vuex/mutations'
import state from '@/scripts/vuex/state'

// Components
import App from '@/components/app'

Vue.use(VueRouter)
Vue.use(Vuex)

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach(before)
router.afterEach(after)

Vuex.Store.prototype.$router = router
const store = new Vuex.Store({
  strict: true,
  actions,
  getters,
  mutations,
  state
})

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
