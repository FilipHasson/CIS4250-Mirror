import Vue from 'vue'
import Router from 'vue-router'

import HomePage from '@/pages/home'
import ExplorePage from '@/pages/explore'
import RecipesPage from '@/pages/recipes'
import GoalsPage from '@/pages/goals'
import PlanPage from '@/pages/plan'
import AccountPage from '@/pages/account'

Vue.use(Router)

// Extries listed here are also placed in nav bar
export const routes = [
  {
    component: HomePage,
    path: '/',
    name: 'YUMM'
  },
  {
    component: ExplorePage,
    path: '/explore',
    name: 'Explore'
  },
  {
    component: RecipesPage,
    path: '/recipes',
    name: 'Recipes'
  },
  {
    component: GoalsPage,
    path: '/goals',
    name: 'Goals'
  },
  {
    path: '/plan',
    component: PlanPage,
    name: 'Meal Plan'
  },
  {
    path: '/account',
    component: AccountPage,
    name: 'Account'
  }
]

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
