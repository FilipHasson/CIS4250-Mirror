/**
 * actions.js
 * contains vuex action definitions which evaluate application "business logic"
 * and asynchronously commit mutations
 */

import axios from 'axios'
import { API } from '@/scripts/helpers/config'
import { logRetrieval, logRequest } from '@/scripts/helpers/log'

const api = axios.create({
  baseURL: API,
  timeout: 1000,
  responseType: 'json'
})
api.interceptors.request.use(
  (response) => {
    logRequest(response.url)
    return response
  },
  (error) => {
    console.log(error)
    return Promise.reject(error)
  }
)

// Session ---------------------------------------------------------------------

async function tryLogin ({ commit, dispatch }, payload) {
  const getUserId = () => {
    switch (payload.username) {
      case 'jessy':
        return 1
      case 'filip':
        return 2
      case 'grant':
        return 3
      default:
        return null
    }
  }
  const userId = getUserId()
  if (userId == null) {
    return Promise.reject(Error('Login rejected'))
  }
  await dispatch('fetchAccount', userId)
  commit('login', userId)
  commit('closeModal')
}

function tryLogout ({ commit }) {
  commit('logout')
  commit('closeModal')
  // redirect to home page if current page required login
  if (!this.$router.currentRoute.meta.is_public) {
    this.$router.push('/')
  }
}

async function fetchUserFoods ({ dispatch }) {
  const userModel = await dispatch('fetchUserModel')
  const foodIds = userModel.recipe_ids
  const foodModels = foodIds.map(id => dispatch('fetchFood', id))
  return Promise.all(foodModels)
}

// Account ---------------------------------------------------------------------

async function fetchAccounts ({ dispatch }) {
  const accountIds = await api('/accounts')
  for (const accountId of accountIds) {
    dispatch('fetchAccount', accountId)
  }
  return accountIds
}

async function fetchAccount ({ commit, getters }, id) {
  let accountModel = getters.getAccountModel(id)
  if (accountModel) {
    logRetrieval(`account(${id})`)
    return accountModel
  }
  const response = await api(`/account/${id}`)
  accountModel = response.data
  return commit('cacheAccount', accountModel)
}

// Food  -----------------------------------------------------------------------

async function fetchFoods ({ dispatch }) {
  const response = await api('/foods')
  const foodIds = response.data
  for (const foodId of foodIds) {
    dispatch('fetchFood', foodId)
  }
  return foodIds
}

async function fetchFood ({ commit, dispatch, state }, id) {
  if (state.models.food[id]) {
    logRetrieval(`food(${id})`)
    const accountId = state.models.food[id].account_id
    return dispatch('fetchAccount', accountId)
  }
  const response = await api(`/food/${id}`)
  const foodData = response.data
  commit('cacheFood', foodData)
  const accountId = state.models.food[id].account_id
  dispatch('fetchAccount', accountId)
  return foodData
}

// -----------------------------------------------------------------------------

export default {
  tryLogin,
  tryLogout,
  fetchUserFoods,
  fetchAccounts,
  fetchAccount,
  fetchFoods,
  fetchFood
}
