<template>
  <ul id="menu" class="list-group list-group-horizontal-xl col-sm">
    <a
      href="#/"
      :class="{ active: selectedItem == 'Home' }"
      class="list-group-item list-group-item-action"
    >
      Home
    </a>
    <a
      href="#/lists"
      :class="{ active: selectedItem == 'Lists' }"
      class="list-group-item list-group-item-action"
    >
      Lists
    </a>
    <a
      href="#/places"
      :class="{ active: selectedItem == 'Places' }"
      class="list-group-item list-group-item-action"
    >
      Places
    </a>
  </ul>
</template>

<script>
export default {
  name: "AppMenu",
  data: function() {
    return {
      selectedItem: "Home"
    };
  },
  mounted: function() {
    this.selectedItem = this.$router.currentRoute.name;
  }
};
</script>
