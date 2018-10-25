/**
 * actions.js
 * contains vuex action definitions which evaluate application "business logic"
 * and asynchronously commit mutations
 */

import { API } from '@/utils/config'
import { logRetrieval, logRequest, logError } from '@/utils/log'

// Account ---------------------------------------------------------------------

function fetchAccounts ({ dispatch }) {
  return fetch(`${API}/accounts`)
    .then(response => response.json())
    .catch(error => logError('accounts', error))
    .then(ids => {
      logRequest('accounts')
      for (const id of ids) {
        dispatch('fetchAccount', id)
      }
      return ids
    })
}

function fetchAccount ({ commit, state }, id) {
  if (state.model_data.account[id]) {
    logRetrieval(`account(${id})`)
    return state.model_data.account[id]
  } else {
    logRequest(`account(${id})`)
    return fetch(`${API}/account/${id}`)
      .then(response => response.json())
      .catch(error => logError(`account(${id})`, error))
      .then(data => {
        commit('cacheAccount', data)
        return data
      })
  }
}

// Food  -----------------------------------------------------------------------

function fetchFoods ({ dispatch }) {
  return fetch(`${API}/foods`)
    .then(response => response.json())
    .then(ids => {
      logRequest('foods')
      for (const id of ids) {
        dispatch('fetchFood', id)
      }
      return ids
    })
    .catch(error => logError('foods', error))
}

function fetchFood ({ commit, dispatch, state }, id) {
  if (state.model_data.food[id]) {
    logRetrieval(`food(${id})`)
    const accountId = state.model_data.food[id].account_id
    dispatch('fetchAccount', accountId)
  } else {
    logRequest(`food(${id})`)
    return fetch(`${API}/food/${id}`)
      .then(response => response.json())
      .catch(error => logError(`food(${id})`, error))
      .then(data => {
        commit('cacheFood', data)
        const accountId = state.model_data.food[id].account_id
        dispatch('fetchAccount', accountId)
        return data
      })
  }
}

// -----------------------------------------------------------------------------

export default {
  fetchAccounts,
  fetchAccount,
  fetchFoods,
  fetchFood
}
