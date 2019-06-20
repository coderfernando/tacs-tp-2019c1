<template>
  <div class="row">
    <a class="col-sm-6" href="/">
      <img src="../../static/img/logo.png" class="logo" />
    </a>
    <div class="col-sm-6 header-links">
      <a v-if="!isLogged" href="/login">Login</a>
      <a v-if="isLogged" href="/logout">Logout</a>
      <a v-if="!isLogged" href="#/signup">Register</a>
      <a v-if="user.isAdmin" href="#/admin">Admin panel</a>
    </div>
  </div>
</template>

<script>
import axios from "axios";

/* eslint-disable */
export function currentUser() {
  return new Promise((resolve, reject) => {
    axios
      .post("/api/user/me", {})
      .then(function(response) {
        resolve(response.data);
      })
      .catch(function() {
        resolve(undefined);
      });
  });
}
/* eslint-enable */

export default {
  name: "Header",
  data() {
    return {
      isLogged: false,
      user: {}
    };
  },
  created: async function() {
    this.user = await this.getUser();
    this.isLogged = this.user ? true : false;
  },
  methods: {
    async getUser() {
      return await currentUser();
    }
  }
};
</script>

<style></style>
