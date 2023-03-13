<template>
  <div class="element">
    <div class="value">{{book.type}}: {{book.name}}({{book.year}})</div>
    <div class="authors">{{authors(book.authors)}}</div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {LiteratureData} from "@/services/LiteratureService";
  export default defineComponent({
    name: "LitName",
    props: {
      book: {
        type: Object as () => LiteratureData,
        required: true
      }
    },
    methods: {
      authors(list: string[]) {
        switch (list.length) {
          case 0:
            return '';
          case 1:
            return list[0];
          case 2:
            return `${list[0]} && ${list[1]}`;
          default:
            return `${list[0]} && ${list[1]} && others`;
        }
      }
    }
  })
</script>

<style scoped>
</style>