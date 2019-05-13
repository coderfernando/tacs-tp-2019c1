<template>
  <div class="homepage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div class="row">
      <h1>Home</h1>
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
