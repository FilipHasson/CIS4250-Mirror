/**
 * router.js
 * contains listing of vue-router page routes
 */

const REDIRECT_ROUTE = '/'

// note: entries listed here are also placed in nav bar
export const routes = [
  {
    component: () => import('@/components/pages/home'),
    path: '/',
    name: 'YUMM',
    meta: {
      is_public: true
    }
  },
  {
    component: () => import('@/components/pages/explore'),
    path: '/explore',
    name: 'Explore',
    meta: {
      is_public: true
    }
  },
  {
    component: () => import('@/components/pages/recipes'),
    path: '/recipes',
    name: 'Recipes',
    meta: {
      is_public: false
    }
  },
  {
    component: () => import('@/components/pages/goals'),
    path: '/goals',
    name: 'Goals',
    meta: {
      is_public: false
    }
  },
  {
    component: () => import('@/components/pages/plan'),
    path: '/plan',
    name: 'Meal Plan',
    meta: {
      is_public: false
    }
  },
  {
    component: () => import('@/components/pages/health'),
    path: '/health',
    name: 'Health',
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
export const privateRoutes =
  routes.filter(r => r.meta.is_public === false) || []

export function before (to, from, next) {
  const isLoggedIn = localStorage.getItem('user_id') !== null
  const isPublic = to.meta.is_public === true
  return isPublic || isLoggedIn ? next() : next(REDIRECT_ROUTE)
}
export function after (to) {
  document.title = to.path === '/' ? 'YUMM' : `YUMM | ${to.name}`
}
