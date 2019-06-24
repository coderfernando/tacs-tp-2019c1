<template>
  <div class="admin">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div v-if="loading" class="row">
      <img class="loading-image" src="/static/img/loading.gif" />
    </div>
    <div v-if="!loading" class="row">
      <b-tabs pills>
        <b-tab title="User Data" active>
          <div>
            <b-button type="button" class="btn btn-primary" @click="getUsers"
              >Get Users</b-button
            >
            <b-card no-body>
              <b-tabs pills card vertical end>
                <b-tab v-for="us in users" :key="us.name" :title="us.name">
                  <b-card-text>
                    <p>ID: {{ us.id }}</p>
                    <p>Last Login: {{ us.lastAccess }}</p>
                    <p>List Quantity: {{ us.listCount }}</p>
                    <p>Visited Places Quantity: {{ us.visitedCount }}</p>
                  </b-card-text>
                </b-tab>
              </b-tabs>
            </b-card>
          </div>
        </b-tab>
        <b-tab title="Lists Comparison"></b-tab>
        <b-tab title="Place Interest"></b-tab>
        <b-tab title="Places Registered"></b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import AppMenu from "@/components/AppMenu";
import axios from "axios";

export default {
  name: "AdminPage",
  components: {
    "app-menu": AppMenu
  },
  data: function() {
    return {
      loading: false,
      users: []
    };
  },
  methods: {
    async getUsers() {
      axios
        .get("/api/admin/users", {})
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          this.users = response.data.map(u => {
            return {
              id: u.id.machineIdentifier,
              name: u.name,
              lastAccess: u.lastAccess,
              listCount: this.getUserListCount(u.id.machineIdentifier),
              visitedCount: this.getUserVisitedCount(u.id.machineIdentifier),
              placesLists: [
                ...u.placesLists.map(pl => {
                  return {
                    id: pl.id,
                    name: pl.name
                  };
                })
              ]
            };
          });
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    getUser(userid) {
      axios
        .get("/api/admin/users/" + userid + "/data", {})
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          return response.data.map(u => {
            return {
              id: u.id,
              name: u.name,
              lastAccess: u.lastAccess,
              placesLists: [
                ...u.placesLists.map(pl => {
                  return {
                    id: pl.id,
                    name: pl.name,
                    places: [
                      ...pl.places.map(p => {
                        return {
                          latitude: p.location.lat,
                          longitude: p.location.lng,
                          title: p.name,
                          address: p.location.address,
                          foursquareId: p.id
                        };
                      })
                    ],
                    visitedPlacesIds: [...pl.visitedPlacesIds]
                  };
                })
              ]
            };
          });
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    getUserListCount(userid) {
      axios
        .get("/api/admin/users/" + userid + "/lists", {})
        .then(response => {
          console.log("Response", response);
          return response.data;
        })
        .catch(e => {
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    getUserVisitedCount(userid) {
      axios
        .get("/api/admin/users/" + userid + "/visited", {})
        .then(response => {
          console.log("Response", response);
          return response.data;
        })
        .catch(e => {
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    async getCommomPlaces(userid1, listid1, userid2, listid2) {
      var user1 = this.getUser(userid1);
      var user2 = this.getUser(userid2);

      var list1 = user1.placesLists.find(function(lista) {
        return lista.id == listid1;
      });

      var list2 = user2.placesLists.find(function(lista) {
        return lista.id == listid2;
      });

      var common = [];

      list1.places.forEach(place => {
        if (
          list2.places.filter(function(pl) {
            return pl.foursquareId == place.foursquareId;
          }) &&
          !common.some(function(lugar) {
            return place.foursquareId == lugar.foursquareId;
          })
        ) {
          common.push(place);
        }
      });
      return common;
    },
    async getPlaceInterest(placeid) {
      axios
        .get("/api/admin/users/interestedin/" + placeid, {})
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          return response.data;
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
          this.errors.push(e);
        });
    },
    async getRegisteredPlaces(fecha) {
      axios
        .get("/api/admin/placesregistered", { dateFrom: fecha })
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          return response.data;
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
          this.errors.push(e);
        });
    }
  }
};
</script>
