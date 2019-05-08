<template>
  <div class="homepage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div class="row">
      <h1>Lists</h1>
    </div>
  </div>
</template>

<script>
import AppMenu from '@/components/AppMenu'

export default {
  name: 'ListsPage',
  components: {
    'app-menu': AppMenu
  }
}
</script>
