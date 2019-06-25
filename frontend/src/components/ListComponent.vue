<template>
  <div class="list">
    <b-button type="button" class="btn btn-primary" @click="getData">
      Add to Compare
    </b-button>
  </div>
</template>

<script>
export default {
  name: "ListComponent",
  // eslint-disable-next-line vue/require-prop-types
  props: ["usid", "list"],
  data: function() {
    return {};
  },
  methods: {
    getData() {
      this.$emit("dato", { usid: this.usid, lstid: this.list.id });
    }
  }
};
</script>
