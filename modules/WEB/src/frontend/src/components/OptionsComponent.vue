<template>
  <div>
    <div>
      <label for="yearLimit">Year Limit:</label>
      <input type="number" id="yearLimit" v-model.number="yearLimit" />
    </div>
    <div>
      <label for="newFirst">New First:</label>
      <input type="checkbox" id="newFirst" v-model="newFirst" />
    </div>
    <div>
      <label for="orderByRating">Order By Rating:</label>
      <input type="checkbox" id="orderByRating" v-model="orderByRating" />
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from "vue";

  export default defineComponent({
  name: "OptionsComponent",
  data() {
    return {
      yearLimit: null,
      searchOption: "SearchByTerm",
      newFirst: false,
      orderByRating: false,
    };
  },
  methods: {
    handleSearchClick() {
      console.log('searchClick');
      // this.handleSearch({
      //   yearLimit: this.yearLimit,
      //   searchOption: this.searchOption,
      //   newFirst: this.newFirst,
      //   orderByRating: this.orderByRating,
      // });
    },
  },
});
</script>