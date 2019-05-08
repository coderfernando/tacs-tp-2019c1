<template>
<div class="places">
  <h1>PLACES</h1>
  <places-list></places-list>
</div>
</template>

<script>
import PlacesList from '@/components/PlacesList'
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
    'places-list': PlacesList
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

<style>
</style>
