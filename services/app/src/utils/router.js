import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// Extries listed here are also placed in nav bar
export const routes = [
  {
    component: () => import('@/pages/home'),
    path: '/',
    name: 'YUMM'
  },
  {
    component: () => import('@/pages/explore'),
    path: '/explore',
    name: 'Explore'
  },
  {
    component: () => import('@/pages/recipes'),
    path: '/recipes',
    name: 'Recipes'
  },
  {
    component: () => import('@/pages/goals'),
    path: '/goals',
    name: 'Goals'
  },
  {
    component: () => import('@/pages/plan'),
    path: '/plan',
    name: 'Meal Plan'
  },
  {
    component: () => import('@/pages/account'),
    path: '/account',
    name: 'Account'
  }
]

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
