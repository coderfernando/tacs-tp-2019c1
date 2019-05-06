<template>
<div class="places">
  <h1>PLACES</h1>
  <places-list position=localPosition></places-list>
</div>
</template>

<script>
import PlacesList from '@/components/PlacesList'
export default {
  name: 'Places',
  data: function () {
    return {
      response: [],
      errors: []
    }
  },
  components: {
    'places-list': PlacesList
  }/*,
  method: {
    localPosition: navigator.geolocation.getCurrentPosition(
      function (position) {}
    )
  }
  */
}
</script>

<style>
</style>
