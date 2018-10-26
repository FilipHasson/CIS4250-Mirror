/**
 * log.js
 * a collection of helper functions used for logging data to the console
 */

import { IS_DEV_DEPLOYMENT } from '@/scripts/helpers/config'

// note: with the exception of logError, all other log functions will only log
// when deployed in the development environment and will only work in Chrome

/* eslint-disable no-console */

export function logRequest (entity, ...msg) {
  if (!IS_DEV_DEPLOYMENT) return
  const text = `%c[REQUEST]%c ${entity}` + (msg.length ? ': ' + msg.join(', ') : '')
  console.debug(text, 'color: green; font-weight: bold', 'color: initial')
}

export function logRetrieval (entity, ...msg) {
  if (!IS_DEV_DEPLOYMENT) return
  const text = `%c[RETRIEVE]%c ${entity}` + (msg.length ? ': ' + msg.join(', ') : '')
  console.debug(text, 'color: violet; font-weight: bold', 'color: initial')
}

export function logCache (entity, ...msg) {
  if (!IS_DEV_DEPLOYMENT) return
  const text = `%c[CACHED]%c ${entity}` + (msg.length ? ': ' + msg.join(', ') : '')
  console.debug(text, 'color: blue; font-weight: bold', 'color: initial')
}

export function logError (entity, ...msg) {
  let text = `[ERROR] ${entity}` + (msg ? ': ' + msg.join(', ') : '')
  console.error(text)
}
