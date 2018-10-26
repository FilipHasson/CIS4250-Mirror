/**
 * getters.js
 * contains vuex getter definitions which serve as computed properties for the
 * shared application state
 */

// Session ---------------------------------------------------------------------

function isLoggedIn (state) {
  return state.session.user_id !== null
}

function isModalShown (state) {
  return state.session.shown_modal !== null
}

function userName (state) {
  const userId = state.session.user_id
  try {
    return userId ? state.models.account[userId].username : null
  } catch (e) {
    return null
  }
}

function userEmail (state) {
  const userId = state.session.user_id
  try {
    return userId ? state.models.account[userId].email : null
  } catch (e) {
    return null
  }
}

// Account ---------------------------------------------------------------------

function getAccountModel (state) {
  return id => (state.models.account[id] || null)
}

// Food ------------------------------------------------------------------------

function getFoodModel (state) {
  return id => state.models.food[id]
}

function latestFoodIds (state) {
  const data = state.models.food
  let keys = Object.keys(data).map(Number)
  keys.sort((a, b) => data[a].time_created - data[b].time_created)
  return keys.slice(0, 100)
}

// -----------------------------------------------------------------------------

export default {
  isLoggedIn,
  isModalShown,
  userName,
  userEmail,
  getAccountModel,
  getFoodModel,
  latestFoodIds
}
