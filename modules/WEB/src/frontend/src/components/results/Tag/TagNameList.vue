<template>
  <div class="tags_list">
    <div class="details-value" v-if="tags.length > 0">
      <div v-for="(tag, id) in tags" :key="id" class="element" @click="$emit('tagSelected', id)">
        <my-value class="value">{{tag.name}}</my-value>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {TagName} from "@/services/TagService";
  import MyValue from "@/components/UI/primitives/MyValue.vue";

  export default defineComponent({
    name: 'TagsNameList',
    components: {MyValue},
    emits: ['tagSelected'],
    props: {
      tags: {
        type: Array as () => TagName[],
        required: true
      }
    }
  })
</script>

<style scoped>
  .tags_list {
    width: 100%;
  }
  .value {
    border: 2px solid #ccc;
    background-color: white;
    margin-top: -2px;
  }
  .value:hover {
    background-color: #d8d8d8;
    cursor: pointer;
  }
</style>