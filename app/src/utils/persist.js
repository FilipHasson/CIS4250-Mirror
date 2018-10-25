Storage.prototype.setObject = function (key, value) {
  this.setItem(key, JSON.stringify(value))
}

Storage.prototype.getObject = function (key) {
  const value = this.getItem(key)
  return value && JSON.parse(value)
}

export function getUserId () {
  return localStorage.getItem('user_id')
}

export function isLoggedIn () {
  return !!getUserId()
}
