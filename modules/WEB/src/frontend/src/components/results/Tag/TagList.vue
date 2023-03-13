<template>
  <div class="tags_list" v-if="tags.length > 0">
    <div v-for="(tag, id) in tags" :key="id" class="element">
      <tag-item :tag="tag" class="tag" :term-id="termId"/>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import TagItem from "@/components/results/Tag/TagItem.vue";
  import {TagData} from "@/services/TagService";
  export default defineComponent({
    name: "TagList",
    components: {TagItem},
    props: {
      tags: {
        type: Array as () => TagData[],
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
  .tags_list {
    width: 100%;
  }
  .tag {
    border-bottom: 3px solid #ccc;
    margin-top: 5px;
  }
</style>