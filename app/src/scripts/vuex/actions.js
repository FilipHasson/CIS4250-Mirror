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

function tryLogin (context, payload) {
  context.commit('login', payload.username)
  context.commit('closeModal')
}

function tryLogout ({ commit, state }) {
  commit('logout')
  commit('closeModal')
  // redirect to home page if current page required login
  if (!this.$router.currentRoute.meta.is_public) {
    this.$router.push('/')
  }
}

// Account ---------------------------------------------------------------------

async function fetchAccounts ({ dispatch }) {
  const accountIds = await api('/accounts')
  for (const accountId of accountIds) {
    dispatch('fetchAccount', accountId)
  }
  return accountIds
}

async function fetchAccount ({ commit, state }, id) {
  if (state.model_data.account[id]) {
    logRetrieval(`account(${id})`)
    return state.model_data.account[id]
  }
  const response = await api(`/account/${id}`)
  const accountData = response.data
  return commit('cacheAccount', accountData)
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
  if (state.model_data.food[id]) {
    logRetrieval(`food(${id})`)
    const accountId = state.model_data.food[id].account_id
    return dispatch('fetchAccount', accountId)
  }
  const response = await api(`/food/${id}`)
  const foodData = response.data
  commit('cacheFood', foodData)
  const accountId = state.model_data.food[id].account_id
  dispatch('fetchAccount', accountId)
  return foodData
}

// -----------------------------------------------------------------------------

export default {
  tryLogin,
  tryLogout,
  fetchAccounts,
  fetchAccount,
  fetchFoods,
  fetchFood
}
