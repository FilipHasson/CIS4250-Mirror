import Vue from 'vue'
import Vuex from 'vuex'

import actions from '@/scripts/vuex/actions'
import getters from '@/scripts/vuex/getters'
import mutations from '@/scripts/vuex/mutations'
import state from '@/scripts/vuex/state'

Vue.use(Vuex)

export default new Vuex.Store({
  strict: true,
  actions,
  getters,
  mutations,
  state
})
