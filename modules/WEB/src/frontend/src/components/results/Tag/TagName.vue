<template>
  <div class="element">
    <my-value class="value">{{tag.name}}</my-value>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {TagData} from "@/services/TagService";
  export default defineComponent({
    name: "TagName",
    props: {
      tag: {
        type: Object as () => TagData,
        required: true
      }
    }
  })
</script>

<style scoped>

</style>