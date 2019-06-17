<template>
  <div class="row">
    <a class="col-sm-6" href="/">
      <img src="../../static/img/logo.png" class="logo" />
    </a>
    <div class="col-sm-6 header-links">
      <a v-if="!isLogged" href="/login">Login</a>
      <a v-if="isLogged" href="/logout">Logout</a>
      <a v-if="!isLogged" href="#/signup">Register</a>
    </div>
  </div>
</template>

<script>
import axios from "axios";

/* eslint-disable */
export function isLoggedIn() {
  return new Promise((resolve, reject) => {
    axios
      .post("/api/user/isuserlogged", {})
      .then(function() {
        resolve(true);
      })
      .catch(function() {
        resolve(false);
      });
  });
}
/* eslint-enable */

export default {
  name: "Header",
  data() {
    return {
      isLogged: false
    };
  },
  created: async function() {
    this.isLogged = await this.checkIfLogged();
  },
  methods: {
    async checkIfLogged() {
      return await isLoggedIn();
    }
  }
};
</script>

<style></style>
