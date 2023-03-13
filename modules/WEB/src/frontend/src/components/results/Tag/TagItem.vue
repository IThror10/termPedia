<template>
  <div class="tag_info">
    <div class="value">
      <my-label>{{tag.name}}</my-label>
      <my-label>{{tag.rating}} 	&#11088; ({{tag.ratesAmount}} &#10032;)</my-label>
    </div>
    <div class="rating" v-if="isAuth">
      <my-rating @set-rating="rated" :initial-rating="rating"/>
    </div>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {TagData, TagService} from "@/services/TagService";
  import MyRating from "@/components/UI/primitives/MyRating.vue";
  import store from "@/store";
  export default defineComponent({
    name: "TagItem",
    components: {MyRating},
    data(): {rating: number, update: boolean} {
      return {
        rating: 0,
        update: false
      }
    },
    props: {
      tag: {
        type: Object as () => TagData,
        required: true
      },
      termId: {
        type: Number,
        required: true
      }
    },
    computed: {
      ts: () => {return new TagService()},
      isAuth: () => store.state.auth.isAuth,
    },
    created() {
      this.updateRating();
    },
    watch: {
      tag() {
        this.updateRating();
      }
    },
    methods: {
      rated(rating: number) {
        this.ts.setRating(this.tag.name, this.termId, rating).then(() => this.rating = rating);
      },
      async updateRating() {
        if (this.isAuth) {
          this.rating = await this.ts.getRating(this.tag.name, this.termId);
        }
      }
    }
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