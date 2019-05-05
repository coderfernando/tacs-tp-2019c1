<template>
<div class="places">
  <ol>
    <li v-for="venue in venues" v-bind:key="venue.foursquareId">{{venue.latitude}}, {{venue.longitude}},{{venue.title}},{{venue.address}},{{venue.foursquareId}}</li>
  </ol>
</div>
</template>

<script>
import axios from 'axios'
var userLat
var userLon
export default {
  name: 'Places',
  data () {
    return {
      venues: [],
      response: [],
      errors: []
    }
  },
  getPlaces () {
    axios.get(`/api/places`, {
      params: {
        lat: userLat,
        lon: userLon,
        radius: 1000
      }
    })
      .then(response => {
        this.venues = response.data.map(p => getVenue(p))
        const venuesToAdd = this.venues.filter(v => !this.venues.some(vd => vd.foursquareId === v.foursquareId))
        this.venues.push(...venuesToAdd)
      })
      .catch(e => {
        this.errors.push(e)
      })
  },
  getLocation () {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        function (position) {
          userLat = position.coords.latitude
          userLon = position.coords.longitude
        },
        function (error) {
          alert(error.message)
        })
    } else {
      this.error = 'Geolocation is not supported.'
    }
  }
}
function getVenue (forsquarevenue) {
  const p = {latitude: forsquarevenue.location.lat,
    longitude: forsquarevenue.location.lng,
    title: forsquarevenue.name,
    address: forsquarevenue.location.address,
    foursquareId: forsquarevenue.id}
  return p
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
