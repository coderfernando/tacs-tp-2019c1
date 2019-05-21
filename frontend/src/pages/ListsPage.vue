<template>
  <div class="listpage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div class="row">
       <div class="col-sm options">
          <b-button id="new-list-btn" @click="showNewListModal" class="btn-l float-right" variant="primary">+</b-button>
          <b-modal ref="NewListModal" hide-footer title="New List">
            <div class="d-block">
              <form>
                <div class="form-group">
                  <label for="name">Name</label>
                  <input type="text" class="form-control" v-model="newListName" id="newListName">
                </div>
              </form>
            </div>
            <div class="d-block float-right">
              <b-button variant="light" @click="closeNewListModal">Close</b-button>
              <b-button variant="success" @click="saveNewList">Save</b-button>
            </div>
          </b-modal>
        </div>
    </div>
    <div class="row">
      <ul id="listsList" class="col-sm items-list">
        <li v-for="l in lists" v-bind:key="l.id.machineIdentifier">
            <h4>{{l.name}}</h4>
            <a>Eliminar</a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>

import AppMenu from '@/components/AppMenu'
import axios from 'axios'

export default {
  name: 'ListsPage',
  data () {
    return {
      newListName: '',
      lists: []
    }
  },
  methods: {
    showNewListModal: function () {
      this.$refs['NewListModal'].show()
    },
    async getPlaces () {
      axios.get('http://localhost:8080/api/me/lists')
    },
    closeNewListModal: function () {
      this.$refs['NewListModal'].hide()
    },
    saveNewList: function () {
      var self = this
      axios.post('http://localhost:8080/api/me/lists', {
        name: this.newListName
      }).then(function (response) {
        console.log(response)
        self.closeNewListModal()
        self.getLists()
      }).catch(function (error) {
        console.log(error)
        alert('Ocurrió un error. Intente de nuevo por favor.')
      })
    },
    getLists: function () {
      var self = this
      axios.get('http://localhost:8080/api/me/lists').then(function (response) {
        console.log(response)
        self.lists = response.data
      }).catch(function (error) {
        console.log(error)
        alert('Ocurrió un error. Intente de nuevo por favor.')
      })
    }
  },
  created () {
    this.getLists()
  },
  components: {
    'app-menu': AppMenu
  }
}
</script>
