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
      in_header: true,
      is_public: true
    }
  },
  {
    component: () => import('@/components/pages/explore'),
    path: '/explore',
    name: 'Explore',
    meta: {
      in_header: true,
      is_public: true
    }
  },
  {
    component: () => import('@/components/pages/recipes'),
    path: '/recipes',
    name: 'Recipes',
    meta: {
      in_header: true,
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
      in_header: true,
      is_public: false
    }
  },
  {
    component: () => import('@/components/pages/new_recipe'),
    path: '/recipes/new',
    name: 'Create a Recipe',
    meta: {
      in_header: false,
      is_public: false
    }
  },
  {
    path: '*',
    redirect: REDIRECT_ROUTE,
    meta: {}
  }
]

export function before (to, from, next) {
  const isLoggedIn = localStorage.getItem('user_id') !== null
  const isPublic = to.meta.is_public === true
  return isPublic || isLoggedIn ? next() : next(REDIRECT_ROUTE)
}
export function after (to) {
  document.title = to.path === '/' ? 'YUMM' : `YUMM | ${to.name}`
}
