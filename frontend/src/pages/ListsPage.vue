<template>
  <div class="listpage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div v-if="loading" class="row">
      <img class="loading-image" src="/static/img/loading.gif" />
    </div>
    <div v-if="!loading">
      <div class="row">
        <div class="col-sm options">
          <b-button
            id="new-list-btn"
            class="btn-l float-right"
            variant="primary"
            @click="showNewListModal"
          >
            +
          </b-button>
        </div>
      </div>
      <div class="row">
        <ul id="listsList" class="col-sm items-list">
          <li v-for="l in lists" :key="l.id">
            <h4>
              {{ l.name }}
              <img
                src="/static/img/edit-icon.png"
                @click="showChangeNameModal(l)"
              />
            </h4>
            <a @click="deleteList(l.id)">Delete</a>
            <div v-if="l.places.length > 0">
              <b>Places</b>
            </div>
            <div v-for="p in l.places" :key="p.id">- {{ p.title }}</div>
          </li>
        </ul>
      </div>

      <b-modal ref="NewListModal" hide-footer title="New List">
        <div class="d-block">
          <form>
            <div class="form-group">
              <label for="name">Name</label>
              <input v-model="newListName" type="text" class="form-control" />
            </div>
          </form>
        </div>
        <div class="d-block float-right">
          <b-button variant="light" @click="closeNewListModal">Close</b-button>
          <b-button variant="success" @click="saveNewList">Save</b-button>
        </div>
      </b-modal>

      <b-modal ref="ChangeListNameModal" hide-footer title="Change List Name">
        <div class="d-block">
          <form>
            <div class="form-group">
              <label for="name">Name</label>
              <input v-model="newListName" type="text" class="form-control" />
            </div>
          </form>
        </div>
        <div class="d-block float-right">
          <b-button variant="light" @click="closeChangeListNameModal">
            Close
          </b-button>
          <b-button variant="success" @click="changeListName">Save</b-button>
        </div>
      </b-modal>
    </div>
  </div>
</template>
<script>
import AppMenu from "@/components/AppMenu";
import axios from "axios";

export default {
  name: "ListsPage",
  components: {
    "app-menu": AppMenu
  },
  data() {
    return {
      newListName: "",
      lists: [],
      loading: true,
      selectedList: {}
    };
  },
  created() {
    this.getLists();
  },
  methods: {
    showNewListModal: function() {
      this.newListName = "";
      this.$refs["NewListModal"].show();
    },
    showChangeNameModal: function(list) {
      this.selectedList = list;
      this.newListName = list.name;
      this.$refs["ChangeListNameModal"].show();
    },
    closeNewListModal: function() {
      this.$refs["NewListModal"].hide();
    },
    closeChangeListNameModal: function() {
      this.$refs["ChangeListNameModal"].hide();
    },
    saveNewList: function() {
      var self = this;
      axios
        .post("/api/me/lists", {
          name: this.newListName
        })
        .then(function() {
          self.closeNewListModal();
          self.getLists();
        })
        .catch(function(error) {
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    },
    changeListName: function() {
      var self = this;
      axios
        .patch(
          "/api/me/lists/" +
            this.selectedList.id +
            "/change-name/" +
            this.newListName,
          {}
        )
        .then(function() {
          self.closeChangeListNameModal();
          self.getLists();
        })
        .catch(function(error) {
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    },
    getLists: function() {
      var self = this;
      self.loading = true;
      axios
        .get("/api/me/lists")
        .then(function(response) {
          console.log(response.data);
          self.lists = response.data;
          self.loading = false;
        })
        .catch(function(error) {
          self.loading = false;
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    },
    deleteList: function(id) {
      var self = this;
      axios
        .delete("/api/me/lists/" + id)
        .then(function(response) {
          console.log(response);
          self.getLists();
        })
        .catch(function(error) {
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    }
  }
};
</script>
