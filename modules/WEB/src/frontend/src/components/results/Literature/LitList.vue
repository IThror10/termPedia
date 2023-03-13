<template>
  <div class="lit_list" v-if="lit.length > 0">
    <div v-for="(book, id) in lit" :key="id" class="element">
      <lit-item :term-id="termId" :lit="book" class="lit"/>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import LitItem from "@/components/results/Literature/LitItem.vue";
  import {RatedLiteratureData} from "@/services/LiteratureService";
  export default defineComponent({
    name: "LitList",
    components: {LitItem},
    props: {
      lit: {
        type: Array as () => RatedLiteratureData[],
        required: true
      },
      termId: {
        type: Number,
        required: true
      }
    }
  })
</script>

<style scoped>
.lit_list {
  width: 100%;
}
.lit {
  border-bottom: 3px solid #ccc;
  margin-top: 5px;
}
</style>