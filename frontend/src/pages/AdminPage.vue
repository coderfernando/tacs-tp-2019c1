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
            <b-card no-body>
              <b-tabs pills card vertical end>
                <b-tab v-for="us in users" :key="us.name" title="us.name">
                  <b-card-text>
                    ID: {{ us.id }}
                    Last Login: {{ us.lastAccess }}
                    List Quantity: {{ us.listCount }}
                    Visited Places Quantity: {{ us.visitedCount }}
                  </b-card-text>
                </b-tab>
              </b-tabs>
            </b-card>
          </div>
        </b-tab>
        <b-tab title="Lists Comparison">

        </b-tab>
        <b-tab title="Place Interest">

        </b-tab>
        <b-tab title="Places Registered">

        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import AppMenu from "@/components/AppMenu";

export default {
  name: "AdminPage",
  components: {
    "app-menu": AppMenu
  },
  data: function() {
    return {
      user: null,
      users: []
    }
  },
  methods: {
    async getUsers: function() {
      axios
      .get("/api/admin/users", {})
      .then(response => {
        console.log("Response", response);
        this.loading = false;
        this.users = response.data.map(u => {
          return {
            id: u.id,
            name: u.name,
            lastAccess: u.lastAccess,
            listCount: this.getUserListCount(u.id),
            visitedCount: this.getUserVisitedCount(u.id),
            placesLists: [...u.placesLists.map(pl => {
              return {
                id: pl.id,
                name:pl.name
              };
            })]
          };
        });
      })
      .catch(e => {
        this.loading = false;
        console.log("ERROR", e);
        this.errors.push(e);
      });
    },
    async getUser: function(userid) {
      axios
      .get("/api/admin/users/" + userid + "/data", {})
      .then(response => {
        console.log("Response", response);
        this.loading = false;
        this.user = response.data.map(u => {
          return {
            id: u.id,
            name: u.name,
            lastAccess: u.lastAccess,
            placesLists: [...u.placesLists.map(pl => {
              return {
                id: pl.id,
                name:pl.name,
                places: [...pl.places.map(p => {
                  return {
                    latitude: p.location.lat,
                    longitude: p.location.lng,
                    title: p.name,
                    address: p.location.address,
                    foursquareId: p.id
                  };
                })],
                visitedPlacesIds: [...pl.visitedPlacesIds]
              };
            })]
          };
        });
      })
      .catch(e => {
        this.loading = false;
        console.log("ERROR", e);
        this.errors.push(e);
      });
    },
    async getUserListCount: function(userid) {
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
    async getUserVisitedCount: function(userid) {
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
    }
  },
  beforeMounted: {
    function (){
      this.getUsers();
    }
  }
};
</script>
