<template>
  <div class="tag_info">
    <div class="value">
      <div class="data">
        <my-label>{{lit.name}} ({{lit.year}})</my-label>
        <my-value>Type: {{lit.type}}</my-value>
        <my-value>Authors: {{lit.authors}}</my-value>
      </div>
      <div v-if="'ratesAmount' in lit">
        <my-label >
          {{lit.rating}} 	&#11088; ({{lit.ratesAmount}} &#10032;)
        </my-label>
      </div>
      <div v-else-if="'rating' in lit">
        <my-label >
          {{lit.rating}} 	&#11088;
        </my-label>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {LiteratureData, RatedLiteratureData, TagLiteratureData} from "@/services/LiteratureService";

  export default defineComponent({
    name: "BookItem",
    props: {
      lit: {
        type: Object as () => RatedLiteratureData | TagLiteratureData | LiteratureData,
        required: true
      }
    },
})
</script>

<style scoped>
.value {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}
</style>