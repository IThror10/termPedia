<template>
  <div class="lit_list">
    <div class="details-value" v-if="lit.length > 0">
      <div v-for="(book, index) in lit" :key="book.lid" class="element" @click="$emit('selected', index)">
        <div class="value">{{book.type}}: {{book.name}}({{book.year}})</div>
        <div class="authors">{{authors(book)}}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {LiteratureData} from "@/services/LiteratureService";

  export default defineComponent({
    name: 'LitNameList',
    emits: ['selected'],
    props: {
      lit: {
        type: Array as () => LiteratureData[],
        required: true
      }
    },
    methods: {
      authors(book: LiteratureData) {
        switch (book.authors.length) {
          case 0:
            return '';
          case 1:
            return book.authors[0];
          default:
            return book.authors[0] + ' others ';
        }
      }
    }
  })
</script>

<style scoped>
  .lit_list {
    width: 100%;
  }
  .element {
    height: auto;
    border: 2px solid #ccc;
    background-color: white;
    margin-top: -2px;
  }
  .element:hover {
    background-color: #d8d8d8;
    cursor: pointer;
  }
</style>