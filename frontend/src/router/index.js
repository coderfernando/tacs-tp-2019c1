import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/pages/HomePage'
import PlacesPage from '@/pages/PlacesPage'
import ListsPage from '@/pages/ListsPage'
import BootstrapVue from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import '../../static/css/main.css'

Vue.use(Router)
Vue.use(BootstrapVue)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
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
