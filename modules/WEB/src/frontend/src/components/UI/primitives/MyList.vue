<template>
  <div class="list">
    <div class="body" v-if="notEmpty">
      <slot/>
    </div>
    <div class="body" v-else>
      <my-label>
        Nothing Found
      </my-label>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import MyLabel from "@/components/UI/primitives/MyLabel.vue";
  export default defineComponent({
    name: "MyList",
    components: {MyLabel},
    props: {
      notEmpty: {
        type: Boolean,
        required: true
      }
    }
  })
</script>

<style scoped>

</style>