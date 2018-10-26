/**
 * getters.js
 * contains vuex getter definitions which serve as computed properties for the
 * shared application state
 */

// Session ---------------------------------------------------------------------

function isLoggedIn (state) {
  return state.session.user_id !== null
}

function getUserModel (state) {
  const userId = state.session.user_id
  return userId ? state.model_data.account[userId] : null
}

function isModalShown (state) {
  return state.session.shown_modal !== null
}

function currentModal (state) {
  return state.session.shown_modal
}

// Account ---------------------------------------------------------------------

function getAccountModel (state) {
  return id => state.model_data.account[id]
}

// Food ------------------------------------------------------------------------

function getFoodModel (state) {
  return id => state.model_data.food[id]
}

function getLatestFoodIds (state) {
  return payload => {
    const category = (payload ? payload.category : null) || null
    const count = (payload ? payload.count : null) || 100
    let data
    if (category) {
      data = state.model_data.food.filter(
        food => food.categories.includes(category)
      )
    } else {
      data = state.model_data.food
    }
    let keys = Object.keys(data).map(Number)
    keys.sort((a, b) => data[a].time_created - data[b].time_created)
    return keys.slice(0, count)
  }
}

// -----------------------------------------------------------------------------

export default {
  isLoggedIn,
  isModalShown,
  currentModal,
  getUserModel,
  getAccountModel,
  getFoodModel,
  getLatestFoodIds
}
