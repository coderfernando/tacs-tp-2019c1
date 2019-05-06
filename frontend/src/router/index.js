import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/pages/HomePage'
import Places from '@/pages/Places'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home Page',
      component: HomePage
    },
    {
      path: '/places',
      name: 'Places',
      component: Places
    }
  ]
})
