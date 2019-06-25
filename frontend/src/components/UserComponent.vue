<template>
  <div class="user">
    <b-tabs pills card vertical>
      <b-tab v-for="lst in user.placesLists" :key="lst.id" :title="lst.name">
        <ListComponent
          :list="lst"
          :usid="user.id"
          @dato="getData"
        ></ListComponent>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import ListComponent from "@/components/ListComponent";

export default {
  name: "UserComponent",
  components: {
    ListComponent: ListComponent
  },
  // eslint-disable-next-line vue/require-prop-types
  props: ["user"],
  data: function() {
    return {};
  },
  methods: {
    getData(elem) {
      this.$emit("dato", elem);
    }
  }
};
</script>
