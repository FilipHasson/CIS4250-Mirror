/**
 * mutations.js
 * contains vuex mutation definitions which serve to "mutate" the shared
 * application state
 */

import Vue from 'vue'
import {
  logCache,
  logError
} from '@/scripts/helpers/log'

// Session ---------------------------------------------------------------------

function login (state, payload) {
  // note: just mocking logging in at the moment
  const userId = () => {
    switch (payload.user_name) {
      case 'jessy':
        return 1
      case 'filip':
        return 2
      case 'grant':
        return 3
      default:
        throw new Error('invalid username')
    }
  }
  state.session.user_id = userId
  localStorage.setItem('user_id', userId)
}

function logout (state) {
  state.session.user_id = null
  localStorage.removeItem('user_id')
}

function showModal (state, modal) {
  const registeredModals = state.registry.modals
  modal = modal.toLowerCase()
  if (!registeredModals.includes(modal)) {
    const validModals = registeredModals.join(', ')
    throw new Error(
      `${modal} modal is invalid, must be one of [${validModals}]`
    )
  }
  state.session.shown_modal = modal
}

function closeModal (state) {
  state.session.shown_modal = null
}

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
  login,
  logout,
  showModal,
  closeModal,
  cacheAccount,
  cacheFood
}
