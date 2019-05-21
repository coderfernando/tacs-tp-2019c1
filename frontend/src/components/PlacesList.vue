<template>
<div id="placesListComponent">
  <div v-if="loading" class="row">
    <img class="loading-image" src="/static/img/loading.gif" />
  </div>
  <ul v-if="!loading" id="placesList" class="col-sm items-list">
    <li v-for="venue in venues" v-bind:key="venue.foursquareId">
        <h4>{{venue.title}}</h4>
        <h6>{{venue.address}}</h6>
    </li>
  </ul>
</div>
</template>

<script>
import axios from 'axios'

export function currentCoordinates () {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      ({ coords }) => resolve(coords),
      error => reject(error)
    )
  })
}

export default {
  name: 'PlacesList',
  data: function () {
    return {
      venues: [],
      response: [],
      errors: [],
      loading: true
      // ,positionLocal: this.position
    }
  },
  methods: {
    async getPlaces () {
      this.loading = true
      var position = await currentCoordinates()
      console.log('Requesting places with position', position)
      axios.get('http://localhost:8080/api/places', {
        params: {
          lat: position.latitude,
          lon: position.longitude,
          radius: 1000
        }
      })
        .then(response => {
          console.log('Response', response)
          this.loading = false
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
          this.loading = false
          console.log('ERROR', e)
          this.errors.push(e)
        })
    }
  },
  created () {
    this.getPlaces()
  }
}
</script>
