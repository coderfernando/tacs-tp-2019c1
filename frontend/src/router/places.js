import Vue from 'vue'
import Router from 'vue-router'
import Places from '@/components/Places'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/places',
      name: 'Places',
      component: Places
    }
  ]
})
