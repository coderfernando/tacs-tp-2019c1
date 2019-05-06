<template>
<ol>
  <li v-for="venue in venues" v-bind:key="venue.foursquareId">
      {{venue.latitude}}, {{venue.longitude}},{{venue.title}},{{venue.address}},{{venue.foursquareId}}
  </li>
</ol>
</template>

<script>
import axios from 'axios'
export default {
  name: 'PlacesList',
  props: {position: Object},
  data: function () {
    return {
      venues: [],
      response: [],
      errors: [],
      positionLocal: this.position
    }
  },
  methods: {
    getPlaces () {
      axios.get('localhost:8080/api/places', {
        params: {
          lat: this.positionLocal.coords.latitude,
          lon: this.positionLocal.coords.longitude,
          radius: 1000
        }
      })
        .then(response => {
          this.venues = response.data.map(p => {
            return {
              latitude: p.location.lat,
              longitude: p.location.lng,
              title: p.name,
              address: p.location.address,
              foursquareId: p.id
            }
          })
          const venuesToAdd = this.venues.filter(v => !this.venues.some(vd => vd.foursquareId === v.foursquareId))
          this.venues.push(...venuesToAdd)
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  },
  beforeMount () {
    this.positionLocal ? this.getPlaces() : console.log('Not yet')
  }
}
</script>

<style>

</style>
