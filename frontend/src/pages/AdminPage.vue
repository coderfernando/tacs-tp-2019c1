<template>
  <div id="adminpanel">
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
        <b-tab title="Lists Comparison">
          <div>
            <table>
              <th>First User</th>
              <th>Second User</th>
              <tr>
                <td>
                  <b-card no-body>
                    <b-tabs pills card vertical>
                      <b-tab v-for="us in users" :key="us.id" :title="us.name">
                        <b-card no-body>
                          <b-tabs pills card vertical>
                            <b-tab
                              v-for="lst in us.placesLists"
                              :key="lst.id"
                              :title="lst.name"
                            >
                              <b-button
                                type="button"
                                class="btn btn-primary"
                                :click="
                                  ((usToComp1 = us.id), (lstToComp1 = lst.id))
                                "
                              >
                                Add to Compare
                              </b-button>
                            </b-tab>
                          </b-tabs>
                        </b-card>
                      </b-tab>
                    </b-tabs>
                  </b-card>
                </td>
                <td>
                  <b-card no-body>
                    <b-tabs pills card vertical>
                      <b-tab v-for="us in users" :key="us.id" :title="us.name">
                        <b-card no-body>
                          <b-tabs pills card vertical>
                            <b-tab
                              v-for="lst in us.placesLists"
                              :key="lst.id"
                              :title="lst.name"
                            >
                              <b-button
                                type="button"
                                class="btn btn-primary"
                                :click="
                                  ((usToComp2 = us.id), (lstToComp2 = lst.id))
                                "
                              >
                                Add to Compare
                              </b-button>
                            </b-tab>
                          </b-tabs>
                        </b-card>
                      </b-tab>
                    </b-tabs>
                  </b-card>
                </td>
              </tr>
            </table>
          </div>

          <div align="center">
            <b-button
              type="button"
              class="btn btn-primary"
              @click="getCommomPlaces"
            >
              Compare
            </b-button>
          </div>
          <div v-if="!ready" class="row">
            <b-list-group>
              <b-list-group-item
                v-for="vnue in commonPlaces"
                :key="vnue.foursquareId"
              >
                <p>{{ vnue.title }}</p>
                <p>{{ vnue.address }}</p></b-list-group-item
              >
            </b-list-group>
          </div>
        </b-tab>
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
      ready: false,
      users: [],
      usToComp1: null,
      usToComp2: null,
      lstToComp1: null,
      lstToComp2: null,
      commonPlaces: []
    };
  },
  created() {
    this.getUsersData();
  },
  methods: {
    async getUsersData() {
      axios
        .get("/api/admin/users", {})
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          this.users = response.data.map(function(u) {
            return {
              id: u.id,
              name: u.name,
              lastAccess: u.lastAccess,
              listCount: u.placesLists.length,
              visitedCount: u.placesLists
                .map(pl => {
                  return pl.visitedPlacesIds.length;
                })
                .reduce((a, b) => a + b, 0),
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
          console.log("USERS", this.users);
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
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
        });
    },
    async getUserListCount(userid) {
      return new Promise(resolve => {
        axios
          .get("/api/admin/users/" + userid + "/lists", {})
          .then(response => {
            console.log("Response", response);
            resolve(response.data);
          })
          .catch(e => {
            console.log("ERROR", e);
            resolve(undefined);
          });
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
        });
    },
    async getCommomPlaces(
      userid1 = this.usToComp1,
      listid1 = this.lstToComp1,
      userid2 = this.usToComp2,
      listid2 = this.lstToComp2
    ) {
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
      this.commonPlaces = common;
      this.ready = true;
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
        });
    }
  }
};
</script>
