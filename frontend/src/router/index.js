import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/pages/HomePage'
import PlacesPage from '@/pages/PlacesPage'
import ListsPage from '@/pages/ListsPage'

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
      component: PlacesPage
    },
    {
      path: '/lists',
      name: 'Lists',
      component: ListsPage
    }
  ]
})
