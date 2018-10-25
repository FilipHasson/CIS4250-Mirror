/**
 * mutations.js
 * contains vuex mutation definitions which serve to "mutate" the shared
 * application state
 */

import Vue from 'vue'
import { logCache, logError } from '@/utils/log'

// Account ---------------------------------------------------------------------

function cacheAccount (state, payload) {
  storeModelData(state, payload, 'account')
}

// Food  -----------------------------------------------------------------------

function cacheFood (state, payload) {
  storeModelData(state, payload, 'food')
}

// -----------------------------------------------------------------------------

function storeModelData (state, payload, entity) {
  if (!state.model_data[entity]) {
    logError(`${entity}(${payload.id})`, `can't commit to model_data.${entity}`)
  } else if (!payload) {
    logError(`${entity}(${payload.id})`, "can't commit without payload")
  } else if (!payload.id) {
    logError(`${entity}(${payload.id})`, "can't commit without id")
  } else {
    Vue.set(state.model_data[entity], payload.id, payload)
    logCache(`${entity}(${payload.id})`)
  }
}

export default {
  cacheAccount,
  cacheFood
}
