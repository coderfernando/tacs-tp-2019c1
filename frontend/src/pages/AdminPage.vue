<template>
  <div id="adminpanel">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div v-if="loading" class="row">
      <img class="loading-image" src="/static/img/loading.gif" />
    </div>
    <div v-if="!loading" class="row">
      <b-tabs pills align="center">
        <b-tab title="User Data" active>
          <div>
            <b-card no-body>
              <b-tabs pills card vertical begin>
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
                        <UserComponent
                          :user="us"
                          @dato="setupComp1"
                        ></UserComponent>
                      </b-tab>
                    </b-tabs>
                  </b-card>
                </td>
                <td>
                  <b-card no-body>
                    <b-tabs pills card vertical>
                      <b-tab v-for="us in users" :key="us.id" :title="us.name">
                        <UserComponent
                          :user="us"
                          @dato="setupComp2"
                        ></UserComponent>
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
          <div align="center">
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
        <b-tab title="Place Interest">
          <div>
            <div>
              <b-form-input
                v-model="searchText"
                placeholder="Search by venue title"
                @input="filterInterestedPlaces"
              ></b-form-input>
              <div class="mt-2">Only shows first 10 places</div>
            </div>
            <br />
            <b-card
              v-for="placeinterest in placesofinterestFiltered"
              id="placeofinterestcard"
              :key="placeinterest.id"
              bg-variant="secondary"
              text-variant="white"
              :header="placeinterest.venue.title"
            >
              <b-card-text id="placeofinteresttext">
                <p>
                  Date of Register:
                  {{ new Date(placeinterest.registeredDate).toDateString() }}
                </p>
                <p>
                  Users interested: {{ placeinterest.usersInterestedCount }}
                </p>
              </b-card-text>
            </b-card>
          </div>
        </b-tab>
        <b-tab title="Registered Places">
          <div align="center">
            <b-button-group align="center">
              <b-button
                :pressed="registeredPlacesSelectedButton == 0"
                @click="getRegisteredPlaces(0)"
                >Today</b-button
              >
              <b-button
                :pressed="registeredPlacesSelectedButton == 1"
                @click="getRegisteredPlaces(1)"
                >Last Three Days</b-button
              >
              <b-button
                :pressed="registeredPlacesSelectedButton == 2"
                @click="getRegisteredPlaces(2)"
                >Last Week</b-button
              >
              <b-button
                :pressed="registeredPlacesSelectedButton == 3"
                @click="getRegisteredPlaces(3)"
                >Last Month</b-button
              >
              <b-button
                :pressed="registeredPlacesSelectedButton == 4"
                @click="getRegisteredPlaces(4)"
                >Beginning Of Times</b-button
              >
            </b-button-group>
          </div>
          <br />
          <div align="center">
            {{ registeredPlaces.length }} registered places
          </div>
          <br />
          <div align="center">
            <b-card
              v-for="regPlace in registeredPlaces"
              :key="regPlace.venue.foursquareId"
              :header="regPlace.venue.title"
              class="registeredPlaceCard"
            >
              <b-card-text>
                <p>Address: {{ regPlace.venue.address }}</p>
                <p>
                  Date of Register:
                  {{ new Date(regPlace.date).toDateString() }}
                </p>
              </b-card-text>
            </b-card>
          </div>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import AppMenu from "@/components/AppMenu";
import UserComponent from "@/components/UserComponent";
import axios from "axios";

