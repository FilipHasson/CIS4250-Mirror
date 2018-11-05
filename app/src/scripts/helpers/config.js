/**
 * config.js
 * configures application behaviour based on the current deployment enviroment
 */

import Vue from 'vue'

// note: NODE_ENV is set to development when running from the development server
export const IS_DEV_DEPLOYMENT = process.env.NODE_ENV === 'development'
export const API = '/api' // IS_DEV_DEPLOYMENT ? '/mock_api' : '/api'

Vue.config.productionTip = IS_DEV_DEPLOYMENT
