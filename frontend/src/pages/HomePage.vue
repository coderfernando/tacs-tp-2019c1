<template>
  <div class="homepage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div id="home-messages" class="row">
      <div class="col-sm-12">
        <h1>Welcome to Placist!</h1>
      </div>
      <div class="col-sm-12">
        <h3>Discover new places.</h3>
      </div>
      <div class="col-sm-12">
        <h3>Create ToGo lists.</h3>
      </div>
      <div class="col-sm-12">
        <h3>Check your visits.</h3>
      </div>
    </div>
  </div>
</template>

<script>
import AppMenu from '@/components/AppMenu'

export default {
  name: 'HomePage',
  components: {
    'app-menu': AppMenu
  }
}
</script>
