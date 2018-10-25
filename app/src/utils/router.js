import Vue from 'vue'
import Router from 'vue-router'
import { logError } from '@/utils/log'
import { isLoggedIn } from '@/utils/persist'

Vue.use(Router)

// Entries listed here are also placed in nav bar
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
    path: '*',
    redirect: '/'
  },
  {
    component: () => import('@/pages/account'),
    path: '/account',
    name: 'Account'
  }
]

// function authGuard (to, from, next) {
//   const publicPages = ['/', '/explore', '/recipes']
//   const authRequired = !publicPages.includes(to.path)
//   if (authRequired && !isLoggedIn()) {
//     logError(`not authorized to visit "${to.path}"`, 'redirecting')
//   }
//   return (authRequired && !isLoggedIn()) ? next('/') : next()
// }

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// router.beforeEach(authGuard)

export default router