export default {
  name: "AdminPage",
  components: {
    "app-menu": AppMenu,
    UserComponent: UserComponent
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
      commonPlaces: [],
      placesofinterest: [],
      placesofinterestFiltered: [],
      searchText: "",
      registeredPlaces: [],
      registeredPlacesSelectedButton: 0
    };
  },
  created() {
    this.getUsersData();
    this.getPlacesOfInterest();
    this.getRegisteredPlaces(0);
  },
  methods: {
    setupComp1(payload) {
      this.usToComp1 = payload.usid;
      this.lstToComp1 = payload.lstid;
    },
    setupComp2(payload) {
      this.usToComp2 = payload.usid;
      this.lstToComp2 = payload.lstid;
    },
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
                ...u.placesLists.map(function(pl) {
                  return {
                    id: pl.id,
                    name: pl.name,
                    places: pl.places
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
          return response.data.map(function(u) {
            return {
              id: u.id,
              name: u.name,
              lastAccess: u.lastAccess,
              placesLists: [
                ...u.placesLists.map(function(pl) {
                  return {
                    id: pl.id,
                    name: pl.name,
                    places: [
                      ...pl.places.map(function(p) {
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
    async getCommomPlaces() {      
      let user1 = this.users.find(u => u.id === this.usToComp1);
      let user2 = this.users.find(u => u.id === this.usToComp2);

      let list1 = user1.placesLists.find(l => l.id === this.lstToComp1);
      let list2 = user2.placesLists.find(l => l.id === this.lstToComp2);

      var common = [];

      list1.places.forEach(place => {
        if (
          list2.places.some(function(pl) {
            return pl.foursquareId === place.foursquareId;
          }) &&
          !common.some(function(lugar) {
            return place.foursquareId === lugar.foursquareId;
          })
        ) {
          common.push(place);
        }
      });
      if (common.length === 0) {
        let strNoResults = `No places in common between list: "${list1.name}" from user: "${user1.name}"`;
        strNoResults += ` and list: "${list2.name}" from user: "${user2.name}" :(`;
        common.push({title: strNoResults})
      }
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
    async getRegisteredPlaces(num) {
      this.registeredPlacesSelectedButton = num;
      /* eslint-disable */
      var today = new Date();
      var dd = String(today.getDate()).padStart(2, '0');
      var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
      var yyyy = today.getFullYear();
      today = mm + '/' + dd + '/' + yyyy;
      var fecha = new Date(today);
      /* eslint-enable */
      switch (num) {
        case 1:
          fecha.setDate(fecha.getDate() - 3);
          break;
        case 2:
          fecha.setDate(fecha.getDate() - 7);
          break;
        case 3:
          fecha.setDate(fecha.getDate() - 30);
          break;
        case 4:
          fecha = new Date(2019, 1, 1);
          break;
        default:
          break;
      }
      axios
        .get("/api/admin/placesregistered?dateFrom=" + fecha)
        .then(response => {
          console.log("Response", response);
          this.loading = false;
          this.registeredPlaces = response.data.map(rp => {
            return {
              id: rp.id,
              venue: {
                title: rp.venue.title,
                address: rp.venue.address,
                foursquareId: rp.venue.id
              },
              date: rp.registeredDate
            };
          });
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
        });
    },
    async getPlacesOfInterest() {
      axios
        .get("/api/admin/placesofinterest")
        .then(response => {
          this.placesofinterest = response.data
            .sort(pi => pi.registeredDate)
            .reverse();
          this.placesofinterestFiltered = response.data
            //.filter(d => true)
            .sort(pi => pi.registeredDate)
            .reverse()
            .slice(0, 10);
          this.loading = false;
          console.log(this.placesofinterestFiltered);
          return response.data;
        })
        .catch(e => {
          this.loading = false;
          console.log("ERROR", e);
        });
    },
    async filterInterestedPlaces() {
      this.placesofinterestFiltered = this.placesofinterest
        .filter(
          pr =>
            !this.searchText ||
            this.textCompare(pr.venue.title, this.searchText)
        )
        .sort(pi => pi.registeredDate)
        .reverse()
        .slice(0, 10);
    },
    textCompare(text1, text2) {
      text1 = text1
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
      text2 = text2
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
      return text1.includes(text2);
    }
  }
};
</script>
<style>
#placeofinterestcard {
  margin-bottom: 15px;
}
#placeofinteresttext {
  margin-bottom: -25px;
}
</style>
