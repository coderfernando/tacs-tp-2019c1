<template>
  <div class="signup">
     <b-form @submit.prevent="onSubmit">
      <b-form-group
      id="name-group"
      label="Username:"
      label-for="name-input"
      >
        <b-form-input
          id="name-input"
          type="text"
          required
          placeholder="Enter your username"
          v-model="user.name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="password-group"
        label="Password:"
        label-for="input-password"
      >
        <b-form-input
          id="input-password"
          type="password"
          required
          placeholder="Enter your password"
          v-model="user.password"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="confirm-password-group"
        label="Confirm Password:"
        label-for="input-confirm-password"
      >
        <b-form-input
          id="input-confirm-passsword"
          type="password"
          required
          placeholder="Confirm your password"
          v-model="user.confirmPassword"
          @change="validatePassword"
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Sign Up</b-button>
    </b-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SignUp',
  data: () => ({
    user: {
      name: '',
      password: '',
      confirmPassword: ''
    }
  }),
  methods: {
    onSubmit () {
      this.validatePassword()
      let newUser = this.user

      axios.post('/api/user', newUser).then(function (response) {
        console.log(response)
      }).catch(function (error) {
        console.log(error)
        alert('Ocurrió un error. Intente de nuevo por favor.')
      })
    },
    validatePassword () {
      if (this.user.password !== this.user.confirmPassword) {
        alert('Passwords do not match')
        this.user.password = ''
        this.user.confirmPassword = ''
      }
    }
  }
}
</script>
