<template>
  <div class="terms_list">
    <div class="details-value" v-if="terms.length > 0">
      <div v-for="termIt in terms" :key="termIt.id" class="value">
        <term-item :term="termIt"/>
      </div>
    </div>
    <div class="details-value" v-else>
      <my-label>Nothing found</my-label>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import TermItem from "@/components/results/Term/TermItem.vue";

  export default defineComponent({
    name: 'TermList',
    components: {TermItem},
    props: {
      terms: {
        type: Array,
        required: true
      }
    }
  })
</script>

<style scoped>
  .terms_list {
    width: 100%;
  }
  .value {
    border-bottom: 3px solid #ccc;
  }
  .value:hover {
    background-color: #d8d8d8;
    cursor: pointer;
  }
</style>