import Vue from 'vue'
import Vuex from 'vuex'

import actions from '@/utils/actions'
import getters from '@/utils/getters'
import mutations from '@/utils/mutations'
import state from '@/utils/state'

Vue.use(Vuex)

export default new Vuex.Store({
  strict: true,
  actions,
  getters,
  mutations,
  state
})
