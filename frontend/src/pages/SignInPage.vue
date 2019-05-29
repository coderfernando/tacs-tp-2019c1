<template>
  <div class="signup">
    <b-form @submit.prevent="onSubmit">
      <b-form-group id="name-group" label="Username:" label-for="name-input">
        <b-form-input
          id="name-input"
          v-model="user.name"
          type="text"
          required
          placeholder="Enter your username"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="password-group"
        label="Password:"
        label-for="input-password"
      >
        <b-form-input
          id="input-password"
          v-model="user.password"
          type="password"
          required
          placeholder="Enter your password"
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Sign In</b-button>
    </b-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SignIn",
  data: () => ({
    user: {
      name: "",
      password: ""
    }
  }),
  methods: {
    onSubmit() {
      let user = this.user;

      axios
        .post("/api/user/login", user)
        .then(function(response) {
          console.log(response);
        })
        .catch(function(error) {
          console.log(error);
          alert("Ocurrió un error. Intente de nuevo por favor.");
        });
    }
  }
};
</script>
