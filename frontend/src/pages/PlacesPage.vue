<template>
  <div class="places">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div v-if="loading" class="row">
      <img class="loading-image" src="/static/img/loading.gif" />
    </div>
    <div v-if="!loading" class="row">
      <ul id="placesList" class="col-sm items-list">
        <li v-for="venue in venues" v-bind:key="venue.foursquareId">
          <div>
            <h4>{{venue.title}}</h4>
            <h6>{{venue.address}}</h6>
          </div>
          <a v-on:click="openAddModal(venue)">Add to list</a>
        </li>
      </ul>
    </div>

    <b-modal ref="AddToListModal" hide-footer title="Add place to list">
        <div class="d-block">
          <div>Place</div>
          <h4>{{selectedVenue.title}}</h4>
          <div>Lists</div>
          <ul id="addToListModalLists" class="col-sm">
            <li v-for="l in lists" v-bind:key="l.id">
              <span>{{l.name}}</span>
              <a v-on:click="addPlaceToList(l.id)" v-if="!listHasSelectedPlace(l)">Add place</a>
              <a v-if="listHasSelectedPlace(l)" class="added">Already added</a>
            </li>
          </ul>
        </div>
        <div class="d-block float-right">
          <b-button variant="light" @click="closeAddModal">Close</b-button>
        </div>
      </b-modal>
  </div>
</template>

<script>
import AppMenu from '@/components/AppMenu'
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
  name: 'Places',
  data: function () {
    return {
      venues: [],
      loading: true,
      selectedVenue: {},
      loadingLists: true,
      lists: []
    }
  },
  components: {
    'app-menu': AppMenu
  },
  methods: {
    async getPlaces () {
      this.loading = true
      var position = await currentCoordinates()
      console.log('Requesting places with position', position)
      axios.get('/api/places', {
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
    },
    openAddModal: function (venue) {
      this.selectedVenue = venue
      this.$refs['AddToListModal'].show()
      this.getLists()
    },
    closeAddModal: function () {
      this.$refs['AddToListModal'].hide()
    },
    getLists: function () {
      var self = this
      self.loadingLists = true
      axios.get('/api/me/lists').then(function (response) {
        console.log(response.data)
        self.lists = response.data
        self.loadingLists = false
      }).catch(function (error) {
        self.loadingLists = false
        console.log(error)
        alert('Ocurrió un error. Intente de nuevo por favor.')
      })
    },
    addPlaceToList: function (listId) {
      var self = this
      axios.post('/api/me/lists/' + listId + '/add', this.selectedVenue).then(function (response) {
        console.log(response.data)
        self.getLists()
      }).catch(function (error) {
        console.log(error)
        alert('Ocurrió un error. Intente de nuevo por favor.')
      })
    },
    listHasSelectedPlace: function (list) {
      var self = this
      var hasPlace = false
      list.places.forEach(function (place) {
        if (place.foursquareId === self.selectedVenue.foursquareId) {
          hasPlace = true
        }
      })
      return hasPlace
    }
  },
  created () {
    this.getPlaces()
  }
}
</script>
