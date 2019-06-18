<template>
  <div class="places">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div v-if="loading" class="row">
      <img class="loading-image" src="/static/img/loading.gif" />
    </div>
    <div v-if="!loading" class="row">
      <b-container class="bv-row">
        <b-row id="places-filters">
          <div>
            <b-form-input
              v-model="termino"
              name="termino"
              placeholder="Enter search term"
            ></b-form-input>
          </div>
          <b-col>
            <b-form-select
              v-model="selselected"
              :options="options"
            ></b-form-select>
          </b-col>
          <b-col>
            <button type="button" class="btn btn-primary" @click="getPlaces">
              Search
            </button>
          </b-col>
        </b-row>
        <b-row>
          <ul id="placesList" class="col-sm items-list">
            <li v-for="venue in venues" :key="venue.foursquareId">
              <b-row>
                <h4>{{ venue.title }}</h4>
                <h6>{{ venue.address }}</h6>
              </b-row>
              <a @click="openAddModal(venue)">Add to list</a>
            </li>
          </ul>
        </b-row>
      </b-container>
    </div>

    <b-modal ref="AddToListModal" hide-footer title="Add place to list">
      <div class="d-block">
        <div>Place</div>
        <h4>{{ selectedVenue.title }}</h4>
        <div>Lists</div>
        <ul id="addToListModalLists" class="col-sm">
          <li v-for="l in lists" :key="l.id">
            <span>{{ l.name }}</span>
            <a v-if="!listHasSelectedPlace(l)" @click="addPlaceToList(l.id)"
              >Add place</a
            >
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
import AppMenu from "@/components/AppMenu";
import axios from "axios";

export function currentCoordinates() {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      ({ coords }) => resolve(coords),
      error => reject(error)
    );
  });
}

export default {
  name: "Places",
  components: {
    "app-menu": AppMenu
  },
  data: function() {
    return {
      venues: [],
      loading: false,
      selectedVenue: {},
      loadingLists: true,
      lists: [],
      selselected: null,
      termino: "",
      options: [
        { value: null, text: "Use Current Location" },
        { value: "Buenos Aires", text: "Buenos Aires" },
        { value: "Catamarca", text: "Catamarca" },
        { value: "Chaco", text: "Chaco" },
        { value: "Chubut", text: "Chubut" },
        { value: "Cordoba", text: "Cordoba" },
        { value: "Corrientes", text: "Corrientes" },
        { value: "Entre Rios", text: "Entre Rios" },
        { value: "Formosa", text: "Formosa" },
        { value: "Jujuy", text: "Jujuy" },
        { value: "La Pampa", text: "La Pampa" },
        { value: "La Rioja", text: "La Rioja" },
        { value: "Mendoza", text: "Mendoza" },
        { value: "Misiones", text: "Misiones" },
        { value: "Neuquen", text: "Neuquen" },
        { value: "Rio Negro", text: "Rio Negro" },
        { value: "Salta", text: "Salta" },
        { value: "San Juan", text: "San Juan" },
        { value: "Santa Cruz", text: "Santa Cruz" },
        { value: "Santa Fe", text: "Santa Fe" },
        { value: "Santiago del Estero", text: "Santiago del Estero" },
        { value: "Tierra del Fuego", text: "Tierra del Fuego" },
        { value: "Tucuman", text: "Tucuman" }
      ]
    };
  },
  methods: {
    async getPlaces() {
      this.loading = true;
      var position;
      var url;
      var param = {
        lat: null,
        lon: null,
        near: null,
        termino: null,
        radius: 1000
      };

      if (this.selselected) {
        position = this.selselected;
        param.near = position;
        if (this.termino == "") {
          url = "/api/places";
        } else {
          url = "/api/places/search";
          param.termino = this.termino;
        }
        console.log("Requesting places with position", position);
      } else {
        position = await currentCoordinates();
        param.lat = position.latitude;
        param.lon = position.longitude;
        if (this.termino == "") {
          url = "/api/places";
        } else {
          url = "/api/places/search";
          param.termino = this.termino;
        }
        console.log("Requesting places with position", position);
      }
      axios
        .get(url, {
          params: param
        })
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          this.venues = response.data.map(p => {
            return {
              latitude: p.location.lat,
              longitude: p.location.lng,
              title: p.name,
              address: p.location.address,
              foursquareId: p.id
            };
          });
          const venuesToAdd = this.venues.filter(
            v => !this.venues.some(vd => vd.foursquareId === v.foursquareId)
          );
          this.venues.push(...venuesToAdd);
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    openAddModal: function(venue) {
      this.selectedVenue = venue;
      this.$refs["AddToListModal"].show();
      this.getLists();
    },
    closeAddModal: function() {
      this.$refs["AddToListModal"].hide();
    },
    getLists: function() {
      var self = this;
      self.loadingLists = true;
      axios
        .get("/api/me/lists")
        .then(function(response) {
          console.log(response.data);
          self.lists = response.data;
          self.loadingLists = false;
        })
        .catch(function(error) {
          self.loadingLists = false;
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    },
    addPlaceToList: function(listId) {
      var self = this;
      axios
        .post("/api/me/lists/" + listId + "/add", this.selectedVenue)
        .then(function(response) {
          console.log(response.data);
          self.getLists();
        })
        .catch(function(error) {
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    },
    listHasSelectedPlace: function(list) {
      var self = this;
      var hasPlace = false;
      list.places.forEach(function(place) {
        if (place.foursquareId === self.selectedVenue.foursquareId) {
          hasPlace = true;
        }
      });
      return hasPlace;
    }
  }
};
</script>
