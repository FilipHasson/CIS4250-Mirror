/**
 * router.js
 * contains listing of vue-router page routes
 */

import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const REDIRECT_ROUTE = '/'

// note: entries listed here are also placed in nav bar
export const routes = [{
  component: () => import('@/pages/home'),
  path: '/',
  name: 'YUMM',
  meta: {
    is_public: true
  }
},
{
  component: () => import('@/pages/explore'),
  path: '/explore',
  name: 'Explore',
  meta: {
    is_public: true
  }
},
{
  component: () => import('@/pages/recipes'),
  path: '/recipes',
  name: 'Recipes',
  meta: {
    is_public: false
  }
},
{
  component: () => import('@/pages/goals'),
  path: '/goals',
  name: 'Goals',
  meta: {
    is_public: false
  }
},
{
  component: () => import('@/pages/plan'),
  path: '/plan',
  name: 'Meal Plan',
  meta: {
    is_public: false
  }
},
{
  path: '*',
  redirect: REDIRECT_ROUTE,
  meta: {}
}
]

export const publicRoutes = routes.filter(r => r.meta.is_public === true) || []
export const privateRoutes = routes.filter(r => r.meta.is_public === false) || []

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

function authGuard (to, from, next) {
  const isLoggedIn = localStorage.getItem('user_id') !== null
  const isPublic = to.meta.is_public === true
  return (isPublic || isLoggedIn) ? next() : next(REDIRECT_ROUTE)
}
router.beforeEach(authGuard)

function setTitle (to) {
  document.title = to.path === '/' ? 'YUMM' : `YUMM | ${to.name}`
}
router.afterEach(setTitle)

export default router
