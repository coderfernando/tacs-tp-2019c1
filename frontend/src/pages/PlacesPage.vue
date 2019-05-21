<template>
  <div class="places">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div class="row">
      <places-list></places-list>
    </div>
  </div>
</template>

<script>
import PlacesList from '@/components/PlacesList'
import AppMenu from '@/components/AppMenu'

export default {
  name: 'Places',
  data: function () {
    return {
      response: [],
      errors: [],
      ubicacion: ''
    }
  },
  components: {
    'places-list': PlacesList,
    'app-menu': AppMenu
  }/* ,
  methods: {
    getPosition: function () {
      var ubicacion
      navigator.geolocation.getCurrentPosition(
        function (location) { return location })
    }
  },
  created () {
    this.ubicacion = this.getPosition()
  } */
}
</script>
