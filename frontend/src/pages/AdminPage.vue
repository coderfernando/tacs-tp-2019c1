<template>
  <div class="homepage">
    <div class="row">
      <app-menu></app-menu>
    </div>
    <div id="adminPage" class="row">
      <h3>Admin Panel</h3>
    </div>
  </div>
</template>

<script>
import AppMenu from "@/components/AppMenu";

export default {
  name: "AdminPage",
  components: {
    "app-menu": AppMenu
  }
};
</script>
